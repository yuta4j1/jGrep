package com.yutaka.jgrep.option;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiPredicate;

import com.yutaka.jgrep.entity.Result;

@FunctionalInterface
public interface ExtractOption {

	public abstract void apply(Path path, String keyword, List<String> allLine,
			List<BiPredicate<String, String>> fnTestList, Result result);

}
