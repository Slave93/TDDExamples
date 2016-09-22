package rs.slavko.examples.tdd.managers.messagerouter;

import rs.slavko.examples.tdd.managers.messagerouter.configuration.ConfigurationHolder;
import rs.slavko.examples.tdd.managers.messagerouter.exception.UnsupportedMessageTypeException;
import rs.slavko.examples.tdd.managers.messagerouter.model.Message;

public class MessageRouter{

	private ConfigurationHolder configurationHolder;
	private Dispatcher smsDispatcher;
	private Dispatcher emailDispatcher;

	public void handleMessage(Message message) {
		if (!isExpired(message)){
			dispatch(message);
		}
	}

	private boolean isExpired(Message message) {
		return message.getAgeMill()>=configurationHolder.getMessageExpirationIntervalMill();
	}

	private void dispatch(Message message) {
		if(message.getMessageType() == Message.Type.EMAIl){
			emailDispatcher.dispatch(message.getBody());
		}else if(message.getMessageType() == Message.Type.SMS){
			smsDispatcher.dispatch(message.getBody());
		}else{
			throw new UnsupportedMessageTypeException("Message type "+message.getMessageType()+" is not supported");
		}
	}

	public void setConfigurationHolder(ConfigurationHolder configurationHolder) {
		this.configurationHolder = configurationHolder;
	}

	public void setSmsDispatcher(Dispatcher smsDispatcher) {
		this.smsDispatcher = smsDispatcher;
	}

	public void setEmailDispatcher(Dispatcher emailDispatcher) {
		this.emailDispatcher = emailDispatcher;
	}


}
