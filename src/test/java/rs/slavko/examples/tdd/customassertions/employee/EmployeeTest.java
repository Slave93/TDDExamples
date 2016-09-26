package rs.slavko.examples.tdd.customassertions.employee;

import org.junit.Test;
import org.mockito.Mockito;

import static rs.slavko.examples.tdd.customassertions.employee.assertion.EmployeeAssert.assertThat;

public class EmployeeTest {

	@Test
	public void employeeTest(){
		Employee employee = new Employee();
		Project currentProject = Mockito.mock(Project.class,"Current project");
		Project oldProject1 = Mockito.mock(Project.class,"Old project1");
		Project oldProject2 = Mockito.mock(Project.class,"Old project2");

		employee.setPosition(Employee.Position.JAVA_DEVELOPER);
		employee.setCurrentProject(currentProject);
		employee.getPreviousProjects().add(oldProject1);
		employee.getPreviousProjects().add(oldProject2);

		assertThat(employee).isJavaDeveloper().worksOn(currentProject).workedOn(oldProject1,oldProject2);
	}

}
