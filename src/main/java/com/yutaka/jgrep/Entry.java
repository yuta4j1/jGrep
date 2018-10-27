package com.yutaka.jgrep;

import com.yutaka.jgrep.grep.ExecutorBuilder;
import com.yutaka.jgrep.grep.GrepWrapper;

/**
 * jGrepエントリークラス
 *
 */
public class Entry {

	/**
	 * エントリーポイント
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		CommandLineParser cmd = new CommandLineParser(args);
		GrepWrapper wrapper = new GrepWrapper(new ExecutorBuilder(cmd.createOptionManager()).createExecutor());
		wrapper.grep(cmd);

	}

}
