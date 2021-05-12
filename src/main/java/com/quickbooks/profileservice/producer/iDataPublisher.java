package com.quickbooks.profileservice.producer;


public interface iDataPublisher {
    public void init();
    public void publish(String messageId, String message);
}
