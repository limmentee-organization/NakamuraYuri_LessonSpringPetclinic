package org.springframework.samples.petclinic.vet;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface VetRepository extends Repository<Vet, Integer> {
	
	// 読み取り専用のトランザクションとして設定する
	@Transactional(readOnly = true)
	// 指定したメソッドをキャッシュの対象として設定する
	@Cacheable("vets")
	Collection<Vet> findAll() throws DataAccessException;
	
	@Transactional(readOnly = true)
	@Cacheable("vets")
	Page<Vet> findAll(Pageable pageable) throws DataAccessException;
}
