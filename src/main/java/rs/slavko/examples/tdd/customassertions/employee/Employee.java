package rs.slavko.examples.tdd.customassertions.employee;

import rs.slavko.examples.tdd.customassertions.employee.Project;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	public enum Position {
		JAVA_DEVELOPER, C_DEVELOPER, PHP_DEVELOPER, MANAGER
	}

	private Position position;
	private Project currentProject;
	private List<Project> previousProjects = new ArrayList<>();

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Project getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
	}

	public List<Project> getPreviousProjects() {
		return previousProjects;
	}

	public void setPreviousProjects(List<Project> previousProjects) {
		this.previousProjects = previousProjects;
	}
}
