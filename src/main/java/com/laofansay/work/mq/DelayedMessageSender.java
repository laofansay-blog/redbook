package com.laofansay.work.mq;

import com.laofansay.work.config.RabbitConfig;
import com.laofansay.work.web.rest.AuthorityResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 */
@Component
public class DelayedMessageSender {

    private static final Logger log = LoggerFactory.getLogger(DelayedMessageSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelayedMessage(String message, int delay) {
        log.info("send message={},DELAYED={}w ", message, delay);
        rabbitTemplate.convertAndSend(RabbitConfig.DELAYED_EXCHANGE, RabbitConfig.DELAYED_QUEUE, message, msg -> {
            msg.getMessageProperties().setHeader("x-delay", delay);
            return msg;
        });
    }
}
