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

import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.dictionary.model.CodeCategory;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 代码原信息管理类
 * 
 * @author chaostone
 * @version $Id: CodeMetaAction.java Jun 29, 2011 5:20:35 PM chaostone $
 */
public class CodeMetaAction extends SecurityActionSupport {

  @Override
  public String index() throws Exception {
    put(getShortName() + "s", search(getQueryBuilder()));
    return forward();
  }

  public String categories() {
    put("categories", entityDao.getAll(CodeCategory.class));
    return forward();
  }

  @Override
  protected void editSetting(Entity<?> entity) {
    put("categories", entityDao.getAll(CodeCategory.class));
  }

  public String saveCategory() {
    List<CodeCategory> categories = entityDao.getAll(CodeCategory.class);
    List<CodeCategory> updated = CollectUtils.newArrayList();
    Set<String> names = CollectUtils.newHashSet();
    Set<String> duplicatedNames = CollectUtils.newHashSet();
    for (CodeCategory category : categories) {
      CodeCategory newCategory = populateEntity(CodeCategory.class, category.getId() + "_codeCategory");
      if (names.contains(newCategory.getName())) {
        duplicatedNames.add(newCategory.getName());
      } else {
        names.add(newCategory.getName());
        updated.add(newCategory);
      }
    }
    CodeCategory newCategory = populateEntity(CodeCategory.class, "newCodeCategory");
    if (Strings.isNotBlank(newCategory.getName())) {
      if (names.contains(newCategory.getName())) {
        duplicatedNames.add(newCategory.getName());
      } else {
        names.add(newCategory.getName());
        updated.add(newCategory);
      }
    }
    if (duplicatedNames.size() > 0) {
      addError("dictionary.error.duplicateCategoryName", Strings.join(duplicatedNames, ","));
      return forward(new Action(this, "categories"));
    } else {
      entityDao.save(updated);
      return redirect("categories", "info.save.success");
    }
  }

  public String removeCategory() {
    CodeCategory newCategory = getEntity(CodeCategory.class, "codeCategory");
    try {
      if (null != newCategory.getId()) entityDao.remove(newCategory);
    } catch (Exception e) {
      logger.error("code category remove error :" + newCategory.getName(), e);
      return redirect("categories", "info.remove.failure");
    }
    return redirect("categories", "info.remove.success");
  }

  @Override
  protected String getEntityName() {
    return CodeMeta.class.getName();
  }

  public String save() {
    CodeMeta meta = populateEntity(CodeMeta.class, "codeMeta");
    boolean error = false;
    if (entityDao.duplicate(CodeMeta.class, meta.getId(), "name", meta.getName())) {
      error = true;
      addError("基础代码名称不能重复");
    }
    if (entityDao.duplicate(CodeMeta.class, meta.getId(), "title", meta.getTitle())) {
      error = true;
      addError("基础代码标题不能重复");
    }
    if (entityDao.duplicate(CodeMeta.class, meta.getId(), "className", meta.getClassName())) {
      error = true;
      addError("基础代码实体类不能重复");
    }

    if (!error) {
      addFlashMessage("info.save.success");
      entityDao.saveOrUpdate(meta);
      return redirect("index");
    } else {
      return forward(new Action(this, "edit"));
    }
  }

}
