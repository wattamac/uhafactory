package org.uhafactory.batch;

import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.launch.support.JvmSystemExiter;
import org.springframework.batch.core.launch.support.SimpleJvmExitCodeMapper;
import org.springframework.batch.core.launch.support.SystemExiter;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;

/**
 */
@Slf4j
@Import({BatchApplicationConfiguration.class})
public class BatchApplication {
	private static SystemExiter EXITER = new JvmSystemExiter();
	private static ExitCodeMapper exitCodeMapper = new SimpleJvmExitCodeMapper();

	public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, ParseException, ClassNotFoundException {
		long startTime = System.currentTimeMillis();

		log.info("start batch application args = {}", ArrayUtils.toString(args));
		BatchApplicationParam param = BatchApplicationParam.parse(args);
		param.setDefaultContextClass(BatchApplication.class);

		SpringBatchApplication batchApplication = new SpringBatchApplication(param);
		int status = 0;
		try {
			JobExecution jobExecution = batchApplication.run();
			status = exitCodeMapper.intValue(jobExecution.getExitStatus().getExitCode());
		} catch (Exception e) {
			status = exitCodeMapper.intValue(ExitStatus.FAILED.getExitCode());
			log.error(e.getMessage(), e);
		} finally {
			log.info("end batch application args = {}, process seconds = {}", ArrayUtils.toString(args), System.currentTimeMillis() - startTime);
			batchApplication.close();
			EXITER.exit(status);
		}
	}

}
