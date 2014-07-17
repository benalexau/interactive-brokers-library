package com.blogspot.mikelaud.ibl.task.call.market_depth;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.call.CallTaskEx;
import com.blogspot.mikelaud.ibl.task.call.CallType;

/**
 * After calling this call,
 * market depth data for the specified Id will stop flowing.
 */
public class CallCancelMktDepth
	extends CallTaskEx<CallCancelMktDepth.In>
{
	//------------------------------------------------------------------------
	public static class In {
	
		/**
		 * The Id that was specified in the call to CallReqMktDepth.
		 */
		public final int TICKER_ID;
		
		public In(int aTickerId) {
			TICKER_ID = aTickerId;
		}
		
	}
	//------------------------------------------------------------------------

	@Override
	protected Task onCall() throws Exception {
		getClientSocket().cancelMktDepth(IN.TICKER_ID);
		return null;
	}

	@Override
	public String toString() {
		return String.format
		(	"%s[%d]"
		,	super.toString()
		,	IN.TICKER_ID
		);
	}

	public CallCancelMktDepth(ConnectionContext aContext, In aIn) {
		super(aContext, aIn, CallType.cancelMktDepth);
	}

	public CallCancelMktDepth(ConnectionContext aContext, int aTickerId) {
		this(aContext, new In(aTickerId));
	}

}
