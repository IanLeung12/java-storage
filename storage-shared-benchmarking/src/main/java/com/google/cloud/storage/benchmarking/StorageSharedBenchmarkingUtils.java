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
package com.google.cloud.storage.benchmarking;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import java.time.Duration;

class StorageSharedBenchmarkingUtils {
  public static long SSB_SIZE_THRESHOLD_BYTES = 1048576;
  public static int DEFAULT_NUMBER_OF_READS = 3;

  public static void cleanupObject(Storage storage, Blob created) {
    storage.delete(
        created.getBlobId(), Storage.BlobSourceOption.generationMatch(created.getGeneration()));
  }

  public static double calculateThroughput(double size, Duration elapsedTime) {
    double adjustedSize =
        size >= StorageSharedBenchmarkingUtils.SSB_SIZE_THRESHOLD_BYTES
            ? (size / 1024D) / 1024D
            : size / 1024D;
    double throughput = adjustedSize / (elapsedTime.toMillis() / 1000D);
    return throughput;
  }
}
