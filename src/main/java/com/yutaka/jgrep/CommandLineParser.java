
package com.yutaka.jgrep;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.yutaka.jgrep.option.OptionManager;

import lombok.Getter;
import lombok.Setter;

/**
 * コマンドライン引数のDTOクラス
 *
 */
public class CommandLineParser {

	private static final String HYPHEN = "-";

	/** 対象ディレクトリパス */
	@Getter
	private Path targetPath;

	/** 検索対象キーワード */
	@Getter
	@Setter
	private String keyword;

	/** オプション引数リスト */
	@Getter
	private List<String> options = new ArrayList<>();

	/**
	 * コンストラクタ。
	 *
	 * @param args コマンドライン引数
	 */
	public CommandLineParser(String[] args) {

		try {
			if (args.length <= 1) {
				throw new IllegalArgumentException("パスを指定してください。");

			} else if (args.length >= 3) {
				// パスを格納
				setTargetPath(args[0]);
				// キーワードを格納
				setKeyword(args[1]);
				setOptions(args[2]);

			} else {
				// パスを格納
				this.setTargetPath(args[0]);
				// キーワードを格納
				this.setKeyword(args[1]);
			}
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	/**
	 * 対象ディレクトリパスのセッター。
	 * 絶対パス/相対パスの検証を行う。
	 *
	 * @param strPath
	 */
	private void setTargetPath(final String strPath) {
		this.targetPath = Paths.get(strPath);
		if (!this.isValidPath(this.targetPath)) {
			throw new IllegalArgumentException("パラメータが不正です : 絶対パスを指定してください。");
		}
	}

	private void setOptions(String option) {
		this.options.add(option);
	}

//	private void strOption2List(String options) {
//		String[] optionArray = options.split("");
//		if (!OPTION_PREFIX.equals(optionArray[0])) {
//			throw new IllegalArgumentException("オプションの指定形式が不正です。");
//		}
//		this.options.addAll(Arrays.asList(optionArray));
//	}

	private boolean isValidPath(Path path) {
		// TODO 相対パスと絶対パスの判別。
		return true;
	}

	private String[] makeParseTarget() {
		List<String> args = this.options;
		List<String> target = new ArrayList<>();
		for (String arg : args) {
			if (HYPHEN.equals(String.valueOf(arg.charAt(0)))) {
				target.addAll(Arrays.asList(arg.split("")));
			} else {
				target.add(arg);
			}
		}
		return target.toArray(new String[target.size()]);
	}

	public OptionManager createOptionManager() {
		OptionManager optionManager = new OptionManager();
		JCommander.newBuilder().addObject(optionManager).build().parse(makeParseTarget());
		System.out.println(optionManager.toString());
		return optionManager;
	}

}
