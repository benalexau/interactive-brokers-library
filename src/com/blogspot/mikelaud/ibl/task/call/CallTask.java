package com.blogspot.mikelaud.nyse.task.call;

import java.util.concurrent.TimeUnit;

import com.blogspot.mikelaud.nyse.Logger;
import com.blogspot.mikelaud.nyse.connection.ConnectionContext;
import com.blogspot.mikelaud.nyse.task.Task;
import com.blogspot.mikelaud.nyse.task.command.Command;
import com.blogspot.mikelaud.nyse.task.command.CommandFactory;

/**
 * EClientSocket calls you use when connecting to TWS.
 */
public abstract class CallTask extends Task {

	protected final CallType CALL_TYPE;

	private Command mCommand;
	private Integer mRequestId;
	
	public CallType getCallType() { return CALL_TYPE; }
	
	public int getRequestId() {
		if (null == mRequestId) {
			mRequestId = mContext.nextRequestId();
		}
		return mRequestId.intValue();
	}
	
	protected abstract Task onCall() throws Exception;

	@Override
	public Task call() throws Exception {
		Logger.logCall(toString());
		return onCall();
	}

	public Command getCommand(long aTimeout, TimeUnit aTimeUnit) {
		if (null == mCommand) {
			mCommand = CommandFactory.createCommand(mContext, this);
		}
		mCommand.setTimeout(aTimeout, aTimeUnit);
		return mCommand;
	}
	
	public Command getCommand() {
		return getCommand(0, TimeUnit.SECONDS);
	}
	
	public Task callCommand(long aTimeout, TimeUnit aTimeUnit) throws Exception {
		return getCommand(aTimeout, aTimeUnit).call();
	}
	
	public Task callCommand() throws Exception {
		return getCommand().call();
	}
	
	@Override
	public String toString() {
		return CALL_TYPE.toString();
	}
	
	public CallTask(ConnectionContext aContext, CallType aCallType) {
		super(aContext);
		CALL_TYPE = aCallType;
		mCommand = null;
		mRequestId = null;
	}

}