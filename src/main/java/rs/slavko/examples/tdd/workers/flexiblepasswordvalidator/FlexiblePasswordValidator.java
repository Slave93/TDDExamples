package rs.slavko.examples.tdd.workers.flexiblepasswordvalidator;

public class FlexiblePasswordValidator {

	public static final int DEFAULT_MIN_PASSWORD_LENGTH = 5;
	public static final boolean DEFAULT_MANDATORY_SPECIAL_SIGN = false;
	public static final boolean DEFAULT_MANDATORY_UPPERCASE = false;
	
	private int minPasswordLength;
	private boolean mandatorySpecialSign;
	private boolean mandatoryUppercase;
	
	public FlexiblePasswordValidator(){
		this.minPasswordLength = DEFAULT_MIN_PASSWORD_LENGTH;
		this.mandatorySpecialSign = DEFAULT_MANDATORY_SPECIAL_SIGN;
		this.mandatoryUppercase = DEFAULT_MANDATORY_UPPERCASE;
	}
	
	public void validate(String password) {
		checkNull(password);
		checkMinLength(password);
		validateLowercase(password);
		validateUppercase(password);
		checkSpecialSign(password);
	}

	private void checkNull(String password) {
		if(password==null || password.isEmpty())
			throw new IllegalArgumentException("Password cann't be null or empty");
	}
	
	private void checkMinLength(String password) {
		if(password.length()<minPasswordLength){
			throw new IllegalArgumentException("Password must be at least "+minPasswordLength+ " characters long");
		}
	}
	
	private void validateUppercase(String password) {
		if(mandatoryUppercase){
			if(!password.matches(".*[A-Z].*")){
				throw new IllegalArgumentException("Password must contains at least one UPPERCASE letter");
			}
		}
	}
	
	private void validateLowercase(String password) {
		if(!password.matches(".*[a-z].*")){
			throw new IllegalArgumentException("Password must contains at least one lowercase letter");
		}
	}
	
	private void checkSpecialSign(String password) {
		if(mandatorySpecialSign){
			if(!password.matches(".*[!@#$%^&\\*].*")){
				throw new IllegalArgumentException("Password must contain special character");
			}
		}
	}
	
	public int getMinPasswordLength() {
		return minPasswordLength;
	}
	public boolean getMandatorySpecialSign() {
		return mandatorySpecialSign;
	}
	public boolean getMandatoryUppercase() {
		// TODO Auto-generated method stub
		return mandatoryUppercase;
	}
	public void setMinPasswordLength(int minPasswordLength) {
		this.minPasswordLength = minPasswordLength;		
	}

	public void setMandatorySpecialSign(boolean mandatorySpecialSign) {
		this.mandatorySpecialSign = mandatorySpecialSign;
		
	}

	public void setMandatoryUppercase(boolean mandatoryUpperCase) {
		this.mandatoryUppercase = mandatoryUpperCase;
		
	}

}
