package org.vertexCover.QueueManagement;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.vertexCover.LazyCalculator.Calculation;
import org.vertexCover.LazyCalculator.Calculator;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class Worker {
	Result result=null;
	int id;
	public void work(String taskQueueName, String host) throws IOException, TimeoutException {
	 
	
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(host);
	    final Connection connection = factory.newConnection();
	    final Channel channel = connection.createChannel();

	    channel.queueDeclare(taskQueueName, true, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	    channel.basicQos(1);

	    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	        String message = new String(delivery.getBody(), "UTF-8");

	        System.out.println("Worker" +" "+ id+ " [x] Received '" + message + "'");
	        try {
	        	String[] arr =message.split(" ");
	        	int a = Integer.parseInt(arr[0]);
	        	int b = Integer.parseInt(arr[1]);
	        	int time = Integer.parseInt(arr[2]);
	        	 id = Integer.parseInt(arr[3]);
	        
	   Calculator calculator = new Calculator();
	   result= calculator.add(a, b, time);
	        	
	        
	        	
	        
	        	
	        
	        } finally {
	        	
	           System.out.println("Worker" +" "+ id+ " [x] Done");
	           try {
				
				QueueManager.getResults().put(id, result);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        	
	            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	           
	        }
	    };
	    channel.basicConsume(taskQueueName, false, deliverCallback, consumerTag -> { });
	    
	    
	    
	  }
	
}
