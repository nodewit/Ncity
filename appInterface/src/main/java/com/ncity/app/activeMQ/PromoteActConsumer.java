package com.ncity.app.activeMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ncity.app.service.BlockchainService;


/**
 * 消费者
 * @author 艾克
 * @date 2018年10月30日
 */
@Component
public class PromoteActConsumer {
	
	@Autowired
	private BlockchainService blockchainService;
	
	@JmsListener(destination = "test", containerFactory = "jmsListenerContainerTopic")
	public void receiveTopic(String text) {
		//System.out.println("Topic Consumer1:" + text);
	}

	@JmsListener(destination = "test", containerFactory = "jmsListenerContainerTopic")
	public void receiveTopic2(String text) {
		//System.out.println("Topic Consumer2:" + text);
	}

	@JmsListener(destination = "diamond", containerFactory = "jmsListenerContainerQueue")
	public void reviceQueue(String text) {
		JSONObject json = JSONObject.parseObject(text);
		blockchainService.updateDiamondLog(json.getString("uuid"), json.getInteger("diamondLogId"));
	}
}
