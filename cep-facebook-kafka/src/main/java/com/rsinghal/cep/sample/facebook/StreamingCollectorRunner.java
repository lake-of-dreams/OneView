package com.rsinghal.cep.sample.facebook;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StreamingCollectorRunner {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");

		StreamCollector obj12 = (StreamCollector) context
				.getBean("filterStreamCollector");
		obj12.start();
	}

}
