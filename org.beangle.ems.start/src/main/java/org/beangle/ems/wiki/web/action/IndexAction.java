package org.beangle.ems.wiki.web.action;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.beangle.ems.web.action.SecurityActionSupport;

import com.petebevin.markdown.MarkdownProcessor;

public class IndexAction extends SecurityActionSupport {

  public String index() throws IOException {
    String file = get("file");
    String path = get("path");
    MarkdownProcessor processor = new MarkdownProcessor();
    if (null != file) {
      String raw = FileUtils.readFileToString(new File(file));
      put("file", new File(file));
      put("res", processor.markdown(raw));
    } else if (null != path) {
      StringBuilder sb = new StringBuilder();
      File index = new File(path + "/index.md");
      if (index.exists()) sb.append(processor.markdown(FileUtils.readFileToString(index)));
      File dir = new File(path);
      put("file", dir);
      File[] mds = dir.listFiles();
      Arrays.sort(mds);
      for (File md : mds) {
        if (!md.getName().equals("index.md")) sb.append(processor.markdown(FileUtils.readFileToString(md)));
      }
      put("res", sb.toString());
    } else {
      put("res", "need file= parameter");
    }
    return forward();
  }

  public static void main(String[] args) {

  }
}
