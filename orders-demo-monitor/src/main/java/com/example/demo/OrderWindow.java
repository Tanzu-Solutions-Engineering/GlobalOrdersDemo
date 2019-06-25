package com.example.demo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.springframework.stereotype.Component;


@Component
public class OrderWindow {
	
	private Map<String,Queue<Order>> stateOrdersMap = new HashMap<String, Queue<Order>>();
	
	private int totalSeen = 0;
	
	public Set<String> getStates() {
		return stateOrdersMap.keySet();
	}
	
	public int getOrderSum(String state){	
		
		int sum = 0;
		
		Queue<Order> q  = stateOrdersMap.get(state);
		
		if(q == null) {
			return sum;
		}
		
		Iterator<Order> it = q.iterator();
		while (it.hasNext()){
			sum += it.next().getAmount();
		}
		
		return sum;
	}
	
	public int getLastOrderAmount(String state) {
		Queue<Order> q  = stateOrdersMap.get(state);
		
		if(q == null) {
			return 0;
		}
		
    	Order[] orders = q.toArray(new Order[]{});
    	return orders[orders.length-1].getAmount();
	}

	public synchronized void registerOrder(Order order){
		Queue<Order> orderQueue = stateOrdersMap.get(order.getState());
		if(orderQueue == null) {
			orderQueue = new ArrayBlockingQueue<Order>(10);
			stateOrdersMap.put(order.getState(), orderQueue);
		}
		if (!orderQueue.offer(order)){
			orderQueue.remove();
			orderQueue.add(order);
		}
		totalSeen++;
	}
	
	public int getTotalSeen() {
		return totalSeen;
	}
}