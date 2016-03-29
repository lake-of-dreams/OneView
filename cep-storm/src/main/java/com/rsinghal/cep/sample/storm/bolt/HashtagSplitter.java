package com.rsinghal.cep.sample.storm.bolt;

import org.apache.commons.lang.StringUtils;

import com.rsinghal.cep.sample.domain.Tweet;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class HashtagSplitter extends BaseBasicBolt {

	public HashtagSplitter() {
	}

	private String normalizeString(String input) {
		String result = input;
		result = StringUtils.lowerCase(result);
		return result;
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		Tweet tweet = (Tweet)input.getValueByField("tweet");
		for (String hashtag : tweet.getHashtags()) {
			String hashtagNormalized = normalizeString(hashtag);

			collector.emit(new Values(hashtagNormalized));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("hashtag"));
	}

}
