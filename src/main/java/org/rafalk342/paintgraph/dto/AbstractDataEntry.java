package org.rafalk342.paintgraph.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AbstractDataEntry {

	@Id
	private String id;

}