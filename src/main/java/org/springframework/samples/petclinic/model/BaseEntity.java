package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * id プロパティを持つ単純な JavaBean ドメイン オブジェクト。オブジェクトの基本クラスとして使用されます
 * このプロパティが必要です。
 * 
 * @author Ken Krebs
 * @author Juergen Hoeller
 */

@MappedSuperclass
public class BaseEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean isNew() {
		return this.id == null;
	}

}
