package com.kkd.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitApplication.class);

	//using messege sender service to send message to rabbitmq
	@Autowired
	private MessageSender sendMessageToRabbit;
	
	//a demo mapping to send msg
	@RequestMapping("/send")
	public String sendMsg(){
		String msg="sending";
		log.info("Sending message...");
		sendMessageToRabbit.produceMsg(msg);
		return "Done";
	}
	
	//a demo mapping to send msg
	@RequestMapping("/sendd")
	public String sendMsgg(){
		String msg="sendingg";
		log.info("Sending message...");
		sendMessageToRabbit.produceMsg(msg);
		return "Done";
	}
	
}
