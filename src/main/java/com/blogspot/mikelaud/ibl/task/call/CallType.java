package com.blogspot.mikelaud.ibl.task.call;

import java.util.concurrent.atomic.AtomicReference;

import com.blogspot.mikelaud.ibl.router.context.Context;
import com.blogspot.mikelaud.ibl.router.context.ContextNocast;
import com.blogspot.mikelaud.ibl.task.TaskGroup;
import com.blogspot.mikelaud.ibl.task.TaskType;
import com.blogspot.mikelaud.ibl.task.call.account_and_portfolio.CallCancelAccountSummary;
import com.blogspot.mikelaud.ibl.task.call.account_and_portfolio.CallCancelPositions;
import com.blogspot.mikelaud.ibl.task.call.account_and_portfolio.CallReqAccountSummary;
import com.blogspot.mikelaud.ibl.task.call.account_and_portfolio.CallReqAccountUpdates;
import com.blogspot.mikelaud.ibl.task.call.account_and_portfolio.CallReqPositions;
import com.blogspot.mikelaud.ibl.task.call.connection_and_server.CallConnect;
import com.blogspot.mikelaud.ibl.task.call.connection_and_server.CallDisconnect;
import com.blogspot.mikelaud.ibl.task.call.connection_and_server.CallIsConnected;
import com.blogspot.mikelaud.ibl.task.call.connection_and_server.CallReqCurrentTime;
import com.blogspot.mikelaud.ibl.task.call.connection_and_server.CallServerVersion;
import com.blogspot.mikelaud.ibl.task.call.connection_and_server.CallSetServerLogLevel;
import com.blogspot.mikelaud.ibl.task.call.connection_and_server.CallTwsConnectionTime;
import com.blogspot.mikelaud.ibl.task.call.contract_details.CallReqContractDetails;
import com.blogspot.mikelaud.ibl.task.call.executions.CallReqExecutions;
import com.blogspot.mikelaud.ibl.task.call.financial_advisors.CallReplaceFA;
import com.blogspot.mikelaud.ibl.task.call.financial_advisors.CallReqManagedAccts;
import com.blogspot.mikelaud.ibl.task.call.financial_advisors.CallRequestFA;
import com.blogspot.mikelaud.ibl.task.call.fundamental_data.CallCancelFundamentalData;
import com.blogspot.mikelaud.ibl.task.call.fundamental_data.CallReqFundamentalData;
import com.blogspot.mikelaud.ibl.task.call.historical_data.CallCancelHistoricalData;
import com.blogspot.mikelaud.ibl.task.call.historical_data.CallReqHistoricalData;
import com.blogspot.mikelaud.ibl.task.call.market_data.CallCalculateImpliedVolatility;
import com.blogspot.mikelaud.ibl.task.call.market_data.CallCalculateOptionPrice;
import com.blogspot.mikelaud.ibl.task.call.market_data.CallCancelCalculateImpliedVolatility;
import com.blogspot.mikelaud.ibl.task.call.market_data.CallCancelCalculateOptionPrice;
import com.blogspot.mikelaud.ibl.task.call.market_data.CallCancelMktData;
import com.blogspot.mikelaud.ibl.task.call.market_data.CallReqMarketDataType;
import com.blogspot.mikelaud.ibl.task.call.market_data.CallReqMktData;
import com.blogspot.mikelaud.ibl.task.call.market_depth.CallCancelMktDepth;
import com.blogspot.mikelaud.ibl.task.call.market_depth.CallReqMktDepth;
import com.blogspot.mikelaud.ibl.task.call.market_scanners.CallCancelScannerSubscription;
import com.blogspot.mikelaud.ibl.task.call.market_scanners.CallReqScannerParameters;
import com.blogspot.mikelaud.ibl.task.call.market_scanners.CallReqScannerSubscription;
import com.blogspot.mikelaud.ibl.task.call.news_bulletins.CallCancelNewsBulletins;
import com.blogspot.mikelaud.ibl.task.call.news_bulletins.CallReqNewsBulletins;
import com.blogspot.mikelaud.ibl.task.call.orders.CallCancelOrder;
import com.blogspot.mikelaud.ibl.task.call.orders.CallExerciseOptions;
import com.blogspot.mikelaud.ibl.task.call.orders.CallPlaceOrder;
import com.blogspot.mikelaud.ibl.task.call.orders.CallReqAllOpenOrders;
import com.blogspot.mikelaud.ibl.task.call.orders.CallReqAutoOpenOrders;
import com.blogspot.mikelaud.ibl.task.call.orders.CallReqGlobalCancel;
import com.blogspot.mikelaud.ibl.task.call.orders.CallReqIDs;
import com.blogspot.mikelaud.ibl.task.call.orders.CallReqOpenOrders;
import com.blogspot.mikelaud.ibl.task.call.other.CallSleep;
import com.blogspot.mikelaud.ibl.task.call.real_time_bars.CallCancelRealTimeBars;
import com.blogspot.mikelaud.ibl.task.call.real_time_bars.CallReqRealTimeBars;

/**
 * EClientSocket calls enum.
 */
