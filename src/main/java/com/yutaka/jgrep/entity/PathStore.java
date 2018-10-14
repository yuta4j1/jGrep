package com.yutaka.jgrep.entity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 探索対象ディレクトリ格納クラス
 *
 */
public class PathStore {

	/** キュー */
	private List<Path> dirs = new ArrayList<>();

	public PathStore() {
	}

	/**
	 * コンストラクタ。
	 *
	 * @param paths
	 */
	public PathStore(List<Path> paths) {
		dirs.addAll(paths);
	}

	public void add(List<Path> paths) {
		dirs.addAll(paths);
	}

	/**
	 * ディレクトリをキューに格納。
	 *
	 * @param path ディレクトリパス
	 */
	public void queue(final Path path) {
		this.dirs.add(path);
	}

	/**
	 * ディレクトリをキューから取り出す。
	 *
	 * @return ディレクトリパス
	 */
	public Path deQueue() {
		Path getPath = this.dirs.get(0);
		this.dirs.remove(0);
		return getPath;
	}

	public boolean isEmpty() {
		return dirs.size() == 0;
	}

}
