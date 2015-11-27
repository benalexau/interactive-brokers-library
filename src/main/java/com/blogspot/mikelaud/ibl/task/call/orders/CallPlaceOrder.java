package com.blogspot.mikelaud.ibl.task.call.orders;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.call.CallTaskEx;
import com.ib.client.Contract;
import com.ib.client.Order;

/**
 * Call this call to place an order.
 */
public class CallPlaceOrder
	extends CallTaskEx<CallPlaceOrder.In>
{
	@Override
	public boolean hasRequestId() {
		return false;
	}
	//------------------------------------------------------------------------
	public static class In {
	
		/**
		 * The order Id. You must specify a unique value.
		 * When the order status returns, it will be identified by this tag.
		 * This tag is also used when canceling the order.
		 */
		public final int ORDER_ID;
		/**
		 * This class contains attributes used to describe the contract.
		 */
		public final Contract CONTRACT;
		/**
		 * This structure contains the details of the order.
		 * Note: Each client MUST connect with a unique clientId.
		 */
		public final Order ORDER;
		
		public In(int aOrderId, Contract aContract, Order aOrder) {
			ORDER_ID = aOrderId;
			CONTRACT = aContract;
			ORDER = aOrder;
		}
		
	}
	//------------------------------------------------------------------------

	@Override
	protected Task onCall() throws Exception {
		getClientSocket().placeOrder
		(	IN.ORDER_ID
		,	IN.CONTRACT
		,	IN.ORDER
		);
		return null;
	}

	@Override
	public String toString() {
		return String.format
		(	"%s(%d)"
		,	super.toString()
		,	IN.ORDER_ID
		);
	}

	public CallPlaceOrder(ConnectionContext aContext, In aIn) {
		super(aContext, aIn, new TaskInnerObject(){});
	}

	public CallPlaceOrder
	(	ConnectionContext aContext
	,	int aOrderId
	,	Contract aContract
	,	Order aOrder
	) {
		this(aContext, new In
		(	aOrderId
		,	aContract
		,	aOrder
		));
	}

}
