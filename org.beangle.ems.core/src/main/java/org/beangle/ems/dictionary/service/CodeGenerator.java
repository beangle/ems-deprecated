/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
