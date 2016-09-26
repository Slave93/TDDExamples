package rs.slavko.examples.tdd.rules.unreliableapi.rest.model.util;

public interface RestUtil {
	<T> T get(String path, Class<T> responseType);
}
