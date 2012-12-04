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

package org.beangle.ems.dictionary.service.impl;

import java.sql.Date;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.pojo.BaseCode;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.dictionary.service.BaseCodeService;

/**
 * @author chaostone
 * @version $Id: BaseCodeServiceImpl.java May 5, 2011 3:33:07 PM chaostone $
 */
public class BaseCodeServiceImpl extends BaseServiceImpl implements BaseCodeService {

  public <T extends BaseCode<Integer>> T getCode(Class<T> codeClass, String code) {
    OqlBuilder<T> builder = OqlBuilder.from(codeClass, "basecode").where("basecode.code=:code", code);
    List<T> rs = entityDao.search(builder);
    if (!rs.isEmpty()) return rs.get(0);
    else return null;
  }

  public <T extends BaseCode<Integer>> List<T> getCodes(Class<T> codeClass) {
    OqlBuilder<T> builder = OqlBuilder.from(codeClass, "basecode").where(
        "basecode.effectiveAt <= :now and (basecode.invalidAt is null or basecode.invalidAt >= :now)",
        new java.util.Date());
    builder.orderBy("basecode.code");
    return entityDao.search(builder);
  }

  public <T extends BaseCode<Integer>> T getCode(Class<T> codeClass, Integer codeId) {
    return entityDao.get(codeClass, codeId);
  }

  public <T extends BaseCode<Integer>> List<T> getCodes(Class<T> type, Integer... ids) {
    OqlBuilder<T> builder = OqlBuilder.from(type, "basecode").where("basecode.id in(:ids)", ids);
    return entityDao.search(builder);
  }

  @SuppressWarnings("unchecked")
  public Class<? extends BaseCode<Integer>> getCodeType(String name) {
    OqlBuilder<CodeMeta> builder = OqlBuilder.from(CodeMeta.class, "coder");
    builder.where("coder.name=:name or coder.engName=:name", name);
    List<CodeMeta> coders = entityDao.search(builder);
    try {
      if (1 != coders.size()) return null;
      else return (Class<? extends BaseCode<Integer>>) Class.forName(coders.get(0).getClassName());
    } catch (ClassNotFoundException e) {
      logger.error("Basecode " + name + "type not found", e);
      throw new RuntimeException(e);
    }
  }

  public void removeCodes(Class<? extends BaseCode<Integer>> codeClass, Integer... codeIds) {
    entityDao.remove(entityDao.get(codeClass, codeIds));
  }

  public void saveOrUpdate(BaseCode<Integer> code) {
    if (code.isTransient()) {
      code.setCreatedAt(new Date(System.currentTimeMillis()));
    }
    code.setUpdatedAt(new Date(System.currentTimeMillis()));
    entityDao.saveOrUpdate(code);
  }
}
