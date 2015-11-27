package com.blogspot.mikelaud.ibl.connection;

import com.blogspot.mikelaud.ibl.Config;
import com.blogspot.mikelaud.ibl.task.event.account_and_portfolio.OnAccountDownloadEnd;
import com.blogspot.mikelaud.ibl.task.event.account_and_portfolio.OnAccountSummary;
import com.blogspot.mikelaud.ibl.task.event.account_and_portfolio.OnAccountSummaryEnd;
import com.blogspot.mikelaud.ibl.task.event.account_and_portfolio.OnPosition;
import com.blogspot.mikelaud.ibl.task.event.account_and_portfolio.OnPositionEnd;
import com.blogspot.mikelaud.ibl.task.event.account_and_portfolio.OnUpdateAccountTime;
import com.blogspot.mikelaud.ibl.task.event.account_and_portfolio.OnUpdateAccountValue;
import com.blogspot.mikelaud.ibl.task.event.account_and_portfolio.OnUpdatePortfolio;
import com.blogspot.mikelaud.ibl.task.event.connection_and_server.OnConnectionClosed;
import com.blogspot.mikelaud.ibl.task.event.connection_and_server.OnCurrentTime;
import com.blogspot.mikelaud.ibl.task.event.connection_and_server.OnError;
import com.blogspot.mikelaud.ibl.task.event.connection_and_server.OnErrorException;
import com.blogspot.mikelaud.ibl.task.event.connection_and_server.OnErrorString;
import com.blogspot.mikelaud.ibl.task.event.contract_details.OnBondContractDetails;
import com.blogspot.mikelaud.ibl.task.event.contract_details.OnContractDetails;
import com.blogspot.mikelaud.ibl.task.event.contract_details.OnContractDetailsEnd;
import com.blogspot.mikelaud.ibl.task.event.executions.OnCommissionReport;
import com.blogspot.mikelaud.ibl.task.event.executions.OnExecDetails;
import com.blogspot.mikelaud.ibl.task.event.executions.OnExecDetailsEnd;
import com.blogspot.mikelaud.ibl.task.event.financial_advisors.OnManagedAccounts;
import com.blogspot.mikelaud.ibl.task.event.financial_advisors.OnReceiveFA;
import com.blogspot.mikelaud.ibl.task.event.fundamental_data.OnFundamentalData;
import com.blogspot.mikelaud.ibl.task.event.historical_data.OnHistoricalData;
import com.blogspot.mikelaud.ibl.task.event.historical_data.OnHistoricalDataEnd;
import com.blogspot.mikelaud.ibl.task.event.market_data.OnMarketDataType;
import com.blogspot.mikelaud.ibl.task.event.market_data.OnTickEFP;
import com.blogspot.mikelaud.ibl.task.event.market_data.OnTickGeneric;
import com.blogspot.mikelaud.ibl.task.event.market_data.OnTickOptionComputation;
import com.blogspot.mikelaud.ibl.task.event.market_data.OnTickPrice;
import com.blogspot.mikelaud.ibl.task.event.market_data.OnTickSize;
import com.blogspot.mikelaud.ibl.task.event.market_data.OnTickSnapshotEnd;
import com.blogspot.mikelaud.ibl.task.event.market_data.OnTickString;
import com.blogspot.mikelaud.ibl.task.event.market_depth.OnUpdateMktDepth;
import com.blogspot.mikelaud.ibl.task.event.market_depth.OnUpdateMktDepthL2;
import com.blogspot.mikelaud.ibl.task.event.market_scanners.OnScannerData;
import com.blogspot.mikelaud.ibl.task.event.market_scanners.OnScannerDataEnd;
import com.blogspot.mikelaud.ibl.task.event.market_scanners.OnScannerParameters;
import com.blogspot.mikelaud.ibl.task.event.news_bulletins.OnUpdateNewsBulletin;
import com.blogspot.mikelaud.ibl.task.event.orders.OnDeltaNeutralValidation;
import com.blogspot.mikelaud.ibl.task.event.orders.OnNextValidId;
import com.blogspot.mikelaud.ibl.task.event.orders.OnOpenOrder;
import com.blogspot.mikelaud.ibl.task.event.orders.OnOpenOrderEnd;
import com.blogspot.mikelaud.ibl.task.event.orders.OnOrderStatus;
import com.blogspot.mikelaud.ibl.task.event.real_time_bars.OnRealtimeBar;
import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.UnderComp;

