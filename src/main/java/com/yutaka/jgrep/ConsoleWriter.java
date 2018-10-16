package com.yutaka.jgrep;

import java.util.List;

import com.yutaka.jgrep.entity.Result;

public class ConsoleWriter implements ResultOutput {

	public void output(Result result) {

		List<String> outputLine = result.getOutput();
		outputLine.forEach(System.out::println);

	}

}
