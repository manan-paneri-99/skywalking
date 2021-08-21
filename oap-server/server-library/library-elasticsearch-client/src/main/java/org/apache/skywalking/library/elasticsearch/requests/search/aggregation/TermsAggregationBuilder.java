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

package org.apache.skywalking.library.elasticsearch.requests.search.aggregation;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public final class TermsAggregationBuilder implements AggregationBuilder {
    private final String name;

    private String field;
    private BucketOrder order;
    private Integer size;
    private ImmutableList.Builder<Aggregation> subAggregations;

    TermsAggregationBuilder(final String name) {
        checkArgument(!Strings.isNullOrEmpty(name), "name cannot be blank");
        this.name = name;
    }

    public TermsAggregationBuilder field(String field) {
        checkArgument(!Strings.isNullOrEmpty(field), "field cannot be blank");
        this.field = field;
        return this;
    }

    public TermsAggregationBuilder order(BucketOrder order) {
        requireNonNull(order, "order");
        this.order = order;
        return this;
    }

    public TermsAggregationBuilder size(int size) {
        checkArgument(size >= 0, "size must be >= 0");
        this.size = size;
        return this;
    }

    public TermsAggregationBuilder subAggregation(Aggregation subAggregation) {
        requireNonNull(subAggregation, "subAggregation");
        subAggregations().add(subAggregation);
        return this;
    }

    public TermsAggregationBuilder subAggregation(AggregationBuilder subAggregationBuilder) {
        requireNonNull(subAggregationBuilder, "subAggregationBuilder");
        return subAggregation(subAggregationBuilder.build());
    }

    @Override
    public TermsAggregation build() {
        List<Aggregation> subAggregations;
        if (this.subAggregations == null) {
            subAggregations = null;
        } else {
            subAggregations = this.subAggregations.build();
        }
        return new TermsAggregation(
            field, order, size, subAggregations
        );
    }

    private ImmutableList.Builder<Aggregation> subAggregations() {
        if (subAggregations == null) {
            subAggregations = ImmutableList.builder();
        }
        return subAggregations;
    }
}
