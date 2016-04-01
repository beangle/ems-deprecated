/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.ems.rule;

import java.util.List;
import java.util.Map;

import org.beangle.commons.text.i18n.Message;

/**
 * 规则执行上下文
 * 
 * @author chaostone
 */
public interface Context {

  Map<String, Object> getParams();

  List<Message> getMessages();

  void addMessage(Message message);

  List<Message> getErrors();

  void addError(Message message);
}
