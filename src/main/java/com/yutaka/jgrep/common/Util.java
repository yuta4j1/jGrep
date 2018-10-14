package com.yutaka.jgrep.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * ユーティルクラス
 *
 */
public class Util {

	public static final Charset ENCODE = isLinux() ? StandardCharsets.UTF_8 : Charset.forName("SJIS");

	public static final String SEPARATOR = System.getProperty("file.separator");

	public static String getFileName(Path path) {
		return path.getFileName().toString();
	}

	private static boolean isLinux() {
		return "Linux".contains(System.getProperty("os.name"));
	}

}
