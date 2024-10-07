package org.springframework.samples.petclinic.vet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.model.Person;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "vets")
public class Vet extends Person {
	// テーブル同士が多対多の関係になるように中間テーブルを用意してくれるアノテーション
	@ManyToMany(fetch = FetchType.EAGER)
	// 指定したカラムを中間テーブルにマッピングしてくれる
	@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<Specialty> specialties;
	
	protected Set<Specialty> getSpecialtiesInternal() {
		// specialtiesがnullの場合は初期化する
		if(this.specialties == null) {
			this.specialties = new HashSet<>();
		}
		return this.specialties;
	}

	protected void setSpecialtiesInternal(Set<Specialty> specialties) {
		this.specialties = specialties;
	}
	
	@XmlElement
	public List<Specialty> getSpecialties() {
		// getSpecialtiesInternal()をsortedSpecsリストの初期値に設定する
		List<Specialty> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
		// sortedSpecsリスト内のnameを昇順に並び替える
		PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
		// リスト内の要素を編集できないように読み取り専用にして返却する
		return Collections.unmodifiableList(sortedSpecs);
	}
	
	public int getNrOfSpecialties() {
		// getSpecialtiesInternalの件数を返却する
		return getSpecialtiesInternal().size();
	}
	
	public void addSpecialty(Specialty specialty) {
		getSpecialtiesInternal().add(specialty);
	}
}
