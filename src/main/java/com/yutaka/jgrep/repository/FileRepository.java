package com.yutaka.jgrep.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.yutaka.jgrep.common.PropertyStore;
import com.yutaka.jgrep.common.Util;
import com.yutaka.jgrep.entity.ExtractLineParameter;
import com.yutaka.jgrep.entity.PathStore;
import com.yutaka.jgrep.entity.Result;
import com.yutaka.jgrep.option.ExtractOption;

/**
 * ファイルアクセスクラス
 *
 */
public class FileRepository {

	// grep対象となる拡張子Set ("txt", "csv"はデフォルト設定)
	private static Set<String> targetExtensions = new HashSet<>(Arrays.asList("txt", "csv"));

	static {
		String val = PropertyStore.getPropValue("extensions");
		if (val != null) {
			Collections.addAll(targetExtensions, val.split(","));
		}
	}

	/**
	 * 指定パス配下のファイルパスリストを取得する。
	 *
	 * @param dir
	 * @return
	 */
	public static List<Path> getPathList(final Path dir) {

		List<Path> paths = null;
		try (Stream<Path> pathStream = Files.list(dir)) {
			paths = pathStream.filter(p -> isFile(p) && isValidExtension(p)).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return paths;
	}

	/**
	 * 指定パス配下のファイルパスを取得する。
	 * ディレクトリはストアに格納する。
	 *
	 * @param dir
	 * @param pathStore
	 * @return
	 */
	public static List<Path> getPathListInclusiveDir(final Path dir, PathStore pathStore) {

		List<Path> paths = null;
		try (Stream<Path> pathStream = Files.list(dir)) {
			paths = pathStream.filter(p -> {
				if (isDirectory(p)) {
					return true;
				} else {
					return isFile(p) && isValidExtension(p);
				}
			}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (paths != null && paths.size() > 0) {
			pathStore.add(paths.stream().filter(FileRepository::isDirectory).collect(Collectors.toList()));
		}
		return paths;
	}

	/**
	 * 対象ファイルのキーワードあるいは正規表現にマッチする行を抽出する。
	 *
	 * @param path 対象ファイルパス
	 * @param keyword 任意の文字列
	 * @param fnMatch 抽出検証用の関数
	 * @return
	 */
	public static void extractLine(ExtractLineParameter param, Result result) {

		final Path path = param.getPath();
		final String keyword = param.getKeyword();
		List<BiPredicate<String, String>> fnTestList = param.getFnTestList();
		String line = null;
		List<String> lineList = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(path, Util.ENCODE)) {
			while ((line = reader.readLine()) != null) {
				lineList.add(line);
			}
		} catch (AccessDeniedException e) {
			// ファイル読み込み権限が無かった場合、通知メッセージのみ表示する。
			lineList.add(line + " " + "<<対象ファイルの読み取り権限がありません。>>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 出力形式を指定するオプションが指定されている場合、その様式で出力リストに格納する
		ExtractOption extractOption = param.getExtraOption();
		if (extractOption == null) {
			for (String aLine : lineList) {
				if (fnTestList.stream().allMatch(predicate -> predicate.test(aLine, keyword))) {
					result.addLine(path.toString());
				}
			}
		} else {
			extractOption.apply(path, keyword, lineList, fnTestList, result);
		}
	}

	/**
	 * 有効な拡張子であるかの検証を行う。
	 *
	 * @param path 対象パス
	 * @return
	 */
	private static boolean isValidExtension(Path path) {
		String[] splitted = path.toString().split("\\.");
		String extension = splitted[splitted.length - 1];
		return targetExtensions.stream().anyMatch(e -> e.equals(extension));
	}

	/**
	 * ファイル情報かどうかの検証を行う。
	 *
	 * @param path 対象パス
	 * @return
	 */
	private static boolean isFile(Path path) {
		return path.toFile().isFile();
	}

	private static boolean isDirectory(Path path) {
		return path.toFile().isDirectory();
	}

}
