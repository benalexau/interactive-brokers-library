package com.blogspot.mikelaud.ibl.connection;

import com.ib.client.EClientSocket;
import com.ib.client.EJavaSignal;

public class Connection {
	
	private ConnectionContext mConnectionContext;
	private ConnectionEventsHandler mEventsHandler;
	private EClientSocket mClientSocket;

	public EClientSocket getClientSocket() {
		return mClientSocket;
	}
	
	public Connection(ConnectionContext aConnectionContext) {
		mConnectionContext = aConnectionContext;
		mEventsHandler = new ConnectionEventsHandler(mConnectionContext);
		mClientSocket = new EClientSocket(mEventsHandler, new EJavaSignal());
	}
	
}
