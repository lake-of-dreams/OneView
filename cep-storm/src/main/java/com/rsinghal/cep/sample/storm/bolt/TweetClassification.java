package com.rsinghal.cep.sample.storm.bolt;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Computed;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.Objects;

	@Table(keyspace = "spark_streaming_twitter_1", name = "tweets_classification")
	public class TweetClassification {
	  
	
		   public Long id;
		   public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getSentiment() {
			return sentiment;
		}


		public void setSentiment(String sentiment) {
			this.sentiment = sentiment;
		}


		public String getClassification() {
			return classification;
		}


		public void setClassification(String classification) {
			this.classification = classification;
		}


		public Double getSentiment_score() {
			return sentiment_score;
		}


		public void setSentiment_score(Double sentiment_score) {
			this.sentiment_score = sentiment_score;
		}


		public Double getClassification_score() {
			return classification_score;
		}


		public void setClassification_score(Double classification_score) {
			this.classification_score = classification_score;
		}


		public String sentiment;
		   public String classification;
		   public Double sentiment_score;
		   public Double classification_score;
		  

		public TweetClassification() {
	    }

	   
	}


