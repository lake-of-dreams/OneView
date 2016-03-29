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
package com.rsinghal.cep.sample.elasticstorm.bolt;

import java.util.Map;

import com.rsinghal.cep.sample.elasticstorm.common.EsConfig;
import com.rsinghal.cep.sample.elasticstorm.common.StormElasticSearchClient;
import org.elasticsearch.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import static org.apache.storm.guava.base.Preconditions.*;

public abstract class AbstractEsBolt extends BaseRichBolt {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEsBolt.class);

    protected static Client client;

    protected OutputCollector collector;
    private EsConfig esConfig;

    public AbstractEsBolt(EsConfig esConfig) {
        checkNotNull(esConfig);
        this.esConfig = esConfig;
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        try {
            this.collector = outputCollector;
            synchronized (AbstractEsBolt.class) {
                if (client == null) {
                    client = new StormElasticSearchClient(esConfig).construct();
                }
            }
        } catch (Exception e) {
            LOG.warn("unable to initialize EsBolt ", e);
        }
    }

    @Override
    public abstract void execute(Tuple tuple);

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }

    
    static Client getClient() {
        return AbstractEsBolt.client;
    }

  
    static void replaceClient(Client client) {
        AbstractEsBolt.client = client;
    }
}
