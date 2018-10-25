package com.yutaka.jgrep.grep;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import com.yutaka.jgrep.common.Util;
import com.yutaka.jgrep.entity.ExtractLineParameter;
import com.yutaka.jgrep.entity.PathStore;
import com.yutaka.jgrep.entity.Result;
import com.yutaka.jgrep.option.ExtractOptionFactory;
import com.yutaka.jgrep.option.TestFactory;
import com.yutaka.jgrep.repository.FileRepository;

public class ExecutorBuilder {

	private static final String RECURSIVE = "r";
	private static final String CONTENT = "i";

	private List<String> options = new ArrayList<>();

	public ExecutorBuilder(List<String> options) {
		this.options = options;
	}

	public GrepExecutor createExecutor() {

		GrepExecutor executor = null;
		if (isRecursive()) {
			if (isContent()) {
				executor = (dir, keyword) -> {
					PathStore pathStore = new PathStore();
					Result result = new Result();
					contentGrep(dir, keyword, pathStore, result);
					if (!pathStore.isEmpty()) {
						contentGrep(pathStore.deQueue(), keyword, pathStore, result);
					}
					return result;
				};
			} else {
				executor = (dir, keyword) -> {
					PathStore pathStore = new PathStore();
					Result result = new Result();
					List<BiPredicate<String, String>> fnTests = TestFactory.createTests(options);
					fileNameGrep(dir, keyword, fnTests, pathStore, result);
					if (!pathStore.isEmpty()) {
						fileNameGrep(pathStore.deQueue(), keyword, fnTests, pathStore, result);
					}
					return result;
				};
			}
		} else {
			if (isContent()) {
				executor = (dir, keyword) -> {
					Result result = new Result();
					contentGrep(dir, keyword, result);
					return result;
				};
			} else {
				executor = (dir, keyword) -> {
					Result result = new Result();
					List<BiPredicate<String, String>> fnTests = TestFactory.createTests(options);
					fileNameGrep(dir, keyword, fnTests, result);
					return result;
				};
			}

		}
		return executor;
	}

	private void contentGrep(Path dir, String keyword, Result result) {
		List<Path> paths = FileRepository.getPathList(dir);
		ExtractLineParameter exlParam = new ExtractLineParameter.Builder().setPath(dir)
				.setKeyword(keyword).setFnTestList(TestFactory.createTests(options))
				.setExtractOption(ExtractOptionFactory.createExtractOption(options)).build();
		for (Path path : paths) {
			exlParam.setPath(path);
			FileRepository.extractLine(exlParam, result);
		}
	}


	private void contentGrep(Path dir, String keyword, PathStore pathStore, Result result) {
		List<Path> paths = FileRepository.getPathListInclusiveDir(dir, pathStore);
		ExtractLineParameter exlParam = new ExtractLineParameter.Builder().setPath(dir)
				.setKeyword(keyword).setFnTestList(TestFactory.createTests(options))
				.setExtractOption(ExtractOptionFactory.createExtractOption(options)).build();
		for (Path path : paths) {
			exlParam.setPath(path);
			FileRepository.extractLine(exlParam, result);
		}
	}

	private void fileNameGrep(Path dir, String keyword,
			List<BiPredicate<String, String>> fnTests, PathStore pathStore, Result result) {
		List<Path> paths = FileRepository.getPathListInclusiveDir(dir, pathStore);
		for (Path path : paths) {
			boolean checked = fnTests.stream()
					.anyMatch(predicate -> predicate.test(Util.getFileName(path), keyword));
			if (checked) {
				result.addLine(path.toString());
			}
		}
	}

	private void fileNameGrep(Path dir, String keyword,
			List<BiPredicate<String, String>> fnTests, Result result) {
		List<Path> paths = FileRepository.getPathList(dir);
		for (Path path : paths) {
			boolean checked = fnTests.stream()
					.anyMatch(predicate -> predicate.test(Util.getFileName(path), keyword));
			if (checked) {
				result.addLine(path.toString());
			}
		}
	}

	private boolean isRecursive() {
		return this.options.contains(RECURSIVE);
	}

	private boolean isContent() {
		return this.options.contains(CONTENT);
	}


}
