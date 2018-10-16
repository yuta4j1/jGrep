package com.yutaka.jgrep.grep;

import java.nio.file.Path;

import com.yutaka.jgrep.entity.Result;

@FunctionalInterface
public interface GrepExecutor {
	Result execute(final Path dir, final String keyword);
}
