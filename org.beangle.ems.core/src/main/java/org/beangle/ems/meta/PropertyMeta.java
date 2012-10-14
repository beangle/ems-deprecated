/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.meta;

import org.beangle.commons.entity.pojo.LongIdEntity;

/**
 * 属性元信息
 * </p>
 * 记录实体的属性元信息描述。
 */
public interface PropertyMeta extends LongIdEntity {

  /**
   * 获得实体元信息
   * 
   * @return 实体元信息
   */
  EntityMeta getMeta();

  /**
   * 设置实体元信息
   * 
   * @param meta 实体元信息
   */
  void setMeta(EntityMeta meta);

  /**
   * 获得属性名
   * 
   * @return 属性名
   */
  String getName();

  /**
   * 设置属性名
   * 
   * @param name 属性名
   */
  void setName(String name);

  /**
   * 获得属性类型
   * 
   * @return 属性类型
   */
  String getType();

  /**
   * 设置属性类型
   * 
   * @param type 属性类型
   */
  void setType(String type);

  /**
   * 获得备注
   * 
   * @return 备注
   */
  String getComments();

  /**
   * 设置备注
   * 
   * @param comments 备注
   */
  void setComments(String comments);
}
