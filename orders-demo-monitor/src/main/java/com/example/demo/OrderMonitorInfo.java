package com.example.demo;

/**
 * Simple POJO to transfer some configuration and status data about the generator
 * 
 * @author dmcintyre
 *
 */
public class OrderMonitorInfo {
	public String rabbitHost;
	public int messagesProcessed = 0;
	public String getRabbitHost() {
		return rabbitHost;
	}
	public void setRabbitHost(String rabbitHost) {
		this.rabbitHost = rabbitHost;
	}
	public int getMessagesProcessed() {
		return messagesProcessed;
	}
	public void setMessagesProcessed(int messagesProcessed) {
		this.messagesProcessed = messagesProcessed;
	}
}
