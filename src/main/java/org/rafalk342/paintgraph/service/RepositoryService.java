package org.rafalk342.paintgraph.service;

import org.rafalk342.paintgraph.repository.DataEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

	private static final Logger log = LoggerFactory.getLogger(RepositoryService.class);
	private final DataEntryRepository dataEntryRepository;

	public RepositoryService(DataEntryRepository dataEntryRepository) {
		this.dataEntryRepository = dataEntryRepository ;
	}

}
