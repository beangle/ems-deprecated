/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.beangle.security.web.auth.preauth.UsernameSource;

public class GivenUsernameSource implements UsernameSource {

  public String obtainUsername(HttpServletRequest request) {
    return "admin";
  }

  public static void replaceFile(String fileName, String encoding) throws Exception, FileNotFoundException {
    File file = new File(fileName);
    if (file.isFile() && !file.isHidden()) {
      String filecontent = FileUtils.readFileToString(file, encoding);
      if (!filecontent.startsWith("/* Copyright c 2005-2012")) {
        System.out.println(fileName);
      }
    } else {
      String[] subFiles = file.list(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          if (new File(dir.getAbsolutePath() + "/" + name).isDirectory()) return true;
          else return name.endsWith(".java");
        }
      });
      if (null != subFiles) {
        for (int i = 0; i < subFiles.length; i++) {
          replaceFile(fileName + '/' + subFiles[i], encoding);
        }
      }
    }
  }

  public static void main(String[] args) throws FileNotFoundException, Exception {
    replaceFile("/home/chaostone/beangle/library", "utf-8");
  }
}
