/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.dictionary.web.action;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.pojo.Code;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.dictionary.service.CodeService;
import org.beangle.struts2.action.ActionSupport;

/**
 * 基础代码管理
 * 
 * @author chaostone
 * @version $Id: CodeAction.java Jun 29, 2011 5:19:26 PM chaostone $
 */
public class CodeAction extends ActionSupport {

  private CodeService codeService;
  private EntityDao entityDao;

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public String index() {
    String simpleName = get("type");
    StringBuilder builder = new StringBuilder();
    PrintWriter out = null;
    String format = get("format");
    if (Strings.isNotBlank(simpleName)) {
      Iterator<CodeMeta> it = entityDao.get(CodeMeta.class, "name", simpleName).iterator();
      if (it.hasNext()) {
        try {
          HttpServletResponse response = getResponse();
          response.setContentType("text/xml");
          response.setCharacterEncoding("UTF-8");
          out = response.getWriter();
          @SuppressWarnings("rawtypes")
          Class baseCodeClass = Class.forName(it.next().getClassName());
          if (Code.class.isAssignableFrom(baseCodeClass)) {
            @SuppressWarnings("unchecked")
            List<? extends Code<?>> baseCodes = codeService.getCodes(baseCodeClass);
            if (Strings.isNotEmpty(format)) {
              for (Code<?> baseCode : baseCodes) {
                builder.append("<option value='" + baseCode.getId() + "'>" + baseCode.getName().trim() + "["
                    + PropertyUtils.getProperty(baseCode, format) + "]" + "</option>");
              }
            } else {
              for (Code<?> baseCode : baseCodes) {
                builder
                    .append("<option value='" + baseCode.getId() + "'>" + baseCode.getName() + "</option>");
              }
            }
          }
          out.write(builder.toString());
        } catch (ClassNotFoundException e) {
          out.write("<option value=''>没有该基础代码</option>");
        } catch (Exception e2) {
          out.write("<option value=''>" + format + "不符合规范</option>");
        }
      }
    }
    return null;
  }

  public void setCodeService(CodeService baseCodeService) {
    this.codeService = baseCodeService;
  }

}
