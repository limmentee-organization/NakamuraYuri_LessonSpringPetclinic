package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.util.Assert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "owners")
public class Owner extends Person{
	
	@Column(name = "address")
	@NotBlank
	private String address;
	
	@Column(name = "city")
	@NotBlank
	private String city;
	
	@Column(name = "telephone")
	@NotBlank
	@Digits(fraction = 0, integer = 10)
	private String telephone;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	@OrderBy("name")
	private List<Pet> pets = new ArrayList<>();
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getTelephone() {
		return this.telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public List<Pet> getPets() {
		return this.pets;
	}
	
	public void addPet(Pet pet) {
		if (pet.isNew()) {
			getPets().add(pet);
		}
	}
	
	public Pet getPet(String name) {
		return getPet(name,false);
	}
	
	public Pet getPet(Integer id) {
		for(Pet pet : getPets()) {
			// petIdがNullでない場合
			if(!pet.isNew()) {
				Integer compId = pet.getId();
				// petIdが登録済みの場合
				if(compId.equals(id)) {
					return pet;
				}
			}
		}
		return null;
	}
	
	public Pet getPet(String name, boolean ignoreNew) {
		// 名前を小文字に変換
		name = name.toLowerCase();
		for(Pet pet : getPets()) {
			String compName = pet.getName();
			// petnameがNullではないかつ
			// nameがpetnameと一致している場合（大文字小文字の区別なし）
			if(compName != null && compName.equalsIgnoreCase(name)) {
				if(!ignoreNew || !pet.isNew()) {
				return pet;
				}
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return new  ToStringCreator(this).append("id", this.getId())
				.append("new", this.isNew())
				.append("lastName", this.getLastName())
				.append("firstName", this.getFirstName())
				.append("address", this.address)
				.append("city", this.city)
				.append("telephone", this.telephone)
				.toString();
	}
	
	public void addVisit(Integer petId, Visit visit) {
		Assert.notNull(petId, "Pet indentifier must not be null!");
		Assert.notNull(visit, "Visit must not be null!");
		
		Pet pet = getPet(petId);
		
		Assert.notNull(pet, "Invalid Pet indentifier");
		
		pet.addVisit(visit);
		
	}
}
