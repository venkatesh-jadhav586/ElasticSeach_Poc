package com.poc.elastic.elastic;



import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


import javax.annotation.PostConstruct;


import static org.elasticsearch.client.Requests.searchRequest;

@Service
public class Test {
    private final ReactiveElasticsearchClient client;

    public Test(ReactiveElasticsearchClient client) {
        this.client = client;
    }

    @PostConstruct
    public Flux<SearchHit> search() {
       ReactiveElasticsearchClient.Indices in= client.indices();

      System.out.println("inside"+String.valueOf(in));
        return client.search(
                searchRequest(".kibana_1")
                        .source(SearchSourceBuilder.searchSource().query(QueryBuilders.matchQuery("title", "rahul")))
        );
    }
}
