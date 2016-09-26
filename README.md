# TDDExamples
This repository contains examples that i've made while reading books related to TDD approach, and other
Java testing topics. It contains examples of unit testing Worker and Manager classes, mocking,
writing custom Rules, writing parameterized tests, writing custom assertions using FEST...

# WORKERS:
Repository with examples made while practicing TDD(tests-first) approach.
Examples placed in rs.slavko.examples.tdd.menagers are examples of testing "manager" classes that
has no or little business logic, and mainly dispatche messages between other classes, that can be managers too
or workers.
## FlexiblePasswordValidatorTest
This class and its test class (FlexiblePasswordValidatorTest) represents a<br/>
good example of how important a <b>MINIMAL API</b> design is. Even after more<br/>
than 30 tests, more use cases remained untested:<br/>
  passwordValidatorMinLengthSpecialSign<br/>
  passwordValidatorMinLengthUppercase<br/>
  passwordValidatorSpecialSignUppsercase<br/>
  passwordValidatorMinLengthSpecialSignUppercase<br/>
This shows that writing test for mutable classes is much harder, because many cases needs to be covered.
This being sad, API should be designed carefully with testability in mind even if
it is easy to implement. Just like in the case of FlexiblePasswordValidator. 
In this case this much flexibility(configurable minLength, mandatoryUppercase, mandatoryLowerCase)
is probably an overhead. It would be better to make just a couple of configurations(ex. weak,medium,hard passwordWalidator)
via some factory method. It would be probably more then enough, and will be much easier to write and MAINTAIN tests.

# MANAGERS:
Worker classes hold business logic and do not use collaborators(do not have DOCs). In this classes
mocking is not necessary, and state testing can be used without behavior testing. But this classes as they hold
business logic and "do real work" usually have more use cases and more tests. Examples of testing worker classes are
placed in package rs.slavko.examples.tdd.workers.
## MessageRouterTest
Simple example of a class thet is pure MANAGER, and does not do "real work". It just takes message, and disptatch
message body to appropriate dispatcher based on message type. This is a good example of how test can be written for a 
manager class(MessageRouter) wihtout even implementing it's collaborators(DOCs) by coding to an interface.

# RULES:
## UnreliableApiTest
This is a example that uses custom rule: RetryRule, to apply retry logic to some of the tests.
If test is annotated with @Rule(custom annotation made for this purpose) it will be rerun(in case of failures)
n-times.

#CUSOTMASSERTIONS
##EmployeeTest
This test uses custom assertion written using FEST library: (...customassertions.employee.assertion.EmployeeAssert).
Custom assertions improve test readability, and can be used for building DSLs.

