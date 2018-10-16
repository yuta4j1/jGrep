package com.yutaka.jgrep;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import com.yutaka.jgrep.common.PropertyStore;
import com.yutaka.jgrep.common.Util;
import com.yutaka.jgrep.entity.Result;

public class LogWriter implements ResultOutput {

	private static final String infoLogPrefix = "jgrep-info_";

	public void output(Result result) {
		List<String> outputLine = result.getOutput();
		String logName = infoLogPrefix + getStrTimestamp() + ".log";
		String logDir = Util.correctSeparator(PropertyStore.getPropValue("logPath"));
		final String logPath = logDir + Util.SEPARATOR + logName;

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(logPath), Util.ENCODE)) {
			outputLine.forEach(line -> {
				try {
					writer.write(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getStrTimestamp() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		LocalDateTime today = LocalDateTime.now();
		String strTimeStamp = formatter.format(today);
		System.out.println(strTimeStamp);
		return strTimeStamp;
	}

}
