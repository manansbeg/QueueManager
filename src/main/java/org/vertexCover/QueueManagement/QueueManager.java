package org.vertexCover.QueueManagement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import org.vertexCover.LazyCalculator.Calculation;

/**
 * 
 * 
 * The QueueManager is the client.
 * Task Runners are basically senders which can be used by the client to send the message
 * Workers are receivers and do the work
 * 
 * Here, the worker is going to calculate. 
 * The results are shared back to the QueueManager using a thread safe result map.
 * */

public class QueueManager {
	
	//Stores results by workers. It can be replaced by a suitable storage like
	// a service or a db. Thread Safe .
	private static Map results =new ConcurrentHashMap<String,Result>();
	
	public static Map getResults() throws InterruptedException {
		
		
		return results;
	}
	
	//public static int expectedResultNumber=0;


	TaskRunner taskRunner=null;
	
	public  QueueManager(){
		
     
	}
	
	// Publish the messages using the task runner
	public void publishMessage(String taskQueueName,String host,String message) throws IOException, TimeoutException, InterruptedException {
		taskRunner = new TaskRunner();
		//expectedResultNumber++;
		taskRunner.runTask(taskQueueName, host, message);
	}
	
	
	// Worker that processes the task.
	// Queue Names is the queue required by rabbitMQ.
	public void configWorker(String taskQueueName,String host) throws IOException, TimeoutException {
		
		Worker worker = new Worker();
		worker.work(taskQueueName, host);
		
		
	}
	

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		
		
		
	//How to use	
	QueueManager queueManager =new QueueManager();
	//QueueName for the workers is test and the host is local
	//This instantiates 3 workers.
	queueManager.configWorker("test","localhost");
	queueManager.configWorker("test","localhost");
	queueManager.configWorker("test","localhost");
	
	/*Task Runner handles these messages and sends them to the server for async execution
	 publish message has  - queue name , host name and message
	message has a b time id
	so 18 2 1000 1 - a = 18 , b = 2 , time =1 sec , id = 1
	IT can be replaced by JSON.
	The message is for the calculator
*/	
	queueManager.publishMessage("test","localhost", "18 2 1 1");
	queueManager.publishMessage("test","localhost", "20 4 1 2");
	queueManager.publishMessage("test","localhost", "34 4 1 3");
	
	/*
	 * This should be put on wait on some manner. This can mean a normal timer
	 * or a check on the map to see if all the given messages have been processed.
	 * A counter - expectedResults can keep a track of how many are required
	 * */
	Map map =  getResults();
   
     
	Iterator mapIter = map.values().iterator();
   
	while(mapIter.hasNext())
	{
		Calculation curr = (Calculation)mapIter.next();
		System.out.println(curr.toString());
		
	}
	
	
	}

}