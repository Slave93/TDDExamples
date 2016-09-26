package rs.slavko.examples.tdd.rules.unreliableapi;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import rs.slavko.examples.tdd.rules.unreliableapi.annotations.Retry;
import rs.slavko.examples.tdd.rules.unreliableapi.rest.model.util.RestUtil;
import rs.slavko.examples.tdd.rules.unreliableapi.rest.UnreliableApi;
import rs.slavko.examples.tdd.rules.unreliableapi.rest.model.UnreliableApiResponse;
import rs.slavko.examples.tdd.rules.unreliableapi.rules.RetryRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnreliableApiTest {

	private static final String RELIABLE_API_PATH = "/reliableApi";
	private static final String UNRELIABLE_API_PATH = "/unreliableApi";

	@InjectMocks
	private UnreliableApi unreliableApi = new UnreliableApi();
	@Mock
	private RestUtil restUtil;
	@Rule
	public RetryRule retryRule = new RetryRule();

	@Test
	public void testCall(){
		UnreliableApiResponse response = Mockito.mock(UnreliableApiResponse.class);
		when(restUtil.get(RELIABLE_API_PATH,UnreliableApiResponse.class)).thenReturn(response);

		UnreliableApiResponse result = unreliableApi.call();

		assertEquals(response,result);
	}

	@Test
	@Retry(times=4)
	public void testRiskyCall(){
		UnreliableApiResponse response = Mockito.mock(UnreliableApiResponse.class);
		when(restUtil.get(UNRELIABLE_API_PATH,UnreliableApiResponse.class)).thenReturn(response);

		UnreliableApiResponse result = unreliableApi.riskyCall();

		assertEquals(response,result);
	}
}
