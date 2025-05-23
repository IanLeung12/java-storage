/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/storage/v2/storage.proto

package com.google.storage.v2;

public interface StartResumableWriteResponseOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.storage.v2.StartResumableWriteResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The upload_id of the newly started resumable write operation. This
   * value should be copied into the `WriteObjectRequest.upload_id` field.
   * </pre>
   *
   * <code>string upload_id = 1;</code>
   *
   * @return The uploadId.
   */
  java.lang.String getUploadId();
  /**
   *
   *
   * <pre>
   * The upload_id of the newly started resumable write operation. This
   * value should be copied into the `WriteObjectRequest.upload_id` field.
   * </pre>
   *
   * <code>string upload_id = 1;</code>
   *
   * @return The bytes for uploadId.
   */
  com.google.protobuf.ByteString getUploadIdBytes();
}
