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

package org.beangle.ems.dictionary.service;

import java.util.List;

import org.beangle.commons.entity.pojo.BaseCode;

/**
 * @author chaostone
 * @version $Id: BaseCodeService.java May 4, 2011 7:49:02 PM chaostone $
 */
public interface BaseCodeService {
  /**
   * 依据code的类型和主键查找,无效时返回null.
   * 
   * @param type
   * @param codeId
   */
  <T extends BaseCode<Integer>> T getCode(Class<T> type, Integer codeId);

  /**
   * 依据code的类型和代码查找,无效时返回null.
   * 
   * @param type
   * @param code
   */
  <T extends BaseCode<Integer>> T getCode(Class<T> type, String code);

  /**
   * 返回现有的有效使用的代码
   * 
   * @param type
   */
  <T extends BaseCode<Integer>> List<T> getCodes(Class<T> type);

  /**
   * 查询指定id的基础代码
   * 
   * @param type
   * @param ids
   */
  <T extends BaseCode<Integer>> List<T> getCodes(Class<T> type, Integer... ids);

  /**
   * 查找指定名称基础代码
   * 
   * @param name
   */
  Class<? extends BaseCode<Integer>> getCodeType(String name);

  /**
   * 新增代码 如果新的代码已经存在，则抛出异常.
   * 
   * @param code
   */
  void saveOrUpdate(BaseCode<Integer> code);

  /**
   * 删除基础代码
   * 
   * @param codeClass
   * @param codeIds
   */
  void removeCodes(Class<? extends BaseCode<Integer>> codeClass, Integer... codeIds);
}
