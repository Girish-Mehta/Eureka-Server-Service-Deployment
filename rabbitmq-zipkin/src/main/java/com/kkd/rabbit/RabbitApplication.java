package com.kkd.rabbit;



import java.io.IOException;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableRabbit
public class RabbitApplication {

	//name of exchange to be created
	public static final String EXCHANGE_NAME = "appExchange";
	//name of generic queue to be created
	public static final String QUEUE_GENERIC_NAME = "appGenericQueue";
	//name of specific to be created
	public static final String QUEUE_SPECIFIC_NAME = "appSpecificQueue";
	//name of routing to be created
	public static final String ROUTING_KEY = "messages.key";

	public static void main(String[] args) {
		SpringApplication.run(RabbitApplication.class, args);
	}
	
	//for sleuth to generate id
	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}
	
	//creating exchange
	@Bean
	public TopicExchange appExchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	//creating generic queue
	@Bean
	public Queue appQueueGeneric() {
		return new Queue(QUEUE_GENERIC_NAME);
	}
	//creating specific queue
	@Bean
	public Queue appQueueSpecific() {
		return new Queue(QUEUE_SPECIFIC_NAME);
	}

	//binding generic queue with exchange with a routing key
	@Bean
	public Binding declareBindingGeneric() {
		return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
	}

	//binding specific queue with exchange with a routing key
	@Bean
	public Binding declareBindingSpecific() {
		return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with(ROUTING_KEY);
	}
	
	//For swagger to document the Service
	@Bean
	public Docket api() throws IOException, XmlPullParserException {
		return new Docket(DocumentationType.SWAGGER_2);
	}

}