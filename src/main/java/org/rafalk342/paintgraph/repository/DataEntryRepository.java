package org.rafalk342.paintgraph.repository;

import org.rafalk342.paintgraph.dto.DataEntry;
import org.springframework.stereotype.Repository;

@Repository
public interface DataEntryRepository extends AbstractDataEntryRepository<DataEntry> {

//	@Query("{field1: ?0, field2: ?1}")
//	public List<DataEntry> findByField(String field1, String field2);
	
}