public class ConnectionEventsHandler implements EWrapper {
	
	private ConnectionContext mContext;
	
	public ConnectionEventsHandler(ConnectionContext aContext) {
		mContext = aContext;
	}

	//========================================================================
	// Connection and Server
	//------------------------------------------------------------------------
	
	@Override
	public void currentTime(long aUnixTimeSec) {
		mContext.onEventTask
		(	new OnCurrentTime(mContext
		,	new OnCurrentTime.Info
		(	aUnixTimeSec
		)));
	}

	@Override
	public void error(int aId, int aErrorCode, String aErrorString) {
		mContext.onEventTask
		(	new OnError(mContext
		,	new OnError.Info
		(	aId
		,	aErrorCode
		,	aErrorString
		)));
	}

	@Override
	public void error(Exception aException) {
		mContext.onEventTask
		(	new OnErrorException(mContext
		,	new OnErrorException.Info
		(	aException
		)));
	}

	@Override
	public void error(String aString) {
		mContext.onEventTask
		(	new OnErrorString(mContext
		,	new OnErrorString.Info
		(	aString
		)));
	}
	
	@Override
	public void connectionClosed() {
		mContext.onEventTask
		(	new OnConnectionClosed(mContext
		,	new OnConnectionClosed.Info
		(
		)));
	}

	//========================================================================
	// Market Data
	//------------------------------------------------------------------------

	@Override
	public void tickPrice
	(	int aTickerId
	,	int aField
	,	double aPrice
	,	int aCanAutoExecute
	) {
		mContext.onEventTask
		(	new OnTickPrice(mContext
		,	new OnTickPrice.Info
		(	aTickerId
		,	aField
		,	aPrice
		,	aCanAutoExecute
		)));
	}

	@Override
	public void tickSize(int aTickerId, int aField, int aSize) {
		mContext.onEventTask
		(	new OnTickSize(mContext
		,	new OnTickSize.Info
		(	aTickerId
		,	aField
		,	aSize
		)));
	}

	@Override
	public void tickOptionComputation
	(	int aTickerId
	,	int aField
	,	double aImpliedVol
	,	double aDelta
	,	double aOptPrice
	,	double aPvDividend
	,	double aGamma
	,	double aVega
	,	double aTheta
	,	double aUndPrice
	) {
		mContext.onEventTask
		(	new OnTickOptionComputation(mContext
		,	new OnTickOptionComputation.Info
		(	aTickerId
		,	aField
		,	aImpliedVol
		,	aDelta
		,	aOptPrice
		,	aPvDividend
		,	aGamma
		,	aVega
		,	aTheta
		,	aUndPrice		
		)));
	}

	@Override
	public void tickGeneric(int aTickerId, int aTickType, double aValue) {
		mContext.onEventTask
		(	new OnTickGeneric(mContext
		,	new OnTickGeneric.Info
		(	aTickerId
		,	aTickType
		,	aValue
		)));
	}

	@Override
	public void tickString(int aTickerId, int aTickType, String aValue) {
		mContext.onEventTask
		(	new OnTickString(mContext
		,	new OnTickString.Info
		(	aTickerId
		,	aTickType
		,	aValue
		)));
	}

	@Override
	public void tickEFP
	(	int aTickerId
	,	int aTickType
	,	double aBasisPoints
	,	String aFormattedBasisPoints
	,	double aImpliedFuture
	,	int aHoldDays
	,	String aFutureExpiry
	,	double aDividendImpact
	,	double aDividendsToExpiry
	) {
		mContext.onEventTask
		(	new OnTickEFP(mContext
		,	new OnTickEFP.Info
		(	aTickerId
		,	aTickType
		,	aBasisPoints
		,	aFormattedBasisPoints
		,	aImpliedFuture
		,	aHoldDays
		,	aFutureExpiry
		,	aDividendImpact
		,	aDividendsToExpiry
		)));
	}

