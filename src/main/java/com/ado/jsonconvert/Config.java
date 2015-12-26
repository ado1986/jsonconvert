package com.ado.jsonconvert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config {
	private Config() {

	}

	private static Config _INSTANCE = new Config();

	private static Properties prop = new Properties();

	static {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("env.conf");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Config getInstacne() {
		return _INSTANCE;
	}

	public List<String> getHostNameList() {
		String hostnameList = (String) prop.get("hostnamelist");

		return convert(hostnameList, ",");
	}

	public List<String> getHostList() {
		String hostList = (String) prop.get("hostlist");

		return convert(hostList, ",");
	}

	private List<String> convert(String source, String pattern) {
		List<String> ret = new ArrayList<String>();
		String[] list = source.split(pattern);
		for (int i = 0; i < list.length; i++) {
			ret.add(list[i]);
		}
		return ret;
	}

}
