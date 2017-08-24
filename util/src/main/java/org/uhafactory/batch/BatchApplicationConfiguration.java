package org.uhafactory.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 */
@PropertySource("batch.properties")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@EnableBatchProcessing
@Configuration
public class BatchApplicationConfiguration {
}