public enum CallType implements TaskType {

	//========================================================================
	// Other
	//------------------------------------------------------------------------
	sleep
	(	CallSleep.class
	,	TaskGroup.Other
	,	"Create pause."
	),
	//========================================================================
	// Connection and Server
	//------------------------------------------------------------------------
	connect
	(	CallConnect.class
	,	TaskGroup.ConnectionAndServer
	,	"Establish the connection with TWS."
	),
	disconnect
	(	CallDisconnect.class
	,	TaskGroup.ConnectionAndServer
	,	"Terminate the connection with TWS."
	),
	isConnected
	(	CallIsConnected.class
	,	TaskGroup.ConnectionAndServer
	,	"Check if there is a connection with TWS."
	),
	setServerLogLevel
	(	CallSetServerLogLevel.class
	,	TaskGroup.ConnectionAndServer
	,	"Set server log level."
	),
	reqCurrentTime
	(	CallReqCurrentTime.class
	,	TaskGroup.ConnectionAndServer
	,	"Request current system time on the server side."
	),
	serverVersion
	(	CallServerVersion.class
	,	TaskGroup.ConnectionAndServer
	,	"Get version of the TWS instance to which the API application is connected."
	),
	twsConnectionTime
	(	CallTwsConnectionTime.class
	,	TaskGroup.ConnectionAndServer
	,	"Get time the API application made a connection to TWS."
	),
	//========================================================================
	// Market Data
	//------------------------------------------------------------------------
	reqMktData
	(	CallReqMktData.class
	,	TaskGroup.MarketData
	,	"Request market data."
	),
	cancelMktData
	(	CallCancelMktData.class
	,	TaskGroup.MarketData
	,	"Stop flowing market data for the specified Id."
	),
	calculateImpliedVolatility
	(	CallCalculateImpliedVolatility.class
	,	TaskGroup.MarketData
	,	"Calculate volatility for a supplied option price and underlying price."
	),
	cancelCalculateImpliedVolatility
	(	CallCancelCalculateImpliedVolatility.class
	,	TaskGroup.MarketData
	,	"Cancel a request to calculate volatility for a supplied option price and underlying price."
	),
	calculateOptionPrice
	(	CallCalculateOptionPrice.class
	,	TaskGroup.MarketData
	,	"Calculate option price and greek values for a supplied volatility and underlying price."
	),
	cancelCalculateOptionPrice
	(	CallCancelCalculateOptionPrice.class
	,	TaskGroup.MarketData
	,	"Cancel a request to calculate the option price and greek values for a supplied volatility and underlying price."
	),
	reqMarketDataType
	(	CallReqMarketDataType.class
	,	TaskGroup.MarketData
	,	"Telling TWS to automatically switch to frozen market data after the close."
	),
	//========================================================================
	// Orders
	//------------------------------------------------------------------------
	placeOrder
	(	CallPlaceOrder.class
	,	TaskGroup.Orders
	,	"Place an order."
	),
	cancelOrder
	(	CallCancelOrder.class
	,	TaskGroup.Orders
	,	"Cancel an order."
	),
	reqOpenOrders
	(	CallReqOpenOrders.class
	,	TaskGroup.Orders
	,	"Request any open orders that were placed from this API client."
	),
	reqAllOpenOrders
	(	CallReqAllOpenOrders.class
	,	TaskGroup.Orders
	,	"Request all open orders that were placed from all API clients linked to one TWS, and also from the TWS."
	),
	reqAutoOpenOrders
	(	CallReqAutoOpenOrders.class
	,	TaskGroup.Orders
	,	"Request that newly created TWS orders be implicitly associated with the client."
	),
	reqIDs
	(	CallReqIDs.class
	,	TaskGroup.Orders
	,	"Request the next valid ID that can be used when placing an order."
	),
	exerciseOptions
	(	CallExerciseOptions.class
	,	TaskGroup.Orders
	,	"Exercise options."
	),
	reqGlobalCancel
	(	CallReqGlobalCancel.class
	,	TaskGroup.Orders
	,	"Cancel all open orders globally: both API and TWS open orders."
	),
	//========================================================================
	// Account and Portfolio
	//------------------------------------------------------------------------
	reqAccountUpdates
	(	CallReqAccountUpdates.class
	,	TaskGroup.AccountAndPortfolio
	,	"Start getting account values, portfolio, and last update time information."
	),
	reqAccountSummary
	(	CallReqAccountSummary.class
	,	TaskGroup.AccountAndPortfolio
	,	"Request and keep up to date the data that appears on the TWS Account Window Summary tab."
	),
	cancelAccountSummary
	(	CallCancelAccountSummary.class
	,	TaskGroup.AccountAndPortfolio
	,	"Cancels the request for Account Window Summary tab data."
	),
	reqPositions
	(	CallReqPositions.class
	,	TaskGroup.AccountAndPortfolio
	,	"Requests real-time position data for all accounts."
	),
	cancelPositions
	(	CallCancelPositions.class
	,	TaskGroup.AccountAndPortfolio
	,	"Cancels real-time position updates."
	),
	//========================================================================
	// Contract Details
	//------------------------------------------------------------------------
	reqContractDetails
	(	CallReqContractDetails.class
	,	TaskGroup.ContractDetails
	,	"Download all details for a particular contract."
	),
	//========================================================================
	// Executions
	//------------------------------------------------------------------------
	reqExecutions
	(	CallReqExecutions.class
	,	TaskGroup.Executions
	,	"Request execution reports from the last 24 hours that meet the filter criteria."
	),
	//========================================================================
	// Market Depth
	//------------------------------------------------------------------------
	reqMktDepth
	(	CallReqMktDepth.class
	,	TaskGroup.MarketDepth
	,	"Request market depth for a specific contract."
	),
	cancelMktDepth
	(	CallCancelMktDepth.class
	,	TaskGroup.MarketDepth
	,	"Stop flowing market depth data for the specified Id."
	),
	//========================================================================
	// News Bulletins
	//------------------------------------------------------------------------
	reqNewsBulletins
	(	CallReqNewsBulletins.class
	,	TaskGroup.NewsBulletins
	,	"Start receiving news bulletins."
	),
	cancelNewsBulletins
	(	CallCancelNewsBulletins.class
	,	TaskGroup.NewsBulletins
	,	"Stop receiving news bulletins."
	),
	//========================================================================
	// Financial Advisors
	//------------------------------------------------------------------------
	reqManagedAccts
	(	CallReqManagedAccts.class
	,	TaskGroup.FinancialAdvisors
	,	"Request the list of managed accounts."
	),
	requestFA
	(	CallRequestFA.class
	,	TaskGroup.FinancialAdvisors
	,	"Request FA configuration information from TWS."
	),
	replaceFA
	(	CallReplaceFA.class
	,	TaskGroup.FinancialAdvisors
	,	"Request new FA configuration information from TWS."
	),
	//========================================================================
	// Historical Data
	//------------------------------------------------------------------------
	reqHistoricalData
	(	CallReqHistoricalData.class
	,	TaskGroup.HistoricalData
	,	"Start receiving historical data results."
	),
	cancelHistoricalData
	(	CallCancelHistoricalData.class
	,	TaskGroup.HistoricalData
	,	"Stop receiving historical data results."
	),
	//========================================================================
	// Market Scanners
	//------------------------------------------------------------------------
	reqScannerParameters
	(	CallReqScannerParameters.class
	,	TaskGroup.MarketScanners
	,	"Request an XML document that describes the valid parameters that a scanner subscription can have."
	),
	reqScannerSubscription
	(	CallReqScannerSubscription.class
	,	TaskGroup.MarketScanners
	,	"Start receiving market scanner results."
	),
	cancelScannerSubscription
	(	CallCancelScannerSubscription.class
	,	TaskGroup.MarketScanners
	,	"Stop receiving market scanner results."
	),
	//========================================================================
	// Real Time Bars
	//------------------------------------------------------------------------
	reqRealTimeBars
	(	CallReqRealTimeBars.class
	,	TaskGroup.RealTimeBars
	,	"Start receiving real time bar results."
	),
	cancelRealTimeBars
	(	CallCancelRealTimeBars.class
	,	TaskGroup.RealTimeBars
	,	"Stop receiving real time bar results."
	),
	//========================================================================
	// Fundamental Data
	//------------------------------------------------------------------------
	reqFundamentalData
	(	CallReqFundamentalData.class
	,	TaskGroup.FundamentalData
	,	"Start receiving Reuters global fundamental data."
	),
	cancelFundamentalData
	(	CallCancelFundamentalData.class
	,	TaskGroup.FundamentalData
	,	"Stop receiving Reuters global fundamental data."
	);
	//========================================================================
	
