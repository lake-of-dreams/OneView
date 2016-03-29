package com.rsinghal.cep.sample.storm.bolt;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

public class Classify implements Serializable{
	String[] sentiment_categories;
	String[] classification_categories;
	@SuppressWarnings("rawtypes")
	LMClassifier sentimentLmc;
	@SuppressWarnings("rawtypes")
	LMClassifier classificationLmc;

	/**
	 * Constructor loading serialized object created by Model class to local
	 * LMClassifer of this class
	 */
	@SuppressWarnings("rawtypes")
	public Classify() {
		try {
			sentimentLmc = (LMClassifier) AbstractExternalizable.readResourceObject(this.getClass(), "/classifier.txt");
			classificationLmc = (LMClassifier) AbstractExternalizable.readResourceObject(this.getClass(), "/classifier_finance.txt");
			sentiment_categories = sentimentLmc.categories();
			classification_categories = classificationLmc.categories();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Classify whether the text is positive or negative based on Model object
	 * 
	 * @param text
	 * @return classified group i.e either positive or negative
	 */
	public TweetClassification classify(Long id,String text) {
		ConditionalClassification classification = classificationLmc.classify(text);
		ConditionalClassification sentiment = sentimentLmc.classify(text);
		TweetClassification tc = new TweetClassification();
		tc.setId(id);
		tc.setClassification(classification.bestCategory());
		tc.setClassification_score(classification.score(classification_categories.length-1));
		tc.setSentiment(sentiment.bestCategory());
		tc.setSentiment_score(sentiment.score(sentiment_categories.length-1));
		
		
		
		return tc;
	}
	
	
}
