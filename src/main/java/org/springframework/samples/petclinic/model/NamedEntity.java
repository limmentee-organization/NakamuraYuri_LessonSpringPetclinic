package org.springframework.samples.petclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/* *
 * 単純な JavaBean ドメイン オブジェクトは、name プロパティを <code>BaseEntity</code>. に追加します。として使用されます
 * これらのプロパティを必要とするオブジェクトの基本クラス。
 * 
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {
	
	@Column(name = "name")
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
