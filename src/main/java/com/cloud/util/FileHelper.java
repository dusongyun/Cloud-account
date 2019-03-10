package com.cloud.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class FileHelper {
	public static String readFile(String filename) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String ans = "", line = null;
		while ((line = reader.readLine()) != null) {
			ans += line + "\r\n";
		}
		reader.close();
		return ans;
	}

	public static void writeFile(String content, String filename) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(content);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		String ans = readFile("D:\\input.txt");
		writeFile(ans, "D:\\output.txt");
	}
}
