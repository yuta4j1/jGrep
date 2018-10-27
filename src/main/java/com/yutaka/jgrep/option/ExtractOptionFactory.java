package com.yutaka.jgrep.option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtractOptionFactory {

	private static Map<String, ExtractOption> extractOptionMap = new HashMap<>();

	static {
		extractOptionMap.put("l", (path, keyword, allLine,
				fnTestList, result) -> {
			List<String> output = result.getOutput();
			boolean ok = false;
			for (String line : allLine) {
				if (fnTestList.stream().allMatch(predicate -> predicate.test(line, keyword))) {
					if (!ok) {
						output.add(path.toString());
						output.add(line);
						ok = true;
					} else {
						output.add(line);
					}
				}
			}
		});
	}

	public static ExtractOption createExtractOption(List<String> options) {
		List<ExtractOption> list = options.stream()
				.filter(option -> extractOptionMap.containsKey(option))
				.map(extractOptionMap::get).collect(Collectors.toList());
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

}
