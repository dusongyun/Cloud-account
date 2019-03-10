package com.cloud.util;

import java.util.StringTokenizer;

public class WebpageMaker {
	public static String initBegin() {
		String s = "<!doctype html><html><head><title></title></head><body>\r\n";
		return s;
	}

	public static String initEnd() {
		String s = "\r\n</body></html>\r\n";
		return s;
	}

	public static void createPage(String inputfilename, String outputfilename) throws Exception {
		String content = FileHelper.readFile(inputfilename);
		StringTokenizer st = new StringTokenizer(content, "\r\n");
		String ans = "";
		ans += initBegin();
		boolean isCoding = false;
		while (st.hasMoreElements()) {
			String s = st.nextToken();
			int len = s.length();
			for (int i = 0; i < len; i++) {
				if (i + 6 <= len && s.substring(i, i + 6).equals("<alex>")) {
					isCoding = true;
					ans += "<pre style=\"background-color:aliceblue\">";
					i += 5;
					continue;
				}
				if (i + 7 <= len && s.substring(i, i + 7).equals("</alex>")) {
					isCoding = false;
					ans += "</pre>";
					i += 6;
					continue;
				}
				char c = s.charAt(i);
				if (c == '\"')
					ans += "\"";
				else if (c == '&')
					ans += "&";
				else if (c == '<')
					ans += "<";
				else if (c == '>')
					ans += ">";
				else if (c == ' ')
					ans += " ";
				else if (c == '\t')
					ans += "    ";
				else
					ans += c;
			}
			if (false == isCoding)
				ans += "<br />\r\n";
			else
				ans += "\r\n";
		}
		ans += initEnd();
		FileHelper.writeFile(ans, outputfilename);
	}

	public static void main(String[] args) throws Exception {
		createPage("D://test.txt", "D://test.html");
	}
}
