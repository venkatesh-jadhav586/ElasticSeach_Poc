package com.poc.elastic.elastic;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class MymodelService {


    public  final ReactiveElasticsearchOperations reactiveElasticsearchOperations;

    public final ReactiveElasticsearchClient reactiveElasticsearchClient;


    public MymodelService(ReactiveElasticsearchOperations reactiveElasticsearchOperations, ReactiveElasticsearchClient reactiveElasticsearchClient) {
        this.reactiveElasticsearchOperations = reactiveElasticsearchOperations;
        this.reactiveElasticsearchClient = reactiveElasticsearchClient;
    }



@PostConstruct
    public void checkIndexExists(){

        GetIndexRequest request = new GetIndexRequest();
        request.indices("mymodel");

        reactiveElasticsearchClient.indices()
                .existsIndex(request)
                .doOnError(throwable ->log.error(throwable.getMessage(), throwable))
                .flatMap(indexExists -> {
                    log.info("Index {} exists: {}", "mymodel", indexExists);
                    if (!indexExists)
                        return createIndex();
                    else
                        return Mono.empty();
                })
                .block();
    }

    public Mono<Boolean> createIndex(){

        CreateIndexRequest request = new CreateIndexRequest();
        request.index("mymodel");
//        request.mapping("DEFAULT_ES_DOC_TYPE",
//                "{\n" +
//                        "  \"properties\": {\n" +
//                        "    \"timestamp\": {\n" +
//                        "      \"type\": \"date\",\n" +
//                        "      \"format\": \"epoch_millis||yyyy-MM-dd HH:mm:ss||yyyy-MM-dd\"\n" +
//                        "    }\n" +
//                        "  }\n" +
//                        "}",
//                XContentType.JSON);

        return reactiveElasticsearchClient.indices()
                .createIndex(request)
                .doOnSuccess(aVoid -> log.info("Created Index {}", "MYMODEL_ES_INDEX"))
                .doOnError(throwable -> log.error(throwable.getMessage(), throwable));
    }
}
