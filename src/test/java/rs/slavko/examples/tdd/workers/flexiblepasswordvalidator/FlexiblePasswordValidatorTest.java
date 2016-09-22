package rs.slavko.examples.tdd.workers.flexiblepasswordvalidator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/*
 * This class and its SUT is made using TDD(test-first) approach.
 * It is a good example of how important good an MINIMAL API design is.  
 * Should write tests for all other use cases:
 * passwordValidatorMinLengthSpecialSign;
 * passwordValidatorMinLengthUppercase;
 * passwordValidatorSpecialSignUppsercase;	
 * passwordValidatorMinLengthSpecialSignUppercase;	 
 * This shows that writing test for mutable classes is much harder, because many cases needs to be covered.
 * This being sad, API should be designed carefully with testability in mind even if it is easy to implement like in this case.
 * In this case this much flexibility(configurable minLength, mandatoryUppercase, mandatoryLowerCase) is probably overhead.
 * It would be better to make just a couple of configurations(ex. weak,medium,hard passwordWalidator) via some factory method.
 * It would be probably more then enough, and will be much easier to write and MAINTAIN tests.
 * */
@RunWith(JUnitParamsRunner.class)
@SuppressWarnings("unused")
public class FlexiblePasswordValidatorTest {
	
	private static final String PASSWORD_NULL = null;
	private static final int MIN_PASSWORD_LENGTH = 10;
	private static final int NO_MIN_PASSWORD_LENGTH = 0;
	
	String mock = "aaa";
	
	FlexiblePasswordValidator passwordValidatorDefault;
	FlexiblePasswordValidator passwordValidatorNoConstraints;	
	FlexiblePasswordValidator passwordValidatorMinLength;
	FlexiblePasswordValidator passwordValidatorSpecialSign;
	FlexiblePasswordValidator passwordValidatorUppercase;	
	FlexiblePasswordValidator passwordValidatorMinLengthSpecialSign;
	FlexiblePasswordValidator passwordValidatorMinLengthUppercase;
	FlexiblePasswordValidator passwordValidatorSpecialSignUppsercase;	
	FlexiblePasswordValidator passwordValidatorMinLengthSpecialSignUppercase;
	
	@Before
	public void setUp(){		
		passwordValidatorDefault = new FlexiblePasswordValidator();
		passwordValidatorNoConstraints = createPasswordValidator(NO_MIN_PASSWORD_LENGTH, false, false);
		passwordValidatorMinLength = createPasswordValidator(MIN_PASSWORD_LENGTH, false, false);
		passwordValidatorSpecialSign= createPasswordValidator(NO_MIN_PASSWORD_LENGTH, true, false);
		passwordValidatorUppercase = createPasswordValidator(NO_MIN_PASSWORD_LENGTH, false, true);
		passwordValidatorMinLengthSpecialSign = createPasswordValidator(MIN_PASSWORD_LENGTH, true, false);
		passwordValidatorMinLengthUppercase = createPasswordValidator(MIN_PASSWORD_LENGTH, false, true);
		passwordValidatorSpecialSignUppsercase = createPasswordValidator(NO_MIN_PASSWORD_LENGTH, true, true);
		passwordValidatorMinLengthSpecialSignUppercase = createPasswordValidator(MIN_PASSWORD_LENGTH, true, true);
	}
	
	
	@Test
	public void constructorTest(){		
		assertEquals(FlexiblePasswordValidator.DEFAULT_MIN_PASSWORD_LENGTH,passwordValidatorDefault.getMinPasswordLength());
		assertEquals(FlexiblePasswordValidator.DEFAULT_MANDATORY_SPECIAL_SIGN, passwordValidatorDefault.getMandatorySpecialSign());
		assertEquals(FlexiblePasswordValidator.DEFAULT_MANDATORY_UPPERCASE,passwordValidatorDefault.getMandatoryUppercase());
	}
	
	@Test
	public void whenTest(){
		FlexiblePasswordValidator flexiblePasswordValidator = Mockito.mock(FlexiblePasswordValidator.class);
		flexiblePasswordValidator.getMinPasswordLength();
		when(13131313).thenReturn(10);
		assertEquals(10, flexiblePasswordValidator.getMinPasswordLength());
		
	}
	
