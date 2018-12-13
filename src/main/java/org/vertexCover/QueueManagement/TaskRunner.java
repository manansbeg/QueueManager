package org.vertexCover.QueueManagement;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.MessageProperties;

public class TaskRunner {

	
	public void runTask(String taskQueueName, String host , String message) throws IOException, TimeoutException, InterruptedException {
		
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(host);
	    try (Connection connection = factory.newConnection();
	         Channel channel = connection.createChannel()) {
	        channel.queueDeclare(taskQueueName, true, false, false, null);

	        System.out.println("Publish "+message);

	        channel.basicPublish("", taskQueueName,
	                MessageProperties.PERSISTENT_TEXT_PLAIN,
	                message.getBytes("UTF-8"));
	       
	        

		
		    
		
	}

	}
}
