package org.springframework.samples.petclinic.owner;

import org.springframework.data.repository.Repository;

public interface OwnerRepository extends Repository<Owner, Integer> {
	
	/**
	 * Save an {@link Owner} to thr store, either inserting or updating it.
	 * @param owner the {@link Owner} to save
	 */
	void save(Owner owner);

}
