package com.blogspot.mikelaud.ibl.command;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.blogspot.mikelaud.ibl.Config;
import com.blogspot.mikelaud.ibl.Logger;
import com.blogspot.mikelaud.ibl.router.Router;
import com.blogspot.mikelaud.ibl.router.RouterImpl;
import com.blogspot.mikelaud.ibl.task.call.CallKind;
import com.blogspot.mikelaud.ibl.task.call.CallTask;
import com.blogspot.mikelaud.ibl.task.event.EventTask;

public class CommandImpl implements Command {

	private final BlockingQueue<EventTask> QUEUE;
	private final Router ROUTER;
	//
	private long mEventsCount;
	private long mHistoricalEventsCount;
	//
	private long mTimeoutMs;
	//
	private long mBeginTimeMs;
	private long mWaitTimeMs;
	
	private void resetTimeout() {
		mBeginTimeMs = System.currentTimeMillis();
		mWaitTimeMs = mTimeoutMs;
	}
	
	private boolean reachTimeout() {
		long timeMs = System.currentTimeMillis();
		if (timeMs < mBeginTimeMs) { // guard
			timeMs = mBeginTimeMs;
		}
		long deltaTimeMs = timeMs - mBeginTimeMs;
		if (deltaTimeMs >= mTimeoutMs) {
			mWaitTimeMs = 0;
			return true;
		}
		else {
			mWaitTimeMs = mTimeoutMs - deltaTimeMs;
			return false;
		}
	}
	
	@Override
	public Router getRouter() {
		return ROUTER;
	}
	
	@Override
	public void incrementEvents() {
		mEventsCount++;
	}

	@Override
	public void incrementHistoricalEvents() {
		incrementEvents();
		mHistoricalEventsCount++;
	}

	@Override
	public long getEventsCount() {
		return mEventsCount;
	}

	@Override
	public long getHistoricalEventsCount() {
		return mHistoricalEventsCount;
	}
	
	@Override
	public void notifyMe(EventTask aEvent) {
		if (! QUEUE.offer(aEvent)) {
			aEvent.logLost();
		}
	}

	@Override
	public void callBefore(CallTask aCall) throws Exception {
		Logger.logCommandBegin(aCall.getRequestId(), toString(aCall));
		CallKind callKind = aCall.getCallKind();
		if (CallKind.NOCAST != callKind) {
			aCall.getContext().addUnicastCall(aCall);
			aCall.getCallType().getContext(callKind).addCommand(aCall);
		}
	}
	
	@Override
	public void callAfter(CallTask aCall) throws Exception {
		CallKind callKind = aCall.getCallKind();
		if (CallKind.NOCAST != callKind) {
			resetTimeout();
			for (;;) {
				EventTask eventTask = QUEUE.poll(mWaitTimeMs, TimeUnit.MILLISECONDS);
				if (null != eventTask) {
					if (ROUTER.notifyMe(eventTask)) {
						eventTask.logEvent(this);
					}
					else {
						eventTask.logLost();
					}
					if (ROUTER.isDone()) {
						break;
					}
				}
				if (reachTimeout()) {
					break;
				}
			}
			aCall.getCallType().getContext(callKind).removeCommand(aCall);
			aCall.getContext().removeUnicastCall(aCall);
		}
		Logger.logCommandEnd(aCall.getRequestId(), toString(aCall));
	}
		
	@Override
	public long getTimeout(TimeUnit aTimeoutUnit) {
		return aTimeoutUnit.convert(mTimeoutMs, TimeUnit.MILLISECONDS);
	}

	@Override
	public void setTimeout(long aTimeout, TimeUnit aTimeoutUnit) {
		mTimeoutMs = TimeUnit.MILLISECONDS.convert(aTimeout, aTimeoutUnit);
	}

	@Override
	public String toString(CallTask aCall) {
		return String.format
		(	"%s.%s"
		,	aCall.getCallKind().toString()
		,	aCall.toString()
		);
	}
	
	public CommandImpl() {
		QUEUE = new LinkedBlockingQueue<>();
		ROUTER = new RouterImpl();
		//
		mEventsCount = 0;
		mHistoricalEventsCount = 0;
		//
		mTimeoutMs = TimeUnit.MILLISECONDS.convert
		(	Config.getDefaultTimeoutSec()
		,	TimeUnit.SECONDS
		);
		//
		mBeginTimeMs = 0;
		mWaitTimeMs = 0;
		resetTimeout();
	}
	
}
