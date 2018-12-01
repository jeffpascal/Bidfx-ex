/*
 * Copyright (c) 2018. BidFX Systems Limited. All rights reserved.
 */

package com.bidfx.orderbook;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * This class represents an order book for a share or stock. An {@code OrderBook}
 * should retain the state of the share, keeping track of the orders that are in
 * the market.
 *
 * @author BidFX Systems Limited
 */
@SuppressWarnings("all")
public class OrderBook {
	// TODO Implement your custom logic here
	private static TreeMap<Double,Object> orderBookBid;

	public static TreeMap<Double, Object> update = new TreeMap<Double, Object>();
	
	public OrderBook(){
		orderBookBid = new TreeMap(Collections.reverseOrder());
	}

	public void add(Order order) {
		
		if (orderBookBid.containsKey(order.getPrice())){
			try {
				long newValue = (long)order.getSize() + (long)orderBookBid.get(order.getPrice());
				
				orderBookBid.remove(order.getPrice());
				orderBookBid.put(order.getPrice(), newValue);
				update.put(order.getPrice(),newValue);
			}
			catch (Exception ex){
				System.out.println(" failed to replace");
			}
		}
		else
		{
			orderBookBid.put(order.getPrice(), order.getSize());
			update.put(order.getPrice(), order.getSize());		
		}
		
		
		
	}
	
	public static Map<String, Object> getOrderBook(){
		TreeMap<String,Object> newTreeMap = new TreeMap<String, Object>();
		TreeMap<String,Object> toSend = new TreeMap<String, Object>();
		int level = 0;
		for(Entry<Double, Object> entry : orderBookBid.entrySet()) {
			level++;
			Object key = entry.getKey();
			Object value = entry.getValue();
			if(level != 1){
				newTreeMap.put(PriceFields.BID.concat(String.valueOf(level)), (double)key);
				newTreeMap.put(PriceFields.BID_SIZE.concat(String.valueOf(level)), (long)value);
			}
			else if (level == 1){	
				newTreeMap.put((PriceFields.BID), (double)key);
				newTreeMap.put((PriceFields.BID_SIZE), (long)value);
			}
		}
		
		return newTreeMap;
	}
}
