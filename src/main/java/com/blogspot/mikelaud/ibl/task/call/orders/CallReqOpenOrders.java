package com.blogspot.mikelaud.ibl.task.call.orders;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.out.OutEvents;
import com.blogspot.mikelaud.ibl.out.OutEnd;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.call.CallTaskEx;
import com.blogspot.mikelaud.ibl.task.event.orders.OnOpenOrder;
import com.blogspot.mikelaud.ibl.task.event.orders.OnOpenOrderEnd;
import com.blogspot.mikelaud.ibl.task.event.orders.OnOrderStatus;

/**
 * Call this call to request any open orders
 * that were placed from this API client.
 * 
 * Each open order will be fed back through the EventOpenOrder
 * and OnOrderStatus events on the EWrapper.
 * 
 * Note: The client with a clientId of "0"
 *       will also receive the TWS-owned open orders.
 *       These orders will be associated with the client
 *       and a new orderId will be generated.
 *       This association will persist over multiple API and TWS sessions.
 */
public class CallReqOpenOrders
	extends CallTaskEx<CallReqOpenOrders.In>
{
	@Override
	public boolean hasRequestId() {
		return false;
	}
	//------------------------------------------------------------------------
	public static class In {
	
		public In() {
			// void
		}
		
	}
	//------------------------------------------------------------------------
	
	public final OutEvents<OnOrderStatus> OUT_ORDER_STATUS;
	public final OutEvents<OnOpenOrder> OUT_OPEN_ORDER;
	public final OutEnd<OnOpenOrderEnd> OUT_OPEN_ORDER_END;

	//------------------------------------------------------------------------
	
	@Override
	protected Task onCall() throws Exception {
		getClientSocket().reqOpenOrders();
		return null;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	private CallReqOpenOrders(ConnectionContext aContext, In aIn) {
		super(aContext, aIn, new TaskInnerObject(){});
		OUT_ORDER_STATUS = new OutEvents<>(this, OnOrderStatus.class);
		OUT_OPEN_ORDER = new OutEvents<>(this, OnOpenOrder.class);
		OUT_OPEN_ORDER_END = new OutEnd<>(this, OnOpenOrderEnd.class);
	}

	public CallReqOpenOrders(ConnectionContext aContext) {
		this(aContext, new In());
	}

}
