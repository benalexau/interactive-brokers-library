package com.blogspot.mikelaud.ibl.task.event.executions;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.event.EventTaskEx;
import com.ib.client.Contract;
import com.ib.client.Execution;

/**
 * This event is called when the CallReqExecutions call is invoked,
 * or when an order is filled.
 */
public class OnExecDetails
	extends EventTaskEx<OnExecDetails.Info>
{
	//------------------------------------------------------------------------
	public static class Info {
	
		/**
		 * The reqID that was specified previously
		 * in the call to CallReqExecution.
		 */
		public final int REQ_ID;
		/**
		 * This structure contains a full description
		 * of the contract that was executed.
		 * Note: Refer to the Java SocketClient Properties page
		 *       for more information:
		 * https://www.interactivebrokers.com/en/software/api/apiguide/java/java_socketclient_properties.htm
		 */
		public final Contract CONTRACT;
		/**
		 * This structure contains addition order execution details.
		 */
		public final Execution EXECUTION;
		
		public Info
		(	int aReqId
		,	Contract aContract
		,	Execution aExecution
		) {
			REQ_ID = aReqId;
			CONTRACT = aContract;
			EXECUTION = aExecution;
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
		(	"%s"
		,	super.toString()
		);
	}
	
	public OnExecDetails(ConnectionContext aContext, Info aInfo) {
		super(aContext, aInfo, new TaskInnerObject(){});
	}

}
