package rs.slavko.examples.tdd.managers.messagerouter;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import rs.slavko.examples.tdd.managers.messagerouter.configuration.ConfigurationHolder;
import rs.slavko.examples.tdd.managers.messagerouter.exception.UnsupportedMessageTypeException;
import rs.slavko.examples.tdd.managers.messagerouter.model.Message;

@RunWith(JUnitParamsRunner.class)
public class MessageRouterTest {

	private static final long MESSAGE_EXPIRATION_INTERVAL = 1000L;
	private static final long MESSAGE_AGE_EXPIRED_1 = MESSAGE_EXPIRATION_INTERVAL;
	private static final long MESSAGE_AGE_EXPIRED_2 = MESSAGE_EXPIRATION_INTERVAL + 10;
	private static final long MESSAGE_AGE_NOT_EXPIRED_1 = MESSAGE_EXPIRATION_INTERVAL - 1;
	private static final long MESSAGE_AGE_NOT_EXPIRED_2 = MESSAGE_EXPIRATION_INTERVAL - 10;

	private static final Message.Type MESSAGE_TYPE_UNKNOWN = null;

	//SUT
	MessageRouter messageRouter = new MessageRouter();

	//DOCs
	ConfigurationHolder configurationHolder = mock(ConfigurationHolder.class);
	Dispatcher smsDispatcher = mock(Dispatcher.class, "smsDispatcher");
	Dispatcher emailDispatcher = mock(Dispatcher.class, "emailDispatcher");
	Message message = mock(Message.class);

	@Before
	public void setUp() {
		messageRouter.setConfigurationHolder(configurationHolder);
		messageRouter.setSmsDispatcher(smsDispatcher);
		messageRouter.setEmailDispatcher(emailDispatcher);
		when(configurationHolder.getMessageExpirationIntervalMill()).thenReturn(MESSAGE_EXPIRATION_INTERVAL);
		when(message.getBody()).thenReturn("Lorem Ipsum...");
	}

	@Test(expected = UnsupportedMessageTypeException.class)
	public void hadndleMessage_messageNotExpiredUnknownMessageType_shouldThrowExceptionShouldNotDispatch() {
		//ARRANGE:
		when(message.getAgeMill()).thenReturn(MESSAGE_AGE_NOT_EXPIRED_1);
		when(message.getMessageType()).thenReturn(MESSAGE_TYPE_UNKNOWN);

		try {
			//ACT:
			messageRouter.handleMessage(message);
		} catch (UnsupportedMessageTypeException umte) {
			//VERIFY:
			verify(smsDispatcher, never()).dispatch(message.getBody());
			verify(emailDispatcher, never()).dispatch(message.getBody());
			throw umte;
		}
	}

	@Test
	public void hadndleMessage_messageExpiredUnknownMessageType_shouldNotTrhowExeptionShouldNotDispatch() {
		//ARRANGE:
		when(message.getAgeMill()).thenReturn(MESSAGE_AGE_EXPIRED_1);
		when(message.getMessageType()).thenReturn(null);

		//ACT:
		messageRouter.handleMessage(message);

		//VERIFY:
		verify(smsDispatcher, never()).dispatch(message.getBody());
		verify(emailDispatcher, never()).dispatch(message.getBody());
	}

	@Test
	@Parameters(method = "getMessageTypes")
	public void handleMessage_messageExpired_shouldNotDispatch(Message.Type messageType) {
		//ARRANGE:
		when(message.getAgeMill()).thenReturn(MESSAGE_AGE_EXPIRED_2);
		when(message.getMessageType()).thenReturn(messageType);

		//ACT:
		messageRouter.handleMessage(message);

		//VERIFY:
		verify(smsDispatcher, never()).dispatch(message.getBody());
		verify(emailDispatcher, never()).dispatch(message.getBody());
	}

	@Test
	public void handleSmsMessage_messageNotExpired_shouldDispatchToSmsDispatcher() {
		//ARRANGE:
		when(message.getAgeMill()).thenReturn(MESSAGE_AGE_NOT_EXPIRED_2);
		when(message.getMessageType()).thenReturn(Message.Type.SMS);

		//ACT:
		messageRouter.handleMessage(message);

		//VERIFY:
		verify(smsDispatcher).dispatch(message.getBody());
		verify(emailDispatcher, never()).dispatch(message.getBody());
	}

	@Test
	public void handleEmailMessage_messageNotExpired_shouldDispatchToEmailDispatcher() {
		//ARRANGE:
		when(message.getAgeMill()).thenReturn(MESSAGE_AGE_NOT_EXPIRED_1);
		when(message.getMessageType()).thenReturn(Message.Type.EMAIl);

		//ACT:
		messageRouter.handleMessage(message);

		//VERIFY:
		verify(smsDispatcher, never()).dispatch(message.getBody());
		verify(emailDispatcher).dispatch(message.getBody());
	}

	private static final Object[][] getMessageTypes() {
		return new Message.Type[][]{{Message.Type.SMS}, {Message.Type.EMAIl}};
	}

}
