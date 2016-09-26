package rs.slavko.examples.tdd.rules.unreliableapi.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.slavko.examples.tdd.rules.unreliableapi.annotations.Retry;

import java.lang.annotation.Annotation;

public class RetryRule implements TestRule {

	private static final Logger logger = LoggerFactory.getLogger(RetryRule.class);

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				int retryCount = getRetryCount(description);
				if(!performRetryCalls(base,retryCount)){
					performFinalCall(base);
				}
				return;
			}
		};
	}

	private int getRetryCount(Description testMethodDescription) {
		for (Annotation annotation : testMethodDescription.getAnnotations()) {
			if (annotation.annotationType().equals(Retry.class)) {
				Retry retryAnnotation = (Retry)annotation;
				return retryAnnotation.times();
			}
		}
		return 0;
	}

	private boolean performRetryCalls(Statement base, int retryCount) throws Throwable {
		while (retryCount != 0) {
			try {
				base.evaluate();
				return true;
			} catch (AssertionError e) {
				logger.info("Unsuccessful retry test execution. Going to try "+retryCount+" more times",e);
				retryCount--;
			}
		}
		return false;
	}

	private void performFinalCall(Statement base) throws Throwable {
		base.evaluate();
	}
}
