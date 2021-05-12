package com.quickbooks.profileservice.store;

import com.quickbooks.profileservice.producer.InMemoryQueuePublisher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryQueuPublisherTest {

    InMemoryQueuePublisher publisher ;

    @Mock
    BlockingQueue<String> queue;

    @Before
    public void setUp(){
        publisher = new InMemoryQueuePublisher();
        publisher.setQueue(queue);
    }

    //insert successfully in the queue
    @Test
    public void insertMessageIntoQueue() throws InterruptedException{
        Mockito.doReturn(true).when(queue).offer(Mockito.any(), Mockito.anyLong(), Mockito.any(TimeUnit.class));
        publisher.publish("messageId123", "Test Message");
        Mockito.verify(queue, Mockito.times(1)).offer(Mockito.any(), Mockito.anyLong(), Mockito.any(TimeUnit.class));
    }

    //queue is full, so insert fails
    @Test(expected = Exception.class)
    public void insertionToQueueFails() throws InterruptedException{
        Mockito.doReturn(false).when(queue).offer(Mockito.any(), Mockito.anyLong(), Mockito.any(TimeUnit.class));
        publisher.publish("messageId123", "Test Message");
        Mockito.verify(queue, Mockito.times(1)).offer(Mockito.any(), Mockito.anyLong(), Mockito.any(TimeUnit.class));
    }


    //exception occurs while inserting into the queue
    @Test(expected =  RuntimeException.class)
    public void exceptionOccursInQueueService() throws InterruptedException{
        Mockito.doThrow(InterruptedException.class).when(queue).offer(Mockito.any(), Mockito.anyLong(), Mockito.any(TimeUnit.class));
        publisher.publish("messageId123", "Test Message");
        Mockito.verify(queue, Mockito.times(1)).offer(Mockito.any(), Mockito.anyLong(), Mockito.any(TimeUnit.class));
    }

}
