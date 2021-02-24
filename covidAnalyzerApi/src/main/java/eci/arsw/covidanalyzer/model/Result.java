package eci.arsw.covidanalyzer.model;

import java.util.UUID;

public class Result {

	private UUID id;
	private String firstName;
	private ResultType type;
	private int TestAmount;

	public Result(String firstName, ResultType type) {
		id = UUID.randomUUID();
		this.firstName = firstName;
		this.type = type;
		TestAmount = 1;
	}

	// <editor-fold desc="getters">
	public UUID getId() {
		return id;
	}

	public ResultType getType() {
		return type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setType(ResultType result) {
		type = result;
	}
	
	public int getTestAmount() {
		return TestAmount;
	}

	public void setTestAmount(int testAmount) {
		TestAmount = testAmount;
	}

}
