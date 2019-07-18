package org.rafalk342.paintgraph.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	private static final  String DB_NAME = "db-name";
	private static final String HOST_NAME = "127.0.0.1";
	private static final Integer PORT = 27017;

	@Override
	protected String getDatabaseName() {
		return DB_NAME;
	}


	@Override
	public MongoClient mongoClient() {
		try {
			return new MongoClient(HOST_NAME, PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
}