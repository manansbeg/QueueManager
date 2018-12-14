package org.vertexCover.QueueManagement;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.vertexCover.LazyCalculator.Calculation;
import org.vertexCover.LazyCalculator.Calculator;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


/*
 * Worker process the message. 
 * Here calculator is used for processing
 * */
public class Worker {
	Result result=null;
	int id;
	public void work(String taskQueueName, String host) throws IOException, TimeoutException {
	 
	
		
		// Connection and its channel
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(host);
	    final Connection connection = factory.newConnection();
	    final Channel channel = connection.createChannel();
        // Link the Queue to the channel
	    // Message durability has been set to true so that RabbitMQ never loses the Queue
	    // in case of any issue
	    channel.queueDeclare(taskQueueName, true, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
         
	    // Set to 1 in order to distribute fairly between workers.
	    // This means in case a worker is still processing , do not send a new message , 
	    // if another worker is free.
	    channel.basicQos(1);
         
	    // Process using the lambda function.
	    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	        String message = new String(delivery.getBody(), "UTF-8");
 
	        System.out.println("Worker" +" "+ id+ " [x] Received '" + message + "'");
	        try {
	        	
	        	// Extract messages . Here space is being used as a delimiter
	        	String[] arr =message.split(" ");
	        	int a = Integer.parseInt(arr[0]);
	        	int b = Integer.parseInt(arr[1]);
	        	int time = Integer.parseInt(arr[2]);
	        	 id = Integer.parseInt(arr[3]);
	        
	        	 // Calculate
	   Calculator calculator = new Calculator();
	   result= calculator.add(a, b, time);	
	        
	        } finally {
	        	// When done, share results
	           System.out.println("Worker" +" "+ id+ " [x] Done");
	           try {
				
				QueueManager.getResults().put(id, result);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	           //Acknowledge
	            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	           
	        }
	    };
	    
	    // Consume the message
	    channel.basicConsume(taskQueueName, false, deliverCallback, consumerTag -> { });
	    
	    
	    
	  }
	
}
