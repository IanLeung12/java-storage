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

package com.google.cloud.storage;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.function.UnaryOperator;

final class TestClock extends Clock {

  private final Instant begin;
  private final UnaryOperator<Instant> next;

  private Instant now;

  private TestClock(Instant begin, UnaryOperator<Instant> next) {
    this.begin = begin;
    this.next = next;
    this.now = begin;
  }

  @Override
  public ZoneId getZone() {
    throw new UnsupportedOperationException("TestClock.getZone()");
  }

  @Override
  public Clock withZone(ZoneId zone) {
    throw new UnsupportedOperationException("TestClock.withZone()");
  }

  @Override
  public Instant instant() {
    Instant ret = now;
    now = next.apply(now);
    return ret;
  }

  public static TestClock tickBy(Instant begin, Duration d) {
    return of(begin, i -> i.plus(d));
  }

  public static TestClock of(Instant begin, UnaryOperator<Instant> next) {
    return new TestClock(begin, next);
  }
}
