package org.beangle.ems.wiki.web.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.beangle.ems.web.action.SecurityActionSupport;

import com.petebevin.markdown.MarkdownProcessor;

public class IndexAction extends SecurityActionSupport {

  public String index() throws IOException {
    String file = get("file");
    MarkdownProcessor processor = new MarkdownProcessor();
    String raw = FileUtils.readFileToString(new File(file));
    put("res", processor.markdown(raw));
    return forward();
  }
}
