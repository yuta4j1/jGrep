package com.yutaka.jgrep.entity;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiPredicate;

import com.yutaka.jgrep.option.ExtractOption;

public class ExtractLineParameter {

	private Path path;
	private String keyword;
	private List<BiPredicate<String, String>> fnTestList;
	private ExtractOption extractOption;

	public ExtractLineParameter(Path path, String keyword,
			List<BiPredicate<String, String>> predicates, ExtractOption extraOption) {
		this.path = path;
		this.keyword = keyword;
		this.fnTestList = predicates;
		this.extractOption = extraOption;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return this.path;
	}
	public String getKeyword() {
		return this.keyword;
	}
	public List<BiPredicate<String, String>> getFnTestList() {
		return this.fnTestList;
	}
	public ExtractOption getExtraOption() {
		return this.extractOption;
	}

	public static class Builder {
		private Path path;
		private String keyword;
		private List<BiPredicate<String, String>> fnTestList;
		private ExtractOption extractOption;

		public Builder setPath(Path path) {
			this.path = path;
			return this;
		}
		public Builder setKeyword(String keyword) {
			this.keyword = keyword;
			return this;
		}
		public Builder setFnTestList(List<BiPredicate<String, String>> predicates) {
			this.fnTestList = predicates;
			return this;
		}
		public Builder setExtractOption(ExtractOption extraOption) {
			this.extractOption = extraOption;
			return this;
		}
		public ExtractLineParameter build() {
			return new ExtractLineParameter(this.path, this.keyword,
					this.fnTestList, this.extractOption);
		}
	}

}
