/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rsinghal.cep.sample.elasticstorm;

import java.io.Serializable;

import java.util.Collection;

import org.elasticsearch.action.get.GetResponse;

import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/**
 * @since 0.11
 * The adapter to convert the results fetched from Elasticsearch to values.
 */
public interface EsLookupResultOutput extends Serializable {

    /**
     * @return collection of values to emit.
     */
    Collection<Values> toValues(GetResponse response);

    /**
     * @return output fields to declare.
     */
    Fields fields();
}
