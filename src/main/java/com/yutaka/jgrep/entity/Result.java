package com.yutaka.jgrep.entity;

import java.util.ArrayList;
import java.util.List;

public class Result {

	private List<String> output = new ArrayList<>();

	public List<String> getOutput() {
		return output;
	}

	public void setOutput(List<String> output) {
		this.output = output;
	}

	public void addLine(String line) {
		output.add(line);
	}

}
