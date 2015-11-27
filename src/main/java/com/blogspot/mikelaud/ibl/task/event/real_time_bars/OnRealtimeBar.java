package com.blogspot.mikelaud.ibl.task.event.real_time_bars;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.event.EventTaskEx;
import com.blogspot.mikelaud.ibl.types.IblDouble;
import com.blogspot.mikelaud.ibl.types.IblTimeZone;

/**
 * This event receives the real-time bars data results.
 */
public class OnRealtimeBar
	extends EventTaskEx<OnRealtimeBar.Info>
{
	//------------------------------------------------------------------------
	public static class Info {
	
		/**
		 * The ticker ID of the request to which this bar is responding.
		 */
		public final int REQ_ID;
		/**
		 * The date-time stamp of the start of the bar. The format
		 * is determined by the CallReqHistoricalData formatDate parameter.
		 */
		public final long TIME;
		/**
		 * The bar opening price.
		 */
		public final double OPEN;
		/**
		 * The high price during the time covered by the bar.
		 */
		public final double HIGH;
		/**
		 * The low price during the time covered by the bar.
		 */
		public final double LOW;
		/**
		 * The bar closing price.
		 */
		public final double CLOSE;
		/**
		 * The volume during the time covered by the bar.
		 */
		public final long VOLUME;
		/**
		 * The weighted average price during the time covered by the bar.
		 */
		public final double WAP;
		/**
		 * When TRADES historical data is returned, represents the number
		 * of trades that occurred during the time period the bar covers.
		 */
		public final int COUNT;
		
		public Info
		(	int aReqId
		,	long aTime
		,	double aOpen
		,	double aHigh
		,	double aLow
		,	double aClose
		,	long aVolume
		,	double aWAP
		,	int aCount
		) {
			REQ_ID = aReqId;
			TIME = aTime;
			OPEN = aOpen;
			HIGH = aHigh;
			LOW = aLow;
			CLOSE = aClose;
			VOLUME = aVolume;
			WAP = aWAP;
			COUNT = aCount;
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
		(	"%s { time=%s open=%s high=%s low=%s close=%s volume=%d count=%d wap=%s }"
		,	super.toString()
		,	IblTimeZone.NEW_YORK.getRealtimeBarTime(INFO.TIME).toString()
		,	IblDouble.toString(INFO.OPEN)
		,	IblDouble.toString(INFO.HIGH)
		,	IblDouble.toString(INFO.LOW)
		,	IblDouble.toString(INFO.CLOSE)
		,	INFO.VOLUME
		,	INFO.COUNT
		,	IblDouble.toString(INFO.WAP)
		);
	}
	
	public OnRealtimeBar(ConnectionContext aContext, Info aInfo) {
		super(aContext, aInfo, new TaskInnerObject(){});
	}

}
