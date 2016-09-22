# TDExamples
Repository with examples made while practicing TDD(tests-first) approach.

## FlexiblePasswordValidator
This class and its test class (FlexiblePasswordValidatorTest) represents a<br/>
good example of how important a <b>MINIMAL API</b> design is. Even after more<br/>
than 30 tests, more use cases remained untested:<br/>
  passwordValidatorMinLengthSpecialSign<br/>
  passwordValidatorMinLengthUppercase<br/>
  passwordValidatorSpecialSignUppsercase<br/>	
  passwordValidatorMinLengthSpecialSignUppercase<br/>	 
This shows that writing test for mutable classes is much harder, because many cases needs to<br/>
be covered. This being sad, API should be designed carefully with testability in mind even if<br/>
it is easy to implement. Just like in the case of FlexiblePasswordValidator. In this case this much <br/>
flexibility(configurable minLength, mandatoryUppercase, mandatoryLowerCase) is probably an overhead.<br/>
It would be better to make just a couple of configurations(ex. weak,medium,hard passwordWalidator)<br/>
via some factory method. It would be probably more then enough, and will be much easier to write<br/>
and MAINTAIN tests.

