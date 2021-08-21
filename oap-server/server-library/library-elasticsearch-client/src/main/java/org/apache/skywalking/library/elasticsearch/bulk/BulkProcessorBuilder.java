/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.skywalking.library.elasticsearch.bulk;

import com.linecorp.armeria.client.WebClient;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.library.elasticsearch.requests.factory.RequestFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
public final class BulkProcessorBuilder {
    private final CompletableFuture<RequestFactory> requestFactory;
    private final WebClient client;

    private int bulkActions = -1;
    private Duration flushInterval;
    private int concurrentRequests = 1;

    // TODO: backoff

    public BulkProcessorBuilder bulkActions(int bulkActions) {
        checkArgument(bulkActions > 0, "bulkActions must be positive");
        this.bulkActions = bulkActions;
        return this;
    }

    public BulkProcessorBuilder flushInterval(Duration flushInterval) {
        this.flushInterval = requireNonNull(flushInterval, "flushInterval");
        return this;
    }

    public BulkProcessorBuilder concurrentRequests(int concurrentRequests) {
        checkArgument(concurrentRequests > 0, "concurrentRequests must be positive");
        this.concurrentRequests = concurrentRequests;
        return this;
    }

    public BulkProcessor build() {
        return new BulkProcessor(
            requestFactory, client, bulkActions, flushInterval, concurrentRequests);
    }
}