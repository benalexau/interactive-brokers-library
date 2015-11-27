package com.blogspot.mikelaud.ibl.task.call.market_depth;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.out.OutStream;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.call.CallTaskEx;
import com.blogspot.mikelaud.ibl.task.event.market_depth.OnUpdateMktDepth;
import com.blogspot.mikelaud.ibl.task.event.market_depth.OnUpdateMktDepthL2;
import com.ib.client.Contract;

/**
 * Call this call to request market depth for a specific contract.
 * The market depth will be returned by the OnUpdateMktDepth
 * and OnUpdateMktDepthL2 events.
 */
public class CallReqMktDepth
	extends CallTaskEx<CallReqMktDepth.In>
{
	/**
	 * The ticker Id. Must be a unique value. When the market depth data
	 * returns, it will be identified by this tag. This is also used when
	 * canceling the market depth.
	 */
	@Override
	public boolean hasRequestId() {
		return true;
	}
	//------------------------------------------------------------------------
	public static class In {
	
		/**
		 * This class contains attributes used to describe the contract.
		 */
		public final Contract CONTRACT;
		/**
		 * Specifies the number of market depth rows to return.
		 */
		public final int NUM_ROWS;
		
		public In
		(	Contract aContract
		,	int aNumRows
		) {
			CONTRACT = aContract;
			NUM_ROWS = aNumRows;
		}
		
	}
	//------------------------------------------------------------------------

	public final OutStream<OnUpdateMktDepth> STREAM_UPDATE_MKT_DEPTH;
	public final OutStream<OnUpdateMktDepthL2> STREAM_UPDATE_MKT_DEPTH_L2;
	
	//------------------------------------------------------------------------
	
	@Override
	protected Task onCall() throws Exception {
		getClientSocket().reqMktDepth
		(	getRequestId()
		,	IN.CONTRACT
		,	IN.NUM_ROWS
		);
		return null;
	}

	@Override
	public String toString() {
		return String.format
		(	"%s { numRows=\"%d\" }"
		,	super.toString()
		,	IN.NUM_ROWS
		);
	}

	public CallReqMktDepth(ConnectionContext aContext, In aIn) {
		super(aContext, aIn, new TaskInnerObject(){});
		STREAM_UPDATE_MKT_DEPTH = new OutStream<>(this, OnUpdateMktDepth.class);
		STREAM_UPDATE_MKT_DEPTH_L2 = new OutStream<>(this, OnUpdateMktDepthL2.class);
	}

	public CallReqMktDepth
	(	ConnectionContext aContext
	,	Contract aContract
	,	int aNumRows
	) {
		this(aContext, new In
		(	aContract
		,	aNumRows
		));
	}

}
