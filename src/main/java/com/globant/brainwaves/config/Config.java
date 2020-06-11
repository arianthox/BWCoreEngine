package com.globant.brainwaves.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories("com.globant.brainwaves.commons.persistence.elastic.repository")
public class Config {

}
