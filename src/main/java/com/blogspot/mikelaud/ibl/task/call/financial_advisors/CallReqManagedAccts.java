package com.blogspot.mikelaud.ibl.task.call.financial_advisors;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.out.OutEnd;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.call.CallTaskEx;
import com.blogspot.mikelaud.ibl.task.event.financial_advisors.OnManagedAccounts;

/**
 * Call this call to request the list of managed accounts.
 * The list will be returned by the OnManagedAccounts event on the EWrapper.
 * 
 * Note: This request can only be made when connected
 *       to a Financial Advisor (FA) account.
 */
public class CallReqManagedAccts
	extends CallTaskEx<CallReqManagedAccts.In>
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

	public final OutEnd<OnManagedAccounts> OUT_MANAGED_ACCOUNTS;
	
	//------------------------------------------------------------------------
	
	@Override
	protected Task onCall() throws Exception {
		getClientSocket().reqManagedAccts();
		return null;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	private CallReqManagedAccts(ConnectionContext aContext, In aIn) {
		super(aContext, aIn, new TaskInnerObject(){});
		OUT_MANAGED_ACCOUNTS = new OutEnd<>(this, OnManagedAccounts.class);
	}

	public CallReqManagedAccts(ConnectionContext aContext) {
		this(aContext, new In());
	}

}
