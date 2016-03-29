package com.rsinghal.cep.sample.storm.bolt;

import java.util.Map;

import org.apache.avro.Schema;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseBasicBolt;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import com.rsinghal.cep.sample.twitter.avro.v11.TwitterStatusUpdate;

public abstract class CassandraBaseBoltNew extends BaseBasicBolt {
	transient Cluster cluster;
	transient Session session;
	protected String cassandraHost;
	protected String keySpace;
	Schema schema;
	Mapper<?> mapper ;

	public CassandraBaseBoltNew(String cassandraHost, String keySpace) {
		this.cassandraHost = cassandraHost;
		this.keySpace = keySpace;
		
	}

	public CassandraBaseBoltNew() {
		super();
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		cluster = Cluster.builder().addContactPoint(cassandraHost).build();
		session = cluster.connect(keySpace);
		
		super.prepare(stormConf, context);
	}

}