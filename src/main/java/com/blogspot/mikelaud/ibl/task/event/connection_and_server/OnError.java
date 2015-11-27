package com.blogspot.mikelaud.ibl.task.event.connection_and_server;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.event.EventTaskEx;

/**
 * This event is called when there is an error with the communication or
 * when TWS wants to send a message to the client.
 */
public class OnError
	extends EventTaskEx<OnError.Info>
{
	//------------------------------------------------------------------------
	public static class Info {
	
		/**
		 * This is the orderId or tickerId of the request
		 * that generated the error.
		 */
		public final int REQ_ID;
		/**
		 * For information on error codes, see Error Codes.
		 */
		public final int ERROR_CODE;
		/**
		 * The textual description of the error.
		 */
		public final String ERROR_STRING;

		public Info(int aReqId, int aErrorCode, String aErrorString) {
			REQ_ID = aReqId;
			ERROR_CODE = aErrorCode;
			ERROR_STRING = aErrorString;
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
		(	"%s(%d) { \"%s\" }"
		,	super.toString()
		,	INFO.ERROR_CODE
		,	INFO.ERROR_STRING
		);
	}
	
	public OnError(ConnectionContext aContext, Info aInfo) {
		super(aContext, aInfo, new TaskInnerObject(){});
	}

}
