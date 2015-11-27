package com.blogspot.mikelaud.ibl.task.event.fundamental_data;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.event.EventTaskEx;

/**
 * This event is called to receive Reuters global fundamental market data.
 * There must be a subscription to Reuters Fundamental set up
 * in Account Management before you can receive this data.
 */
public class OnFundamentalData
	extends EventTaskEx<OnFundamentalData.Info>
{
	//------------------------------------------------------------------------
	public static class Info {
	
		/**
		 * The ID of the data request.
		 */
		public final int  REQ_ID;
		/**
		 * One of these XML reports:
		 *     Company overview
		 *     Financial summary
		 *     Financial ratios
		 *     Financial statements
		 *     Analyst estimates
		 *     Company calendar
		 */
		public final String DATA;
		
		public Info(int aReqId, String aData) {
			REQ_ID = aReqId;
			DATA = aData;
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
		(	"%s { data=\"%s\" }"
		,	super.toString()
		,	INFO.DATA
		);
	}
	
	public OnFundamentalData(ConnectionContext aContext, Info aInfo) {
		super(aContext, aInfo, new TaskInnerObject(){});
	}

}
