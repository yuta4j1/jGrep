package com.yutaka.jgrep.grep;

import com.yutaka.jgrep.CommandLineArgs;

public class GrepWrapper {

	private GrepExecutor executor;

	public GrepWrapper(GrepExecutor executor) {
		this.executor = executor;
	}

	public void grep(CommandLineArgs cmd) {
		// TODO executor実行、結果出力、ログ出力
	}

}
