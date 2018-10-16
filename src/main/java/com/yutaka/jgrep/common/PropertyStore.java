package com.yutaka.jgrep.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyStore {

	private static final Properties properties = new Properties();

	static {
		try (InputStream in = new BufferedInputStream(
				Files.newInputStream(Paths.get(Util.joinPath("resources", "app.properties"))))) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPropValue(String key) {
		return properties.getProperty(key);
	}

}
