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
package org.beangle.ems.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.ems.web.tag.component.AvatarImage;
import org.beangle.ems.web.tag.component.Code;
import org.beangle.ems.web.tag.component.Guard;
import org.beangle.ems.web.tag.component.Userinfo;
import org.beangle.security.access.AuthorityManager;
import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.tag.AbstractModels;
import org.beangle.struts2.view.tag.TagModel;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @since 3.0.2
 */
public class EmsModels extends AbstractModels {

  AuthorityManager authorityManager;

  public EmsModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    super(stack, req, res);
  }

  public TagModel getGuard() {
    TagModel model = models.get(Guard.class);
    if (null == model) {
      model = new TagModel(stack) {
        protected Component getBean() {
          return new Guard(stack, authorityManager);
        }
      };
      models.put(Guard.class, model);
    }
    return model;
  }

  public TagModel getAvatar() {
    TagModel model = models.get(AvatarImage.class);
    if (null == model) {
      model = new TagModel(stack) {
        protected Component getBean() {
          return new AvatarImage(stack, authorityManager);
        }
      };
      models.put(AvatarImage.class, model);
    }
    return model;
  }

  public TagModel getUserinfo() {
    TagModel model = models.get(Userinfo.class);
    if (null == model) {
      model = new TagModel(stack) {
        protected Component getBean() {
          return new Userinfo(stack, authorityManager);
        }
      };
      models.put(Userinfo.class, model);
    }
    return model;
  }

  public TagModel getCode() {
    return get(Code.class);
  }
}
