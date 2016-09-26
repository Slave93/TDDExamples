package rs.slavko.examples.tdd.customassertions.employee.assertion;


import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;
import rs.slavko.examples.tdd.customassertions.employee.Employee;
import rs.slavko.examples.tdd.customassertions.employee.Project;

public class EmployeeAssert extends GenericAssert<EmployeeAssert, Employee> {
	public EmployeeAssert(Employee actual) {
		super(EmployeeAssert.class, actual);
	}
	public static EmployeeAssert assertThat(Employee actual) {
		return new EmployeeAssert(actual);
	}

	public EmployeeAssert isJavaDeveloper() {
		isNotNull();
		String errorMessage = String.format(
						"Expected employee position to be <%s> but was <%s>",
						Employee.Position.JAVA_DEVELOPER, actual.getPosition());
		Assertions.assertThat(actual.getPosition())
						.overridingErrorMessage(errorMessage)
						.isEqualTo(Employee.Position.JAVA_DEVELOPER);
		return this;
	}

	public EmployeeAssert worksOn(Project project) {
		isNotNull();
		String errorMessage = String.format(
						"Expected employee currentProject to be <%s> but was <%s>",
						project, actual.getCurrentProject());
		Assertions.assertThat(actual.getCurrentProject())
						.overridingErrorMessage(errorMessage)
						.isEqualTo(project);
		return this;
	}

	public EmployeeAssert workedOn(Project... projects) {
		isNotNull();
		String errorMessage = String.format(
						"Expected that employee previousProjects contains <%s>, but it doesn't",
						projects);
		Assertions.assertThat(actual.getPreviousProjects())
						.overridingErrorMessage(errorMessage)
						.contains(projects);
		return this;
	}
}
