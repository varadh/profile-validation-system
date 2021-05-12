package com.quickbooks.profileservice.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class InMemoryQueuePublisher implements iDataPublisher {

    @Resource(name="in-memory-queue")
    BlockingQueue<String> queue ;

    Logger logger = LoggerFactory.getLogger(InMemoryQueuePublisher.class);

    @Override
    public void init(){

    }

    @Override
    public void publish(String messageId, String message) {
        try{
            logger.info("Publishing message to queue: {}",messageId);
            logger.info("Queue size is: {}", queue.size());
            boolean result = queue.offer(message, 2000, TimeUnit.MILLISECONDS);
            if(!result){
                throw new Exception("Could not publish message to the queue:"+messageId);
            }
        }catch (Exception exception){
            throw new RuntimeException("Exception occurred while publishing the data to the queue:"+exception.getMessage());
        }
    }

    public void setQueue(BlockingQueue<String> queue){
        this.queue = queue;
    }

}
