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
package org.beangle.ems.security.helper;

import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.security.blueprint.Resource;

/**
 * @author chaostone
 * @version $Id: ResourcePropertyExtractor.java Jul 17, 2011 9:41:20 PM chaostone $
 */
public class ResourcePropertyExtractor extends DefaultPropertyExtractor {

  public ResourcePropertyExtractor() {
    super();
  }

  public ResourcePropertyExtractor(TextResource textResource) {
    super(textResource);
  }

  public Object getPropertyValue(Object target, String property) throws Exception {
    Resource resource = (Resource) target;
    if ("enabled".equals(property)) {
      return resource.isEnabled() ? "激活" : "冻结";
    } else return super.getPropertyValue(target, property);
  }

}
