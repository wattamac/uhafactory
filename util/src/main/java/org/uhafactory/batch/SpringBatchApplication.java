package org.uhafactory.batch;

import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import lombok.Setter;

/**
 */
@SuppressWarnings("rawtypes")
public class SpringBatchApplication {
    @Setter
    private BatchApplicationParam param;

    private ConfigurableApplicationContext ctx;

    public SpringBatchApplication(BatchApplicationParam param) {
        this.param = param;
    }

    public JobExecution run() throws Exception {
        SpringApplication app = new SpringApplication((Object[])param.getContextClasses());
        app.setWebEnvironment(false);
        ctx = app.run(param.getArgs());

        JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
        Job job = ctx.getBean(param.getJobName(), Job.class);
        return jobLauncher.run(job, convertJobParameters());
    }

    public void close() {
        if(ctx != null) {
            ctx.close();
        }
    }

    JobParameters convertJobParameters() {
        String[] optionValues = param.getJobParameters();
        if (optionValues == null || optionValues.length == 0) {
            return new JobParameters();
        }

        JobParametersConverter jobParametersConverter = new DefaultJobParametersConverter();
        Properties properties = StringUtils.splitArrayElementsIntoProperties(optionValues, "=");
        return jobParametersConverter.getJobParameters(properties);
    }
}
