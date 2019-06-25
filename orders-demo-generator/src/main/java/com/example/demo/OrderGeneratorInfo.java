package com.example.demo;

/**
 * Simple POJO to transfer some configuration and status data about the generator
 * 
 * @author dmcintyre
 *
 */
public class OrderGeneratorInfo {
	
	public String rabbitHost;
	public boolean streaming = false;
	public boolean stopped = false;
	public int messagesSent = 0;
	
	public int getMessagesSent() {
		return messagesSent;
	}

	public void setMessagesSent(int messagesSent) {
		this.messagesSent = messagesSent;
	}

	public boolean isStreaming() {
		return streaming;
	}

	public void setStreaming(boolean streaming) {
		this.streaming = streaming;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public void setRabbitHost(String host) {
		rabbitHost = host;
	}
	
	public String getRabbitHost() {
		return rabbitHost;
	}
}