	@Override
	public void tickSnapshotEnd(int aReqId) {
		mContext.onEventTask
		(	new OnTickSnapshotEnd(mContext
		,	new OnTickSnapshotEnd.Info
		(	aReqId
		)));
	}

	@Override
	public void marketDataType(int aReqId, int aMarketDataType) {
		mContext.onEventTask
		(	new OnMarketDataType(mContext
		,	new OnMarketDataType.Info
		(	aReqId
		,	aMarketDataType
		)));
	}
	
	//========================================================================
	// Orders
	//------------------------------------------------------------------------
	
	@Override
	public void orderStatus
	(	int aOrderId
	,	String aStatus
	,	int aFilled
	,	int aRemaining
	,	double aAvgFillPrice
	,	int aPermId
	,	int aParentId
	,	double aLastFillPrice
	,	int aClientId
	,	String aWhyHeld
	) {
		mContext.onEventTask
		(	new OnOrderStatus(mContext
		,	new OnOrderStatus.Info
		(	aOrderId
		,	aStatus
		,	aFilled
		,	aRemaining
		,	aAvgFillPrice
		,	aPermId
		,	aParentId
		,	aLastFillPrice
		,	aClientId
		,	aWhyHeld	
		)));
	}

	@Override
	public void openOrder
	(	int aOrderId
	,	Contract aContract
	,	Order aOrder
	,	OrderState aOrderState
	) {
		mContext.onEventTask
		(	new OnOpenOrder(mContext
		,	new OnOpenOrder.Info
		(	aOrderId
		,	aContract
		,	aOrder
		,	aOrderState
		)));
	}

	@Override
	public void openOrderEnd() {
		mContext.onEventTask
		(	new OnOpenOrderEnd(mContext
		,	new OnOpenOrderEnd.Info
		(
		)));
	}

	@Override
	public void nextValidId(int aOrderId) {
		mContext.onEventTask
		(	new OnNextValidId(mContext
		,	new OnNextValidId.Info
		(	aOrderId
		)));
	}

	@Override
	public void deltaNeutralValidation(int aReqId, UnderComp aUnderComp) {
		mContext.onEventTask
		(	new OnDeltaNeutralValidation(mContext
		,	new OnDeltaNeutralValidation.Info
		(	aReqId
		,	aUnderComp
		)));
	}

	//========================================================================
	// Account and Portfolio
	//------------------------------------------------------------------------
	
	@Override
	public void updateAccountValue
	(	String aKey
	,	String aValue
	,	String aCurrency
	,	String aAccountName
	) {
		mContext.onEventTask
		(	new OnUpdateAccountValue(mContext
		,	new OnUpdateAccountValue.Info
		(	aKey
		,	aValue
		,	aCurrency
		,	aAccountName
		)));
	}

	@Override
	public void updatePortfolio
	(	Contract aContract
	,	int aPosition
	,	double aMarketPrice
	,	double aMarketValue
	,	double aAverageCost
	,	double aUnrealizedPNL
	,	double aRealizedPNL
	,	String aAccountName
	) {
		mContext.onEventTask
		(	new OnUpdatePortfolio(mContext
		,	new OnUpdatePortfolio.Info
		(	aContract
		,	aPosition
		,	aMarketPrice
		,	aMarketValue
		,	aAverageCost
		,	aUnrealizedPNL
		,	aRealizedPNL
		,	aAccountName
		)));
	}

	@Override
	public void updateAccountTime(String aTimeStamp) {
		mContext.onEventTask
		(	new OnUpdateAccountTime(mContext
		,	new OnUpdateAccountTime.Info
		(	aTimeStamp
		)));
	}