	@Test
	@Parameters(method="getMinPasswordLengths")
	public void setMinPasswordLengthTest(int passwordLength){
		
		passwordValidatorDefault.setMinPasswordLength(passwordLength);
		assertEquals(passwordLength, passwordValidatorDefault.getMinPasswordLength());
	}
	
	@Test
	public void setMandatorySpecialSignTrueTest(){
		passwordValidatorDefault.setMandatorySpecialSign(true);
		assertTrue(passwordValidatorDefault.getMandatorySpecialSign());
	}
	
	@Test
	public void setMandatorySpecialSignFalseTest(){
		passwordValidatorDefault.setMandatorySpecialSign(false);
		assertFalse(passwordValidatorDefault.getMandatorySpecialSign());
	}
	
	@Test
	public void setMandatoryUppercaseTrueTest(){
		passwordValidatorDefault.setMandatoryUppercase(true);
		assertTrue(passwordValidatorDefault.getMandatoryUppercase());
	}
	
	@Test
	public void setMandatoryUppercaseFalseTest(){
		passwordValidatorDefault.setMandatoryUppercase(false);
		assertFalse(passwordValidatorDefault.getMandatoryUppercase());
	}
		
	@Test(expected=IllegalArgumentException.class)	
	public void validateNullTest(){
		passwordValidatorNoConstraints.validate(PASSWORD_NULL);
	}
	
	@Test(expected=IllegalArgumentException.class)	
	public void validateEmptyTest(){
		passwordValidatorNoConstraints.validate("");
	}
	
	public void validateNoConstraintTest_oneLowercaseLetter_shouldNotThrowException(){
		passwordValidatorNoConstraints.validate("a");
	}
	
	@Test(expected=IllegalArgumentException.class)	
	public void validateNoConstraintTest_noLowercaseoneUpperCaseLetter_shouldThrowException(){
		passwordValidatorNoConstraints.validate("A");
	}
	
	@Test(expected=IllegalArgumentException.class)	
	public void validateNoConstraintTest_noLowercaseOneSpecialSignLetter_shouldThrowException(){
		passwordValidatorNoConstraints.validate("$");
	}
	
	@Test
	public void validateMinLength_validLength_shouldNotThrowException(){
		String password = "abcabcabca";
		passwordValidatorMinLength.validate(password);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateMinLength_validLengthButNoLowerCase_shouldThrowException(){
		String password = "ABCABCABCA";
		passwordValidatorMinLength.validate(password);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateMinLength_invalidLength_shouldThrowException(){
		String password = "abcabcabc";
		passwordValidatorMinLength.validate(password);
	}
	
	@Test
	public void validateUppercase_hasUppercase_shouldNotThrowException(){
		String password = "Aa";
		passwordValidatorUppercase.validate(password);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateUppercase_hasUppercaseButNoLowerCase_shouldThrowException(){
		String password = "A";
		passwordValidatorUppercase.validate(password);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateUppercase_noUppercase_shouldThrowException(){
		String password = "a";
		passwordValidatorUppercase.validate(password);		
	}
	
	@Test
	@Parameters(method="getSpecialSigns")
	public void validateSpecialSign_hasSpecialSign_shouldNotThrowException(Character specialSign){
		String password = specialSign+"a";
		passwordValidatorSpecialSign.validate(password);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	@Parameters(method="getSpecialSigns")
	public void validateUppercase_hasSpecialSignButNoLowerCase_shouldThrowException(Character specialSign){
		String password = specialSign+"";
		passwordValidatorSpecialSign.validate(password);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateUppercase_noSpecialSign_shouldThrowException(){
		String password = "a";
		passwordValidatorSpecialSign.validate(password);		
	}	
		
	private static final Object[][] getMinPasswordLengths(){
		return new Integer[][]{{10},{17},{33}};
	}
	
	private static final Object[][] getSpecialSigns(){
		return new Character[][]{{'!'},{'@'},{'#'},{'$'},{'%'},{'^'},{'&'},{'*'}};
	}
	
	private FlexiblePasswordValidator createPasswordValidator(int minPasswordLength, boolean mandatorySpecialSign, boolean mandatoryUppercase){
		FlexiblePasswordValidator passwordValidator = new FlexiblePasswordValidator();
		passwordValidator.setMandatorySpecialSign(mandatorySpecialSign);
		passwordValidator.setMandatoryUppercase(mandatoryUppercase);
		passwordValidator.setMinPasswordLength(minPasswordLength);
		return passwordValidator;
	}
	
}
