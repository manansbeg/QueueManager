package org.vertexCover.LazyCalculator;

public class Calculator  {
	
	
	public Calculation add(int a , int b , int time ) {
		
//		try {
//			//Thread.sleep(time*1000);
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
//		}
//		
		
		Calculation calculation = new Calculation();
    	calculation.setA(a);
    	calculation.setB(b);
    	
    	calculation.setOperation("add");
    	calculation.setResult(a+b);
    	
		return calculation;
		
	}
	

}