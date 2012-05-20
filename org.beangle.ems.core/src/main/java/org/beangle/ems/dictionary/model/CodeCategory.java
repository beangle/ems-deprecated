/* Copyright c 2005-2012.\
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.collection.CollectUtils;
import org.beangle.dao.pojo.HierarchyEntity;
import org.beangle.dao.pojo.IntegerIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 代码分类
 * 
 * @author chaostone
 * @version $Id: CodeCategory.java Jun 28, 2011 8:32:18 PM chaostone $
 */
@Entity(name = "org.beangle.ems.dictionary.model.CodeCategory")
@Cacheable
@Cache(region = "ems.dictionary", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CodeCategory extends IntegerIdObject implements HierarchyEntity<CodeCategory, Integer> {

	private static final long serialVersionUID = -8865890399079481866L;

	/** 类别名称 */
	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String name;

	/** 上级类别 */
	@ManyToOne
	private CodeCategory parent;

	/** 下级类别列表 */
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<CodeCategory> children = CollectUtils.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CodeCategory getParent() {
		return parent;
	}

	public void setParent(CodeCategory parent) {
		this.parent = parent;
	}

	public List<CodeCategory> getChildren() {
		return children;
	}

	public void setChildren(List<CodeCategory> children) {
		this.children = children;
	}

}
