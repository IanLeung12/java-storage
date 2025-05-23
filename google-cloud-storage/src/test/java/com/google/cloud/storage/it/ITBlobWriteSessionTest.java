/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.storage.it;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.assertThrows;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.BlobWriteSession;
import com.google.cloud.storage.BlobWriteSessionConfigs;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.DataGenerator;
import com.google.cloud.storage.GrpcStorageOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobWriteOption;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.TransportCompatibility.Transport;
import com.google.cloud.storage.it.runner.StorageITRunner;
import com.google.cloud.storage.it.runner.annotations.Backend;
import com.google.cloud.storage.it.runner.annotations.CrossRun;
import com.google.cloud.storage.it.runner.annotations.Inject;
import com.google.cloud.storage.it.runner.registry.Generator;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(StorageITRunner.class)
@CrossRun(
    transports = {Transport.GRPC},
    backends = {Backend.PROD})
public final class ITBlobWriteSessionTest {

  @Rule public final TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Inject public Storage storage;
  @Inject public Transport transport;

  @Inject public BucketInfo bucket;

  @Inject public Generator generator;

  @Test
  public void allDefaults() throws Exception {
    doTest(storage);
  }

  @Test
  public void bufferToTempDirThenUpload() throws Exception {
    StorageOptions options = null;
    if (transport == Transport.GRPC) {
      options =
          ((GrpcStorageOptions) storage.getOptions())
              .toBuilder()
              .setBlobWriteSessionConfig(BlobWriteSessionConfigs.bufferToTempDirThenUpload())
              .build();
    }
    assertWithMessage("unable to resolve options").that(options).isNotNull();
    //noinspection DataFlowIssue
    try (Storage s = options.getService()) {
      doTest(s);
    }
  }

  @Test
  public void overrideDefaultBufferSize() throws Exception {
    StorageOptions options = null;
    if (transport == Transport.GRPC) {
      options =
          ((GrpcStorageOptions) storage.getOptions())
              .toBuilder()
              .setBlobWriteSessionConfig(
                  BlobWriteSessionConfigs.getDefault().withChunkSize(256 * 1024))
              .build();
    }
    assertWithMessage("unable to resolve options").that(options).isNotNull();
    //noinspection DataFlowIssue
    try (Storage s = options.getService()) {
      doTest(s);
    }
  }

  @Test
  public void closingAnOpenedSessionWithoutCallingWriteShouldMakeAnEmptyObject()
      throws IOException, ExecutionException, InterruptedException, TimeoutException {
    BlobInfo info = BlobInfo.newBuilder(bucket, generator.randomObjectName()).build();
    BlobWriteSession session = storage.blobWriteSession(info, BlobWriteOption.doesNotExist());

    WritableByteChannel open = session.open();
    open.close();
    BlobInfo gen1 = session.getResult().get(1, TimeUnit.SECONDS);

    assertThat(gen1.getSize()).isEqualTo(0);
  }

  @Test
  public void attemptingToOpenASessionWhichResultsInFailureShouldThrowAStorageException() {
    // attempt to write to a bucket which we have not created
    String badBucketName = bucket.getName() + "x";
    BlobInfo info = BlobInfo.newBuilder(badBucketName, generator.randomObjectName()).build();

    BlobWriteSession session = storage.blobWriteSession(info, BlobWriteOption.doesNotExist());
    StorageException se = assertThrows(StorageException.class, () -> session.open().close());

    assertThat(se.getCode()).isEqualTo(404);
    assertThat(se).hasMessageThat().contains(badBucketName);
  }

  private void doTest(Storage underTest) throws Exception {
    BlobWriteSession sess =
        underTest.blobWriteSession(
            BlobInfo.newBuilder(bucket, generator.randomObjectName()).build(),
            BlobWriteOption.doesNotExist());

    byte[] bytes = DataGenerator.base64Characters().genBytes(512 * 1024);
    try (WritableByteChannel w = sess.open()) {
      w.write(ByteBuffer.wrap(bytes));
    }

    BlobInfo gen1 = sess.getResult().get(10, TimeUnit.SECONDS);

    byte[] allBytes = storage.readAllBytes(gen1.getBlobId());

    assertThat(allBytes).isEqualTo(bytes);
  }
}
