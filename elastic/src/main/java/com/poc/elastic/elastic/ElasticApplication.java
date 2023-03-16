package com.poc.elastic.elastic;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

import static org.elasticsearch.client.Requests.searchRequest;

@SpringBootApplication
public class ElasticApplication {
@Autowired
MymodelService service;

	public static void main(String[] args) {

		SpringApplication.run(ElasticApplication.class, args);



	}



}
