package org.beangle.ems.wiki;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.io.Files;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petebevin.markdown.MarkdownProcessor;

public class WikiMain {

	public static void main(String[] args) throws IOException {
		Logger logger = LoggerFactory.getLogger(WikiMain.class);
		logger.info("d", 1, 2);
		String path = args[0];
		List<String> toc = FileUtils.readLines(new File(path));
		List<String[]> items = extract(toc);
		String rootDir = StringUtils.substringBeforeLast(path, "/");
		String template = FileUtils.readFileToString(new File(rootDir
				+ "/report.ftl"));
		MarkdownProcessor processor = new MarkdownProcessor();
		StringBuilder sb = new StringBuilder();
		File index = new File(path);
		if (index.exists())
			sb.append(processor.markdown(Files.readFileToString(index)));

		int func = 0;
		for (String[] item : items) {
			File itemmd = new File(rootDir + "/" + item[2] + ".md");
			if (item[2].contains("/")) {
				sb.append("<h3>" + item[0] + " " + item[1] + "</h3>");
			} else {
				sb.append("<h2>" + item[0] + " " + item[1] + "</h2>");
			}
			if (itemmd.exists()) {
				sb.append(processor.markdown(Files.readFileToString(itemmd)));
				// 统计功能点个数
				List<String> lines = Files.readLines(itemmd);
				for (String l : lines) {
					String start = Strings.substringBefore(l, ".");
					if (Strings.isNotEmpty(start)
							&& start.length() == start.trim().length()) {
						if (Numbers.isDigits(start)) {
							func++;
						}
					}
				}
			}
		}
		template = StringUtils.replace(template, "${body}", sb.toString());
		File rs = new File("/tmp/wiki.html");
		FileUtils.writeStringToFile(rs, template);
		System.out.println("menu:" + items.size());
		System.out.println("function:" + func);
		System.out.println("html:" + rs.getAbsolutePath());
	}

	public static void merge(String[] args) throws IOException {
		String path = args[0];
		List<String> toc = FileUtils.readLines(new File(path));
		List<String[]> items = extract(toc);
		String rootDir = StringUtils.substringBeforeLast(path, "/");
		StringBuilder sb = new StringBuilder();
		File index = new File(path);
		if (index.exists())
			sb.append(Files.readFileToString(index));

		for (String[] item : items) {
			File itemmd = new File(rootDir + "/" + item[2] + ".md");
			if (item[2].contains("/")) {
				sb.append("### " + item[0] + " " + item[1] + "\n");
			} else {
				sb.append("## " + item[0] + " " + item[1] + "\n");
			}
			if (itemmd.exists()) {
				sb.append(Files.readFileToString(itemmd));
			}
		}
		File rs = new File("/tmp/wiki.md");
		FileUtils.writeStringToFile(rs, sb.toString());
	}

	public static List<String[]> extract(List<String> toc) {
		List<String[]> rs = new ArrayList<String[]>();
		String parent = null;
		for (String item : toc) {
			String wikiname = StringUtils.substringBetween(item, "(", ")");
			if (null != wikiname) {
				if (Character.isLowerCase(wikiname.charAt(0))) {
					parent = wikiname;
				} else {
					wikiname = parent + "/" + wikiname;
				}
				String title = StringUtils.substringBetween(item, "[", "]");
				String indexno = StringUtils.substringBetween(item, "*", "[");
				if (null == indexno || indexno.isEmpty()) {
					indexno = item.substring(0, item.indexOf('.'));
					title = StringUtils.substringBetween(item, ".", "(").trim();
				}
				indexno = indexno.trim();
				rs.add(new String[] { indexno, title, wikiname });
			}
		}
		return rs;
	}
}
