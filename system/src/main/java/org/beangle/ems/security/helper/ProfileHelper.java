/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.functor.Predicate;
import org.beangle.security.blueprint.Dimension;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.Property;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.service.ProfileService;
import org.beangle.security.blueprint.service.UserDataResolver;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;

public class ProfileHelper {

  final EntityDao entityDao;

  final ProfileService profileService;

  UserDataResolver identifierDataResolver;

  public ProfileHelper(EntityDao entityDao, ProfileService profileService) {
    super();
    this.entityDao = entityDao;
    this.profileService = profileService;
  }

  /**
   * 查看限制资源界面
   */
  public void populateInfo(List<? extends Profile> profiles) {
    Map<String, Map<String, Object>> fieldMaps = CollectUtils.newHashMap();
    for (Profile profile : profiles) {
      Map<String, Object> aoDimensions = CollectUtils.newHashMap();
      for (Property property : profile.getProperties()) {
        String value = property.getValue();
        String fieldName = property.getDimension().getName();
        if (Strings.isNotEmpty(value)) {
          if (null == property.getDimension().getSource()) {
            aoDimensions.put(fieldName, value);
          } else if (value.equals("*")) {
            aoDimensions.put(fieldName, "不限");
          } else {
            aoDimensions.put(fieldName, profileService.getProperty(profile, property.getDimension()));
          }
        }
      }
      fieldMaps.put(PropertyUtils.getProperty(profile, "id").toString(), aoDimensions);
    }
    ContextHelper.put("profiles", profiles);
    ContextHelper.put("fieldMaps", fieldMaps);
  }

  public void fillEditInfo(Profile profile, Long userId, boolean isAdmin) {
    Map<String, Object> mngDimensions = CollectUtils.newHashMap();
    Map<String, Object> aoDimensions = CollectUtils.newHashMap();

    List<Profile> myProfiles = entityDao.get(User.class, userId).getProfiles();
    Set<Dimension> ignores = getIgnoreDimensions(myProfiles);
    ContextHelper.put("ignoreDimensions", ignores);
    Set<Dimension> holderIgnoreDimensions = CollectUtils.newHashSet();
    ContextHelper.put("holderIgnoreDimensions", holderIgnoreDimensions);
    List<Dimension> fields = entityDao.getAll(Dimension.class);
    ContextHelper.put("fields", fields);
    for (Dimension field : fields) {
      List<?> mngDimensionValues = profileService.getDimensionValues(field);
      if (!isAdmin) mngDimensionValues.retainAll(getMyProfileValues(myProfiles, field));
      else ignores.add(field);

      String fieldValue = "";
      Property property = profile.getProperty(field);
      if (null != property) fieldValue = property.getValue();
      if ("*".equals(fieldValue)) holderIgnoreDimensions.add(field);

      mngDimensions.put(field.getName(), mngDimensionValues);
      if (null == field.getSource()) {
        aoDimensions.put(field.getName(), fieldValue);
      } else {
        aoDimensions.put(field.getName(), profileService.getProperty(profile, field));
      }
    }
    ContextHelper.put("mngDimensions", mngDimensions);
    ContextHelper.put("aoDimensions", aoDimensions);
    ContextHelper.put("profile", profile);
  }

  @SuppressWarnings("unchecked")
  public void populateSaveInfo(Profile profile, Long userId, Boolean isAdmin) {
    List<Profile> myProfiles = entityDao.get(User.class, userId).getProfiles();
    Set<Dimension> ignoreDimensions = getIgnoreDimensions(myProfiles);
    for (final Dimension field : entityDao.getAll(Dimension.class)) {
      String[] values = (String[]) Params.getAll(field.getName());
      if ((ignoreDimensions.contains(field) || isAdmin)
          && Params.getBool("ignoreDimension" + field.getId())) {
        profile.setProperty(field, "*");
      } else {
        if (null == values || values.length == 0) {
          profile.setProperty(field, null);
        } else {
          String storedValue = null;
          if (null != field.getKeyName()) {
            final Set<String> keys = CollectUtils.newHashSet(values);
            List<Object> allValues = null;
            Collection<?> originValues = profileService.getDimensionValues(field);
            if (originValues instanceof List<?>) {
              allValues = (List<Object>) originValues;
            } else {
              allValues = CollectUtils.newArrayList(originValues);
            }
            allValues = CollectUtils.select(allValues, new Predicate<Object>() {
              public Boolean apply(Object arg0) {
                try {
                  String keyValue = String.valueOf(PropertyUtils.getProperty(arg0, field.getKeyName()));
                  return keys.contains(keyValue);
                } catch (Exception e) {
                  e.printStackTrace();
                }
                return false;
              }
            });
            storedValue = identifierDataResolver.marshal(field, allValues);
          } else {
            storedValue = Strings.join(values);
          }
          profile.setProperty(field, storedValue);
        }
      }
    }
  }

  private List<Object> getMyProfileValues(List<Profile> Profiles, Dimension field) {
    List<Object> values = CollectUtils.newArrayList();
    for (Profile profile : Profiles) {
      Property property = profile.getProperty(field);
      if (null != property) {
        String value = property.getValue();
        if (null != value) {
          if (property.getDimension().isMultiple()) {
            values.addAll((Collection<?>) profileService.getProperty(profile, field));
          } else {
            values.add(profileService.getProperty(profile, field));
          }
        }
      }
    }
    return values;
  }

  private Set<Dimension> getIgnoreDimensions(List<Profile> profiles) {
    Set<Dimension> ignores = CollectUtils.newHashSet();
    for (Profile profile : profiles) {
      for (Property property : profile.getProperties()) {
        String value = property.getValue();
        if ("*".equals(value)) ignores.add(property.getDimension());
      }
    }
    return ignores;
  }

  public void setIdentifierDataResolver(UserDataResolver identifierDataResolver) {
    this.identifierDataResolver = identifierDataResolver;
  }

}
