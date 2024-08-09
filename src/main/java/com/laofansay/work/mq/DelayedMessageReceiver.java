package com.laofansay.work.mq;

import com.laofansay.work.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * 消息接收者
 */
@Component
public class DelayedMessageReceiver {

    private static final Logger log = LoggerFactory.getLogger(DelayedMessageSender.class);

    @RabbitListener(queues = RabbitConfig.DELAYED_QUEUE)
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            // 处理消息的业务逻辑
            log.info("Received delayed message: " + message);
            // 手动确认消息
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 处理失败，拒绝消息并重新入队
            channel.basicNack(tag, false, true);
        }
    }
}
