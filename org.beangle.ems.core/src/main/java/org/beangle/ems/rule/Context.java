/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule;

import java.util.List;
import java.util.Map;

import org.beangle.commons.i18n.Message;

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
