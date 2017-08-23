package org.uhafactory.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by cj848 on 2017. 5. 11..
 */
@PropertySource("batch.properties")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@EnableBatchProcessing
@Configuration
public class BatchApplicationConfiguration {
}
