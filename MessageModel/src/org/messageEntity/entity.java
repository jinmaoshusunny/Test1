package org.messageEntity;

import java.util.ArrayList;

/**
@author*
@paramater*
@return
*/
public class entity {
	
	private String model;
	private String sender;
	private String sence;
	private ArrayList<String> splitedwords;
	
	public ArrayList<String> getSplitedwords() {
		return splitedwords;
	}
	public void setSplitedwords(ArrayList<String> splitedwords) {
		this.splitedwords = splitedwords;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSence() {
		return sence;
	}
	public void setSence(String sence) {
		this.sence = sence;
	}
}
