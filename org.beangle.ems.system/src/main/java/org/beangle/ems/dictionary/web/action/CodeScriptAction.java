/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import java.util.Date;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.ems.dictionary.model.CodeScript;
import org.beangle.ems.dictionary.service.CodeFixture;
import org.beangle.ems.dictionary.service.CodeGenerator;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.helper.Params;

/**
 * 代码生成脚本
 * 
 * @author chaostone
 * @version $Id: CodeScriptAction.java Jun 29, 2011 5:17:46 PM chaostone $
 */
public class CodeScriptAction extends SecurityActionSupport {

  private CodeGenerator codeGenerator;

  /**
   * 主页面
   */
  public String index() {
    return forward();
  }

  /**
   * 搜索
   */
  public String search() {
    OqlBuilder<CodeScript> builder = OqlBuilder.from(CodeScript.class, "codeScript").limit(getPageLimit())
        .orderBy(get("orderBy"));
    populateConditions(builder);
    put("codeScripts", entityDao.search(builder));
    return forward();
  }

  /**
   * 编辑
   */
  public String edit() {
    Integer codeScriptId = getInt("codeScriptId");
    CodeScript codeScript = null;
    if (null == codeScriptId) {
      codeScript = (CodeScript) populate(CodeScript.class, "codeScript");
    } else {
      codeScript = (CodeScript) entityDao.get(CodeScript.class, codeScriptId);
    }
    put("codeScript", codeScript);
    return forward();
  }

  /**
   * 查看
   */
  public String info() {
    CodeScript codeScript = (CodeScript) entityDao.get(CodeScript.class, getInt("codeScriptId"));
    put("codeScript", codeScript);
    return forward();
  }

  /**
   * 保存
   */
  public String save() {
    Integer codeScriptId = getInt("codeScript.id");
    CodeScript codeScript = null;
    if (null == codeScriptId) {
      codeScript = new CodeScript();
      codeScript.setCreatedAt(new Date(System.currentTimeMillis()));
    } else {
      codeScript = (CodeScript) entityDao.get(CodeScript.class, codeScriptId);
    }
    codeScript.setUpdatedAt(new Date(System.currentTimeMillis()));
    Model.populate(Params.sub("codeScript"), codeScript);
    entityDao.saveOrUpdate(codeScript);
    return redirect("search", "info.save.success");
  }

  /**
   * 保存
   */
  public String test() {
    CodeFixture codeFixture = new CodeFixture(Params.sub("codeFixture"));
    String testResult = codeGenerator.test(codeFixture);
    if (null == testResult) {
      testResult = "null";
    }
    put("testResult", testResult);
    return forward(new Action(this.getClass(), "edit"));
  }

  /**
   * 删除
   */
  public String remove() {
    Integer codeScriptId = getInt("codeScriptId");
    entityDao.remove(entityDao.get(CodeScript.class, codeScriptId));
    return redirect("search", "info.delete.success");
  }

  public void setCodeGenerator(CodeGenerator codeGenerator) {
    this.codeGenerator = codeGenerator;
  }

}
