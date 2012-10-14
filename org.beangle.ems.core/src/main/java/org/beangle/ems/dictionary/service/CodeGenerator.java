/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.service;

/**
 * 代码生成器
 * 
 * @author chaostone
 * @version $Id: CodeGenerator.java May 5, 2011 3:49:45 PM chaostone $
 */

public interface CodeGenerator {

  static final int MAX_LENGTH = 50;

  static final String MARK = "******";

  /**
   * 根据实体类的信息,生成一个代码
   * 
   * @param fixture
   */
  String gen(CodeFixture fixture);

  /**
   * 测试脚本
   * 
   * @param fixture
   */
  String test(CodeFixture fixture);

  /**
   * 判断是否是合法编码
   * 
   * @param code
   */
  boolean isValidCode(String code);
}
