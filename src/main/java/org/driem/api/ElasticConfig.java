package org.driem.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("elastic")
@ComponentScan(basePackages = "org.driem.api.elastic")
public class ElasticConfig {
}
