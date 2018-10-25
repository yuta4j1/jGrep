package com.yutaka.jgrep.option;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import lombok.Data;

@Data
public class OptionManager {

	@Parameter
    public List<String> parameters = Lists.newArrayList();

	@Parameter(names = {"-i", "i", "--inner"}, description = "grep files that contains the target keyword")
	private boolean inner = false;

	@Parameter(names = {"-e", "e", "--regexp"}, description = "grep by regular expression")
	private boolean regexp = false;

	@Parameter(names = {"-l", "l", "--lineNum"}, description = "display extracted line number")
	private boolean lineNum = false;

	@Parameter(names = {"-r", "r", "--recursive"}, description = "grep files recursively")
	private boolean recursive = false;

}
