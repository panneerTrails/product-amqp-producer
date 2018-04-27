package com.amqp.producer.product.delegate;

import com.amqp.producer.product.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDelegate.class);

    @Value("${product.routingkey.name}")
    private String routingKey;

    @Value("${product.exchange.name}")
    private  String  exchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean sendMessage(Product product){
        LOGGER.info("Inside ProductDelegate::sendMessage:::before sending rabbitmq message");
        try{
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(product)).setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
            rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
            LOGGER.info("Inside ProductDelegate::sendMessage:::after sending rabbitmq message");
            return true;
        }
        catch (Exception ex) {
            LOGGER.error("Unable to serialize product entry", ex);
            return false;
        }

    }
}
