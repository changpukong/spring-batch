package yfu.practice.springbatch.batch.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import yfu.practice.springbatch.config.BatchConfig2;
import yfu.practice.springbatch.config.SpringBatchConfig;

@SpringBootTest
@SpringBatchTest
@ContextConfiguration(classes = { TestJobConfig.class, SpringBatchConfig.class, DataSourceAutoConfiguration.class, BatchConfig2.class })
@EnableBatchProcessing
class TestJobConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

//	@Autowired
//	private JobRegistry jobRegistry;
//	
//	@Autowired
//	private JobLauncher jobLauncher;

//	private JobLauncherTestUtils getJobLauncherTestUtils() throws NoSuchJobException {
//		JobLauncherTestUtils utils = new JobLauncherTestUtils();
//		utils.setJob(jobRegistry.getJob("testJob"));
//		utils.setJobLauncher(jobLauncher);
//		return utils;
//	}

	@After
	public void cleanUp() {
//		jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	void testTestJob() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
//		JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
		JobInstance jobInstance = jobExecution.getJobInstance();
		ExitStatus exitStatus = jobExecution.getExitStatus();

		assertEquals(jobInstance.getJobName(), "testJob");
	}

	private JobParameters defaultJobParameters() {
		return new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
	}
}
