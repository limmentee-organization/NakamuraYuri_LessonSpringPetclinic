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

// @MappedSuperclass：上位のエンティティクラスに対し使用できるアノテーション
// 使用すると項目を共通化できるので、複数のテーブルに共通のカラムが存在している場合に使える
@MappedSuperclass
// Serializable：対象のオブジェクトをByte配列にしてデータベースやフォルダ内に出力する
public class BaseEntity implements Serializable {
	
	// エンティティの主キーとして設定
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
