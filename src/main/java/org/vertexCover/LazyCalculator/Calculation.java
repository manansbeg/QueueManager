package org.vertexCover.LazyCalculator;

import org.vertexCover.QueueManagement.Result;

public class Calculation implements Result {
	
	int a;
	int b;
	String operation;
	String id;
	int result;

	public void setResult(int result) {
		this.result = result;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String string) {
		this.operation = string;
	}
	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Calculation [a=" + a + ", b=" + b + ", operation=" + operation + ", id=" + id + ", result=" + result
				+ "]";
	}
	public void setId(String id2) {
		this.id = id2;
	}
	
	public String getResult() {
	
		
		return toString();
		
	}
	

	
}