package com.blogspot.mikelaud.ibl.out;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.blogspot.mikelaud.ibl.task.call.CallTask;
import com.blogspot.mikelaud.ibl.task.event.EventTask;

public class OutEvents<EVENT_TASK extends EventTask> extends OutAbstract<EVENT_TASK> {

	private Queue<EVENT_TASK> mEventTasks = new ConcurrentLinkedQueue<EVENT_TASK>(); 
	
	@Override
	protected void addEvent(EVENT_TASK aEventTask) {
		super.setEvent(aEventTask);
		mEventTasks.add(aEventTask);
	}
	
	public Queue<EVENT_TASK> getEvents() {
		return mEventTasks;
	}
	
	@Override
	public OutType getOutType() {
		return OutType.EVENTS;
	}

	public OutEvents(CallTask aCallTask, Class<EVENT_TASK> aEventClass) {
		super(aCallTask, aEventClass);
	}

}
