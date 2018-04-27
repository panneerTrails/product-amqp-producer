package com.amqp.producer.product.controller;

import com.amqp.producer.product.delegate.ProductDelegate;
import com.amqp.producer.product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ProductAMQPProducer")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    ProductDelegate productDelegate;

    public ProductController(ProductDelegate productDelegate) {
        this.productDelegate = productDelegate;
    }

    @PostMapping(value="/sendEvent")
    public ResponseEntity sendMessage(@RequestBody Product product) {
        LOGGER.info("Inside ProductController::sendMessage");
        if (productDelegate.sendMessage(product)){
            LOGGER.info("Inside ProductController::sendMessage");
            return new ResponseEntity(product, HttpStatus.OK);

        }else{
            return new ResponseEntity(product, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
