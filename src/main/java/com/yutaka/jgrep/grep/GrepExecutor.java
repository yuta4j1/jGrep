package com.yutaka.jgrep.grep;

import java.nio.file.Path;

@FunctionalInterface
public interface GrepExecutor {
	void execute(final Path dir, final String keyword);
}
