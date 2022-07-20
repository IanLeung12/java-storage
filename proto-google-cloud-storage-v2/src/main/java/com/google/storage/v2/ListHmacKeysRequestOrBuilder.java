/*
 * Copyright 2020 Google LLC
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

public interface ListHmacKeysRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.storage.v2.ListHmacKeysRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required. The project to list HMAC keys for, in the format of
   * "projects/&lt;projectIdentifier&gt;".
   * &lt;projectIdentifier&gt; can be the project ID or project number.
   * </pre>
   *
   * <code>
   * string project = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The project.
   */
  java.lang.String getProject();
  /**
   *
   *
   * <pre>
   * Required. The project to list HMAC keys for, in the format of
   * "projects/&lt;projectIdentifier&gt;".
   * &lt;projectIdentifier&gt; can be the project ID or project number.
   * </pre>
   *
   * <code>
   * string project = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The bytes for project.
   */
  com.google.protobuf.ByteString getProjectBytes();

  /**
   *
   *
   * <pre>
   * Optional. The maximum number of keys to return.
   * </pre>
   *
   * <code>int32 page_size = 2 [(.google.api.field_behavior) = OPTIONAL];</code>
   *
   * @return The pageSize.
   */
  int getPageSize();

  /**
   *
   *
   * <pre>
   * Optional. A previously returned token from ListHmacKeysResponse to get the next page.
   * </pre>
   *
   * <code>string page_token = 3 [(.google.api.field_behavior) = OPTIONAL];</code>
   *
   * @return The pageToken.
   */
  java.lang.String getPageToken();
  /**
   *
   *
   * <pre>
   * Optional. A previously returned token from ListHmacKeysResponse to get the next page.
   * </pre>
   *
   * <code>string page_token = 3 [(.google.api.field_behavior) = OPTIONAL];</code>
   *
   * @return The bytes for pageToken.
   */
  com.google.protobuf.ByteString getPageTokenBytes();

  /**
   *
   *
   * <pre>
   * Optional. If set, filters to only return HMAC keys for specified service account.
   * </pre>
   *
   * <code>string service_account_email = 4 [(.google.api.field_behavior) = OPTIONAL];</code>
   *
   * @return The serviceAccountEmail.
   */
  java.lang.String getServiceAccountEmail();
  /**
   *
   *
   * <pre>
   * Optional. If set, filters to only return HMAC keys for specified service account.
   * </pre>
   *
   * <code>string service_account_email = 4 [(.google.api.field_behavior) = OPTIONAL];</code>
   *
   * @return The bytes for serviceAccountEmail.
   */
  com.google.protobuf.ByteString getServiceAccountEmailBytes();

  /**
   *
   *
   * <pre>
   * Optional. If set, return deleted keys that have not yet been wiped out.
   * </pre>
   *
   * <code>bool show_deleted_keys = 5 [(.google.api.field_behavior) = OPTIONAL];</code>
   *
   * @return The showDeletedKeys.
   */
  boolean getShowDeletedKeys();
}
