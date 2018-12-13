package org.vertexCover.QueueManagement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import org.vertexCover.LazyCalculator.Calculation;


public class QueueManager {
	
	private static Map results =new ConcurrentHashMap<String,Result>();
	
	public static Map getResults() throws InterruptedException {
		
		return results;
	}
	
	public static int expectedResultNumber=0;


	TaskRunner taskRunner=null;
	
	public  QueueManager(){
		
     
	}
	public void publishMessage(String taskQueueName,String host,String message) throws IOException, TimeoutException, InterruptedException {
		taskRunner = new TaskRunner();
		expectedResultNumber++;
		taskRunner.runTask(taskQueueName, host, message);
	}
	
	
	public void configWorker(String taskQueueName,String host) throws IOException, TimeoutException {
		
		Worker worker = new Worker();
		worker.work(taskQueueName, host);
		
		
	}
	

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		
		
		
	//How to use	
	QueueManager queueManager =new QueueManager();
	queueManager.configWorker("test","localhost");
	queueManager.configWorker("test","localhost");
	queueManager.configWorker("test","localhost");
	
	queueManager.publishMessage("test","localhost", "18 2 1000 1");
	queueManager.publishMessage("test","localhost", "20 4 1000 2");
	queueManager.publishMessage("test","localhost", "34 4 1000 3");
	Map map =  getResults();
   
     
	Iterator mapIter = map.values().iterator();
   
	while(mapIter.hasNext())
	{
		Calculation curr = (Calculation)mapIter.next();
		System.out.println(curr.toString());
		
	}
	
	
	}

}