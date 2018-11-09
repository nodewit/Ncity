package com.ncity.app.activeMQ;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 生成者
 * @author 艾克
 * @date 2018年10月30日
 */
@Service
public class PromoteActProducer {
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private Queue queue;
	@Autowired
	private Topic topic;
	
	public void send(String text) {
		this.jmsMessagingTemplate.convertAndSend(this.queue, text);
	}
}
