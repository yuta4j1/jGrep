package com.yutaka.jgrep;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.yutaka.jgrep.option.OptionManager;

/**
 * コマンドライン引数のDTOクラス
 *
 */
public class CommandLineParser {

	private static final String OPTION_PREFIX = "-";

	/** 対象ディレクトリパス */
	private Path targetPath;

	/** 検索対象キーワード */
	private String keyword;

	/** オプション文字列リスト */
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
				// 第三引数のオプションをリストとして格納
				strOption2List(args[2]);
				OptionManager optionManager = new OptionManager();
				JCommander.newBuilder().addObject(optionManager).build().parse(strOption2Array(args[2]));
				System.out.println(optionManager.toString());

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

	private void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	private void strOption2List(String options) {
		String[] optionArray = options.split("");
		if (!OPTION_PREFIX.equals(optionArray[0])) {
			throw new IllegalArgumentException("オプションの指定形式が不正です。");
		}
		this.options.addAll(Arrays.asList(optionArray));
	}

	private String[] strOption2Array(String options) {
		return options.split("");
	}

	public Path getTargetPath() {
		return targetPath;
	}

	public String getKeyword() {
		return keyword;
	}

	public List<String> getOptions() {
		return this.options;
	}

	private boolean isValidPath(Path path) {
		// TODO 相対パスと絶対パスの判別。
		return true;
	}

}
