/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.meta;

import org.beangle.commons.entity.pojo.LongIdEntity;

/**
 * 实体元信息
 * </p>
 * 记录实体信息的元信息描述。
 * 
 * @author chaostone
 * @since 2.3.0
 */
public interface EntityMeta extends LongIdEntity {
  /**
   * 获得实体名称
   * 
   * @return 实体名称
   */
  String getName();

  /**
   * 设置实体名称
   * 
   * @param name 实体名称
   */
  void setName(String name);

  /**
   * 获得实体说明
   * 
   * @return 实体说明
   */
  String getComments();

  /**
   * 设置实体说明
   * 
   * @param comments 实体说明
   */
  void setComments(String comments);

  /**
   * 获得实体备注
   * 
   * @return 实体备注
   */
  String getRemark();

  /**
   * 设置实体备注
   * 
   * @param remark 实体备注
   */
  void setRemark(String remark);
}
