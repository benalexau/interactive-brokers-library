package com.blogspot.mikelaud.ibl.task.call.historical_data;

import com.blogspot.mikelaud.ibl.connection.ConnectionContext;
import com.blogspot.mikelaud.ibl.out.OutEvents;
import com.blogspot.mikelaud.ibl.out.OutEnd;
import com.blogspot.mikelaud.ibl.task.Task;
import com.blogspot.mikelaud.ibl.task.TaskInnerObject;
import com.blogspot.mikelaud.ibl.task.call.CallTaskEx;
import com.blogspot.mikelaud.ibl.task.event.historical_data.OnHistoricalData;
import com.blogspot.mikelaud.ibl.task.event.historical_data.OnHistoricalDataEnd;
import com.blogspot.mikelaud.ibl.types.IblBarSize;
import com.blogspot.mikelaud.ibl.types.IblDuration;
import com.blogspot.mikelaud.ibl.types.IblEndDateTime;
import com.blogspot.mikelaud.ibl.types.IblFormatDate;
import com.blogspot.mikelaud.ibl.types.IblString;
import com.blogspot.mikelaud.ibl.types.IblUseRth;
import com.blogspot.mikelaud.ibl.types.IblWhatToShow;
import com.blogspot.mikelaud.ibl.types.common.IblSymbol;
import com.ib.client.Contract;

/**
 * Call the CallReqHistoricalData call to start receiving
 * historical data results through the OnHistoricalData EWrapper event.
 * 
 * Note: For more information about historical data request limitations,
 *       see Historical Data Limitations:
 * https://www.interactivebrokers.com/en/software/api/apiguide/tables/historical_data_limitations.htm
 */
