package ar.com.tandilweb.exchange.clientOperations;

import ar.com.tandilweb.exchange.ClientOperationsSchema;

public class UserBanned extends ClientOperationsSchema {
	
	public long userID;
	
	public UserBanned() {
		super("userBanned");
	}

}
