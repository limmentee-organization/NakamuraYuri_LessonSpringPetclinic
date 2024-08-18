package org.springframework.samples.petclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object adds a name property to <code>BaseEntity</code>. Used as
 * a base class for objects needing these properties.
 * 
 * @author Ken krebs
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