public class CallReqHistoricalData
	extends CallTaskEx<CallReqHistoricalData.In>
{
	/**
	 * The Id for the request. Must be a unique value. When the data is
	 * received, it will be identified by this Id. This is also used when
	 * canceling the historical data request.
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
		 * Use the format yyyymmdd hh:mm:ss tmz, where the time zone is
		 * allowed (optionally) after a space at the end.
		 */
		public final String END_DATE_TIME;
		/**
		 * This is the time span the request will cover, and is specified
		 * using the format: <integer> <unit>,
		 * i.e., 1 D, where valid units are:
		 *     " S (seconds)
		 *     " D (days)
		 *     " W (weeks)
		 *     " M (months)
		 *     " Y (years)
		 * If no unit is specified, seconds are used.
		 * Also, note "years" is currently limited to one.
		 */
		public final String DURATION_STR;
		/**
		 * Specifies the size of the bars that will be returned
		 * (within IB/TWS limits). Valid bar size values include:
		 *     1 sec
		 *     5 secs
		 *     15 secs
		 *     30 secs
		 *     1 min
		 *     2 mins
		 *     3 mins
		 *     5 mins
		 *     15 mins
		 *     30 mins
		 *     1 hour
		 *     1 day
		 */
		public final String BAR_SIZE_SETTING;
		/**
		 * Determines the nature of data being extracted.
		 * Valid values include:
		 *     TRADES
		 *     MIDPOINT
		 *     BID
		 *     ASK
		 *     BID_ASK
		 *     HISTORICAL_VOLATILITY
		 *     OPTION_IMPLIED_VOLATILITY
		 */
		public final String WHAT_TO_SHOW;
		/**
		 * Determines whether to return all data available during
		 * the requested time span, or only data that falls within
		 * regular trading hours. Valid values include:
		 *     0 - all data is returned even where the market in question
		 *         was outside of its regular trading hours.
		 *     1 - only data within the regular trading hours is returned,
		 *         even if the requested time span falls partially or
		 *         completely outside of the RTH.
		 */
		public final int USE_RTH;
		/**
		 * Determines the date format applied to returned bars.
		 * Valid values include:
		 *     1 - dates applying to bars returned in the format:
		 *         yyyymmdd{space}{space}hh:mm:dd
		 *     2 - dates are returned as a long integer specifying the number
		 *         of seconds since 1/1/1970 GMT.
		 */
		public final int FORMAT_DATE;
				
		public In
		(	Contract aContract
		,	IblEndDateTime aEndDateTime
		,	IblDuration aDuration
		,	IblBarSize aBarSize
		,	IblWhatToShow aWhatToShow
		,	IblUseRth aUseRTH
		,	IblFormatDate aFormatDate
		) {
			CONTRACT = aContract;
			END_DATE_TIME = aEndDateTime.getValue();
			DURATION_STR = aDuration.getName();
			BAR_SIZE_SETTING = aBarSize.getName();
			WHAT_TO_SHOW = aWhatToShow.getName();
			USE_RTH = aUseRTH.getIntegerValue();
			FORMAT_DATE = aFormatDate.getValue();
		}
		
	}
	//------------------------------------------------------------------------

	public final OutEvents<OnHistoricalData> OUT_HISTORICAL_DATA;
	public final OutEnd<OnHistoricalDataEnd> OUT_HISTORICAL_DATA_END;
	
	//------------------------------------------------------------------------
	
	@Override
	protected Task onCall() throws Exception {
		getClientSocket().reqHistoricalData
		(	getRequestId()
		,	IN.CONTRACT
		,	IN.END_DATE_TIME
		,	IN.DURATION_STR
		,	IN.BAR_SIZE_SETTING
		,	IN.WHAT_TO_SHOW
		,	IN.USE_RTH
		,	IN.FORMAT_DATE
		);
		return null;
	}

	@Override
	public String toString() {
		return String.format
		(	"%s(\"%s/%s/%s/%s/%s\") { endDateTime=\"%s\" durationStr=\"%s\" barSizeSetting=\"%s\" whatToShow=\"%s\" useRth=%d formatDate=%d }"
		,	super.toString()
		,	IblString.nvl(IN.CONTRACT.m_symbol)
		,	IblString.nvl(IN.CONTRACT.m_secType)
		,	IblString.nvl(IN.CONTRACT.m_currency)
		,	IblString.nvl(IN.CONTRACT.m_exchange)
		,	IblString.nvl(IN.CONTRACT.m_primaryExch)
		,	IblString.nvl(IN.END_DATE_TIME)
		,	IblString.nvl(IN.DURATION_STR)
		,	IblString.nvl(IN.BAR_SIZE_SETTING)
		,	IblString.nvl(IN.WHAT_TO_SHOW)
		,	IN.USE_RTH
		,	IN.FORMAT_DATE
		);
	}

	public CallReqHistoricalData(ConnectionContext aContext, In aIn) {
		super(aContext, aIn, new TaskInnerObject(){});
		OUT_HISTORICAL_DATA = new OutEvents<>(this, OnHistoricalData.class);
		OUT_HISTORICAL_DATA_END = new OutEnd<>(this, OnHistoricalDataEnd.class);
	}

	public CallReqHistoricalData
	(	ConnectionContext aContext
	,	IblBarSize aBarSize
	,	IblSymbol aSymbol
	,	IblEndDateTime aEndDateTime
	) {
		this(aContext, new In
		(	new Contract()
		,	aEndDateTime
		,	aBarSize.getHistoricalLimit()
		,	aBarSize
		,	IblWhatToShow.TRADES
		,	IblUseRth.RTH_DATA
		,	IblFormatDate.UNIX_TIME_SEC
		));
		IN.CONTRACT.m_symbol = aSymbol.getName();
		IN.CONTRACT.m_secType = aSymbol.getSecurityType().getName();
		IN.CONTRACT.m_currency = aSymbol.getCurrency().getName();
		IN.CONTRACT.m_exchange = aSymbol.getExchange().getName();
		IN.CONTRACT.m_primaryExch = aSymbol.getPrimaryExchange().getName();
	}

}