	@Override
	public void accountDownloadEnd(String aAccountName) {
		mContext.onEventTask
		(	new OnAccountDownloadEnd(mContext
		,	new OnAccountDownloadEnd.Info
		(	aAccountName
		)));
	}

	@Override
	public void accountSummary
	(	int aReqId
	,	String aAccount
	,	String aTag
	,	String aValue
	,	String aCurrency
	) {
		mContext.onEventTask
		(	new OnAccountSummary(mContext
		,	new OnAccountSummary.Info
		(	aReqId
		,	aAccount
		,	aTag
		,	aValue
		,	aCurrency
		)));
	}

	@Override
	public void accountSummaryEnd(int aReqId) {
		mContext.onEventTask
		(	new OnAccountSummaryEnd(mContext
		,	new OnAccountSummaryEnd.Info
		(	aReqId
		)));
	}

	@Override
	public void position
	(	String aAccount
	,	Contract aContract
	,	int aPos
	,	double aAvgCost
	) {
		mContext.onEventTask
		(	new OnPosition(mContext
		,	new OnPosition.Info
		(	aAccount
		,	aContract
		,	aPos
		,	aAvgCost
		)));
	}

	@Override
	public void positionEnd() {
		mContext.onEventTask
		(	new OnPositionEnd(mContext
		,	new OnPositionEnd.Info
		(
		)));
	}

	//========================================================================
	// Contract Details
	//------------------------------------------------------------------------
	
	@Override
	public void contractDetails
	(	int aReqId
	,	ContractDetails aContractDetails
	) {
		mContext.onEventTask
		(	new OnContractDetails(mContext
		,	new OnContractDetails.Info
		(	aReqId
		,	aContractDetails
		)));
	}

	@Override
	public void contractDetailsEnd(int aReqId) {
		mContext.onEventTask
		(	new OnContractDetailsEnd(mContext
		,	new OnContractDetailsEnd.Info
		(	aReqId
		)));
	}

	@Override
	public void bondContractDetails
	(	int aReqId
	,	ContractDetails aContractDetails
	) {
		mContext.onEventTask
		(	new OnBondContractDetails(mContext
		,	new OnBondContractDetails.Info
		(	aReqId
		,	aContractDetails
		)));
	}

	//========================================================================
	// Executions
	//------------------------------------------------------------------------

	@Override
	public void execDetails
	(	int aReqId
	,	Contract aContract
	,	Execution aExecution
	) {
		mContext.onEventTask
		(	new OnExecDetails(mContext
		,	new OnExecDetails.Info
		(	aReqId
		,	aContract
		,	aExecution
		)));
	}

	@Override
	public void execDetailsEnd(int aReqId) {
		mContext.onEventTask
		(	new OnExecDetailsEnd(mContext
		,	new OnExecDetailsEnd.Info
		(	aReqId
		)));
	}

	@Override
	public void commissionReport(CommissionReport aCommissionReport) {
		mContext.onEventTask
		(	new OnCommissionReport(mContext
		,	new OnCommissionReport.Info
		(	aCommissionReport
		)));
	}

	//========================================================================
	// Market Depth
	//------------------------------------------------------------------------	
	
	@Override
	public void updateMktDepth
	(	int aTickerId
	,	int aPosition
	,	int aOperation
	,	int aSide
	,	double aPrice
	,	int aSize
	) {
		mContext.onEventTask
		(	new OnUpdateMktDepth(mContext
		,	new OnUpdateMktDepth.Info
		(	aTickerId
		,	aPosition
		,	aOperation
		,	aSide
		,	aPrice
		,	aSize
		)));
	}

	@Override
	public void updateMktDepthL2
	(	int aTickerId
	,	int aPosition
	,	String aMarketMaker
	,	int aOperation
	,	int aSide
	,	double aPrice
	,	int aSize
	) {
		mContext.onEventTask
		(	new OnUpdateMktDepthL2(mContext
		,	new OnUpdateMktDepthL2.Info
		(	aTickerId
		,	aPosition
		,	aMarketMaker
		,	aOperation
		,	aSide
		,	aPrice
		,	aSize
		)));
	}
	
