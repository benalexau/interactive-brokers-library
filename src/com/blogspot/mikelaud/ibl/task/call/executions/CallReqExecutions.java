package com.blogspot.mikelaud.ibl.task.call.executions;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.out.OutEvent;
import com.blogspot.mikelaud.ibl.out.OutEvents;
import com.blogspot.mikelaud.ibl.out.OutTerminator;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.call.CallTaskEx;
import com.blogspot.mikelaud.ibl.task.call.CallType;
import com.blogspot.mikelaud.ibl.task.event.executions.OnCommissionReport;
import com.blogspot.mikelaud.ibl.task.event.executions.OnExecDetails;
import com.blogspot.mikelaud.ibl.task.event.executions.OnExecDetailsEnd;
import com.ib.client.ExecutionFilter;

/**
 * When this call is called, the execution reports from the last 24 hours
 * that meet the filter criteria are downloaded to the client
 * via the OnExecDetails method.
 * 
 * To view executions beyond the past 24 hours, open the Trade Log in TWS and,
 * while the Trade Log is displayed, request the executions again from the API.
 */
public class CallReqExecutions
	extends CallTaskEx<CallReqExecutions.In>
{
	//------------------------------------------------------------------------
	public static class In {
	
		/**
		 * Request Id.
		 */
		public final int REQ_ID;
		/**
		 * The filter criteria used to determine
		 * which execution reports are returned.
		 */
		public final ExecutionFilter FILTER;
		
		public In
		(	int aReqId
		,	ExecutionFilter aFilter
		) {
			REQ_ID = aReqId;
			FILTER = aFilter;
		}
		
	}
	//------------------------------------------------------------------------

	public final OutEvent<OnCommissionReport> OUT_COMMISSION_REPORT;
	public final OutEvents<OnExecDetails> OUT_EXEC_DETAILS;
	public final OutTerminator<OnExecDetailsEnd> OUT_EXEC_DETAILS_END;
	
	//------------------------------------------------------------------------
	
	@Override
	protected Task onCall() throws Exception {
		getClientSocket().reqExecutions
		(	IN.REQ_ID
		,	IN.FILTER
		);
		return null;
	}

	@Override
	public String toString() {
		return String.format
		(	"%s[%d]"
		,	super.toString()
		,	IN.REQ_ID
		);
	}

	public CallReqExecutions(ConnectionContext aContext, In aIn) {
		super(aContext, aIn, CallType.reqExecutions);
		OUT_COMMISSION_REPORT = new OutEvent<OnCommissionReport>(getRouter());
		OUT_EXEC_DETAILS = new OutEvents<OnExecDetails>(getRouter());
		OUT_EXEC_DETAILS_END = new OutTerminator<OnExecDetailsEnd>(getRouter());
	}

}
