package com.yutaka.jgrep.option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * grep検証条件用関数オブジェクトのファクトリクラス
 */
public class TestFactory {

	private static Map<String, BiPredicate<String, String>> testsMap = new HashMap<>();

	static {
		testsMap.put("e", (target, keyword) -> target.matches(keyword));
	}

	public static List<BiPredicate<String, String>> createTests(List<String> optionKeys) {
		List<BiPredicate<String, String>> testList = new ArrayList<>();
		for (String optionKey : optionKeys) {
			if (testsMap.containsKey(optionKey)) {
				testList.add(testsMap.get(optionKey));
			}
		}
		if (testList.size() == 0) {
			testList.add((target, keyword) -> target.contains(keyword));
		}
		return testList;
	}

}
