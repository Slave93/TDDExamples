package rs.slavko.examples.tdd.rules.unreliableapi.rest;

import rs.slavko.examples.tdd.rules.unreliableapi.rest.model.UnreliableApiResponse;
import rs.slavko.examples.tdd.rules.unreliableapi.rest.model.util.RestUtil;

public class UnreliableApi {
	private static final String RELIABLE_API_PATH = "/reliableApi";
	private static final String UNRELIABLE_API_PATH = "/unreliableApi";

	private RestUtil restUtil;

	public static int shouldFailCount = 1;

	public UnreliableApiResponse call() {
		return restUtil.get(RELIABLE_API_PATH,UnreliableApiResponse.class);
	}

	public UnreliableApiResponse riskyCall() {
		if(!shouldSucceed()){
			return null;
		}
		return restUtil.get(UNRELIABLE_API_PATH,UnreliableApiResponse.class);
	}

	private static boolean shouldSucceed(){
		resetShouldFailCount();
		return ++shouldFailCount %5==0;
	}

	private static void resetShouldFailCount() {
		shouldFailCount = shouldFailCount == 1000 ? 1 : shouldFailCount++;
	}
}
