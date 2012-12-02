/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

package org.beangle.ems.security.web.action.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.Operation;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.security.helper.DataPermissionHelper;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.data.Profile;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.Property;
import org.beangle.security.blueprint.data.RoleProfile;
import org.beangle.security.blueprint.data.UserProfile;
import org.beangle.security.blueprint.data.model.RoleProfileBean;
import org.beangle.security.blueprint.data.service.UserDataResolver;
import org.beangle.security.blueprint.model.RoleBean;
import org.beangle.struts2.helper.Params;

/**
 * @author chaostone
 * @version $Id: ProfileAction.java Apr 13, 2012 10:01:12 PM chaostone $
 */
public class ProfileAction extends SecurityActionSupport {
  protected UserDataResolver identifierDataResolver;

  @Override
  protected String getEntityName() {
    return UserProfile.class.getName();
  }

  public String tip() {
    return forward();
  }

  /**
   * 删除数据限制权限
   */
  public String remove() {
    Profile profile = getProfile();
    if (profile instanceof RoleProfileBean) {
      RoleProfileBean rp = (RoleProfileBean) profile;
      ((RoleBean) rp.getRole()).setDynamic(false);
      entityDao.execute(Operation.saveOrUpdate(rp.getRole()).remove(profile));
    } else {
      entityDao.remove(profile);
    }
    return redirect("info", "info.remove.success");
  }

  /**
   * 查看限制资源界面
   */
  public String info() {
    DataPermissionHelper helper = new DataPermissionHelper(entityDao, dataPermissionService);
    helper.populateInfo(getProfiles());
    return forward();
  }

  public void setIdentifierDataResolver(UserDataResolver identifierDataResolver) {
    this.identifierDataResolver = identifierDataResolver;
  }

  public String save() {
    Profile profile = getProfile();
    List<UserProfile> myProfiles = dataPermissionService.getUserProfiles(entityDao.get(User.class,
        getUserId()));
    Set<ProfileField> ignoreFields = getIgnoreFields(myProfiles);
    boolean isAdmin = isAdmin();
    for (final ProfileField field : entityDao.getAll(ProfileField.class)) {
      String[] values = (String[]) getAll(field.getName());
      if ((ignoreFields.contains(field) || isAdmin) && getBool("ignoreField" + field.getId())) {
        profile.setProperty(field, "*");
      } else {
        if (null == values || values.length == 0) {
          profile.setProperty(field, null);
        } else {
          String storedValue = null;
          if (null != field.getType().getKeyName()) {
            final Set<String> keys = CollectUtils.newHashSet(values);
            Collection<?> allValues = dataPermissionService.getFieldValues(field);
            allValues = CollectionUtils.select(allValues, new Predicate() {
              public boolean evaluate(Object arg0) {
                try {
                  String keyValue = String.valueOf(PropertyUtils.getProperty(arg0, field.getType()
                      .getKeyName()));
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

    if (profile.getProperties().isEmpty()) {
      if (((Entity<?>) profile).isPersisted()) entityDao.remove(profile);
      return redirect("info", "info.save.success");
    } else {
      if (profile instanceof RoleProfileBean) {
        RoleProfileBean rp = (RoleProfileBean) profile;
        RoleBean role = entityDao.get(RoleBean.class, rp.getRole().getId());
        role.setDynamic(true);
        entityDao.saveOrUpdate(role);
      }
      entityDao.saveOrUpdate(DataPermissionHelper.getProfileEntity(get("type")), profile);
      return redirect("info", "info.save.success");
    }
  }

  /**
   * 编辑权限<br>
   */
  public String edit() {
    // 取得各参数的值
    Profile profile = getProfile();
    boolean isAdmin = isAdmin();
    Map<String, Object> mngFields = CollectUtils.newHashMap();
    Map<String, Object> aoFields = CollectUtils.newHashMap();

    List<UserProfile> myProfiles = dataPermissionService.getUserProfiles(entityDao.get(User.class,
        getUserId()));
    Set<ProfileField> ignores = getIgnoreFields(myProfiles);
    put("ignoreFields", ignores);
    Set<ProfileField> holderIgnoreFields = CollectUtils.newHashSet();
    put("holderIgnoreFields", holderIgnoreFields);
    List<ProfileField> fields = entityDao.getAll(ProfileField.class);
    put("fields", fields);
    for (ProfileField field : fields) {
      List<?> mngFieldValues = dataPermissionService.getFieldValues(field);
      if (!isAdmin) mngFieldValues.retainAll(getMyProfileValues(myProfiles, field));
      else ignores.add(field);

      String fieldValue = "";
      Property property = profile.getProperty(field);
      if (null != property) fieldValue = property.getValue();
      if ("*".equals(fieldValue)) holderIgnoreFields.add(field);

      mngFields.put(field.getName(), mngFieldValues);
      if (null == field.getSource()) {
        aoFields.put(field.getName(), fieldValue);
      } else {
        aoFields.put(field.getName(), dataPermissionService.getPropertyValue(field, profile));
      }
    }
    put("mngFields", mngFields);
    put("aoFields", aoFields);
    put("profile", profile);
    return forward();
  }

  private Set<ProfileField> getIgnoreFields(List<UserProfile> profiles) {
    Set<ProfileField> ignores = CollectUtils.newHashSet();
    for (UserProfile profile : profiles) {
      for (Property property : profile.getProperties()) {
        String value = property.getValue();
        if ("*".equals(value)) ignores.add(property.getField());
      }
    }
    return ignores;
  }

  private List<Object> getMyProfileValues(List<UserProfile> Profiles, ProfileField field) {
    List<Object> values = CollectUtils.newArrayList();
    for (UserProfile profile : Profiles) {
      Property property = profile.getProperty(field);
      if (null != property) {
        String value = property.getValue();
        if (null != value) {
          if (property.getField().isMultiple()) {
            values.addAll((Collection<?>) dataPermissionService.getPropertyValue(field, profile));
          } else {
            values.add(dataPermissionService.getPropertyValue(field, profile));
          }
        }
      }
    }
    return values;
  }

  private Profile getProfile() {
    Long profileId = getLong("profile.id");
    Profile profile = null;
    String entityName = (String) DataPermissionHelper.getProfileEntity(get("type"));
    if (null == profileId) {
      profile = DataPermissionHelper.newProfile(get("type"), getLong("holder.id"));
    } else {
      profile = (Profile) entityDao.get(entityName, profileId);
    }
    populate(Params.sub("profile"), (Entity<?>) profile, entityName);
    return profile;
  }

  private List<? extends Profile> getProfiles() {
    Long id = getLong("holder.id");
    List<? extends Profile> profiles = null;
    if ("user".equals(get("type"))) {
      profiles = dataPermissionService.getUserProfiles(entityDao.get(User.class, id));
    } else {
      RoleProfile profile = dataPermissionService.getRoleProfile(entityDao.get(Role.class, id));
      if (null != profile) profiles = CollectUtils.newArrayList(profile);
      else profiles = Collections.emptyList();
    }
    return profiles;
  }
}