	//========================================================================
	// News Bulletins
	//------------------------------------------------------------------------
	
	@Override
	public void updateNewsBulletin
	(	int aMsgId
	,	int aMsgType
	,	String aMessage
	,	String aOrigExchange
	) {
		mContext.onEventTask
		(	new OnUpdateNewsBulletin(mContext
		,	new OnUpdateNewsBulletin.Info
		(	aMsgId
		,	aMsgType
		,	aMessage
		,	aOrigExchange
		)));
	}

	//========================================================================
	// Financial Advisors
	//------------------------------------------------------------------------
	
	@Override
	public void managedAccounts(String aAccountsList) {
		mContext.onEventTask
		(	new OnManagedAccounts(mContext
		,	new OnManagedAccounts.Info
		(	aAccountsList
		)));
	}

	@Override
	public void receiveFA(int aFaDataType, String aXml) {
		mContext.onEventTask
		(	new OnReceiveFA(mContext
		,	new OnReceiveFA.Info
		(	aFaDataType
		,	aXml
		)));
	}

	//========================================================================
	// Historical Data
	//------------------------------------------------------------------------
	
	@Override
	public void historicalData
	(	int aReqId
	,	String aDate
	,	double aOpen
	,	double aHigh
	,	double aLow
	,	double aClose
	,	int aVolume
	,	int aCount
	,	double aWAP
	,	boolean aHasGaps
	) {
		String historicalDataEndPrefix = Config.getHistoricalDataEndPrefix();
		if (aDate.startsWith(historicalDataEndPrefix)) {
			mContext.onEventTask
			(	new OnHistoricalDataEnd(mContext
			,	new OnHistoricalDataEnd.Info
			(	aReqId
			)));
		}
		else {
			mContext.onEventTask
			(	new OnHistoricalData(mContext
			,	new OnHistoricalData.Info
			(	aReqId
			,	aDate
			,	aOpen
			,	aHigh
			,	aLow
			,	aClose
			,	aVolume
			,	aCount
			,	aWAP
			,	aHasGaps
			)));
		}
	}

	//========================================================================
	// Market Scanners
	//------------------------------------------------------------------------

	@Override
	public void scannerParameters(String aXml) {
		mContext.onEventTask
		(	new OnScannerParameters(mContext
		,	new OnScannerParameters.Info
		(	aXml
		)));
	}

	@Override
	public void scannerData
	(	int aReqId
	,	int aRank
	,	ContractDetails aContractDetails
	,	String aDistance
	,	String aBenchmark
	,	String aProjection
	,	String aLegsStr
	) {
		mContext.onEventTask
		(	new OnScannerData(mContext
		,	new OnScannerData.Info
		(	aReqId
		,	aRank
		,	aContractDetails
		,	aDistance
		,	aBenchmark
		,	aProjection
		,	aLegsStr
		)));
	}

	@Override
	public void scannerDataEnd(int aReqId) {
		mContext.onEventTask
		(	new OnScannerDataEnd(mContext
		,	new OnScannerDataEnd.Info
		(	aReqId
		)));
	}

	//========================================================================
	// Real Time Bars
	//------------------------------------------------------------------------

	@Override
	public void realtimeBar
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
		mContext.onEventTask
		(	new OnRealtimeBar(mContext
		,	new OnRealtimeBar.Info
		(	aReqId
		,	aTime
		,	aOpen
		,	aHigh
		,	aLow
		,	aClose
		,	aVolume
		,	aWAP
		,	aCount
		)));
	}

	//========================================================================
	// Fundamental Data
	//------------------------------------------------------------------------
	
	@Override
	public void fundamentalData(int aReqId, String aData) {
		mContext.onEventTask
		(	new OnFundamentalData(mContext
		,	new OnFundamentalData.Info
		(	aReqId
		,	aData
		)));
	}

	//========================================================================
	
}
