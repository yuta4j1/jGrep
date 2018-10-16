package com.yutaka.jgrep.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * ユーティルクラス
 *
 */
public class Util {
	// 文字コード
	// Linuxの場合UTF-8、それ以外（Windowsを想定）はSJISとする。
	public static final Charset ENCODE = isLinux() ? StandardCharsets.UTF_8 : Charset.forName("SJIS");

	public static final String SEPARATOR = System.getProperty("file.separator");

	public static String getFileName(Path path) {
		return path.getFileName().toString();
	}

	public static String joinPath(String... dir) {
		return String.join(SEPARATOR, dir);
	}

	public static String correctSeparator(String path) {
		final String YEN = "\\";
		final String SLA = "/";
		if (path.contains(YEN)) {
			String[] splitted = path.split(YEN);
			return joinPath(splitted);
		} else if (path.contains(SLA)) {
			String[] splitted = path.split(SLA);
			return joinPath(splitted);
		}
		return path;
	}

	private static boolean isLinux() {
		return "Linux".contains(System.getProperty("os.name"));
	}

}
