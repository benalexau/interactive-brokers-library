package com.blogspot.mikelaud.ibl.task.event.market_data;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.event.EventTaskEx;

/**
 * This event is called when the market data changes.
 * Prices are updated immediately with no delay.
 */
public class OnTickPrice
	extends EventTaskEx<OnTickPrice.Info>
{
	//------------------------------------------------------------------------
	public static class Info {
	
		/**
		 * The ticker Id that was specified previously in the CallReqMktData.
		 */
		public final int REQ_ID;
		/**
		 * Specifies the type of price.
		 * Pass the field value into TickType.getField(int tickType)
		 * to retrieve the field description.
		 * For example, a field value of 1 will map to bidPrice,
		 * a field value of 2 will map to askPrice, etc.
		 *     1 = bid
		 *     2 = ask
		 *     4 = last
		 *     6 = high
		 *     7 = low
		 *     9 = close
		 */
		public final int FIELD;
		/**
		 * Specifies the price for the specified field.
		 */
		public final double PRICE;
		/**
		 * Specifies whether the price tick is available
		 * for automatic execution. Possible values are:
		 *     0 = not eligible for automatic execution
		 *     1 = eligible for automatic execution
		 */
		public final int CAN_AUTO_EXECUTE;
		
		public Info
		(	int aReqId
		,	int aField
		,	double aPrice
		,	int aCanAutoExecute
		) {
			REQ_ID = aReqId;
			FIELD = aField;
			PRICE = aPrice;
			CAN_AUTO_EXECUTE = aCanAutoExecute;
		}
		
	}
	//------------------------------------------------------------------------

	@Override
	public int getRequestId() {
		return INFO.REQ_ID;
	}

	@Override
	protected Task onEvent() throws Exception {
		return null;
	}

	@Override
	public String toString() {
		return String.format
		(	"%s(%f) { field=\"%d\" canAutoExecute=\"%d\" }"
		,	super.toString()
		,	INFO.PRICE
		,	INFO.FIELD
		, 	INFO.CAN_AUTO_EXECUTE
		);
	}
	
	public OnTickPrice(ConnectionContext aContext, Info aInfo) {
		super(aContext, aInfo, new TaskInnerObject(){});
	}

}