	private final Class<? extends CallTask> TASK_CLASS;
	private final TaskGroup GROUP;
	private final String NAME;
	private final String DESCRIPTION;
	private final ContextNocast DEFAULT_CONTEXT;
	private AtomicReference<Context> mContext;

	private CallType
	(	Class<? extends CallTask> aTaskClass
	,	TaskGroup aGroup
	,	String aDescription
	) {
		TASK_CLASS = aTaskClass;
		GROUP = aGroup;
		NAME = name();
		DESCRIPTION = aDescription;
		DEFAULT_CONTEXT = new ContextNocast();
		mContext = new AtomicReference<>(null);
	}
	
	@Override
	public Class<? extends CallTask> getTaskClass() {
		return TASK_CLASS;
	}
	
	@Override
	public TaskGroup getGroup() {
		return GROUP;
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getDescription() {
		return DESCRIPTION;
	}
		
	public Context getContext(CallKind aCallKind) {
		Context context = mContext.get();
		if (null == context) {
			if (null == aCallKind) {
				context = DEFAULT_CONTEXT;
			}
			else {
				Context newContext = aCallKind.createContext();
				mContext.compareAndSet(null, newContext);
				context = mContext.get();
			}
		}
		return context;
	}
	
	@Override
	public String toString() {
		return String.format("%s.%s", GROUP, NAME);
	}

}
