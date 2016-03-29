package com.rsinghal.cep.sample.twitter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StreamingCollectorRunner1 {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext1.xml");

		StreamCollector1 obj12 = (StreamCollector1) context
				.getBean("filterStreamCollector");
		obj12.start();
	}

}
