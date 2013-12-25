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
package org.beangle.ems.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.security.access.AuthorityManager;
import org.beangle.struts2.view.tag.AbstractTagLibrary;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @since 2.4
 */
public class EmsTagLibrary extends AbstractTagLibrary {

  AuthorityManager authorityManager;

  public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    if (null == authorityManager) {
      Container container = (Container) stack.getContext().get(ActionContext.CONTAINER);
      ObjectFactory objectFactory = container.getInstance(ObjectFactory.class);
      try {
        authorityManager = (AuthorityManager) objectFactory.buildBean("authorityManager", null, false);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    EmsModels models = new EmsModels(stack, req, res);
    models.authorityManager = authorityManager;
    return models;
  }

}
