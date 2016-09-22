package rs.slavko.examples.tdd.managers.messagerouter.model;

public interface Message {

	enum Type{
		SMS,EMAIl
	}

	long getAgeMill();

	Type getMessageType();

	String getBody();
}
