package com.yutaka.jgrep.grep;

import com.yutaka.jgrep.CommandLineArgs;
import com.yutaka.jgrep.ConsoleWriter;
import com.yutaka.jgrep.LogWriter;
import com.yutaka.jgrep.entity.Result;

public class GrepWrapper {

	private GrepExecutor executor;

	public GrepWrapper(GrepExecutor executor) {
		this.executor = executor;
	}

	public void grep(CommandLineArgs cmd) {
		Result result = executor.execute(cmd.getTargetPath(), cmd.getKeyword());
		ConsoleWriter console = new ConsoleWriter();
		console.output(result);
		LogWriter log = new LogWriter();
		log.output(result);
	}

}
