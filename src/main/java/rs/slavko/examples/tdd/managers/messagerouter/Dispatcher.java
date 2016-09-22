package rs.slavko.examples.tdd.managers.messagerouter;

import rs.slavko.examples.tdd.managers.messagerouter.model.Message;

public interface Dispatcher {
	void dispatch(String message);
}
