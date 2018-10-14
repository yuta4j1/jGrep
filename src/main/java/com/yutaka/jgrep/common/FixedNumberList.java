package com.yutaka.jgrep.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 固定長リストクラス
 *
 * @param <E>
 */
public class FixedNumberList<E> {

	private List<E> fixNumList = new ArrayList<>();
	private int listSize;

	public FixedNumberList(int size) {
		this.listSize = size;
	}

	public void add(E elem) {
		if (fixNumList.size() >= listSize) {
			fixNumList.add(elem);
			int len = fixNumList.size();
			this.fixNumList = fixNumList.subList((len - listSize) - 1, len - 1);
		} else {
			fixNumList.add(elem);
		}
	}

}
