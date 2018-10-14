package com.yutaka.jgrep;

import com.yutaka.jgrep.grep.GrepWrapper;
import com.yutaka.jgrep.option.OptionManager;

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

		CommandLineArgs cmd = new CommandLineArgs(args);
		GrepWrapper wrapper = new GrepWrapper(new OptionManager(cmd.getOptions()).createExecutor());
		wrapper.grep(cmd);

	}

}
