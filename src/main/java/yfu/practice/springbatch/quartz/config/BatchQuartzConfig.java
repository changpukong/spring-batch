package yfu.practice.springbatch.quartz.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import yfu.practice.springbatch.quartz.job.BatchQuartzJob;

//@Configuration
public class BatchQuartzConfig {
	
	private static final String TEST_JOB = "testJob";
	
	private static final String TEST_JOB_CRON = "*/5 * * * * ?";

	private static final String TEST_JOB_2 = "testJob2";

	private static final String TEST_JOB_CRON_2 = "*/15 * * * * ?";
	
	@Bean
	public JobDetail testJobDetail() {
		return JobBuilder.newJob(BatchQuartzJob.class)
				.withIdentity(TEST_JOB)
				.usingJobData("jobName", TEST_JOB)
				.storeDurably()
				.build();
	}

	@Bean
	public Trigger testJobTrigger() {
		return TriggerBuilder.newTrigger()
				.withIdentity(TEST_JOB)
				.forJob(testJobDetail())
				.withSchedule(CronScheduleBuilder.cronSchedule(TEST_JOB_CRON))
				.build();
	}
	
	@Bean
	public Trigger testJobTrigger2() {
		return TriggerBuilder.newTrigger()
				.withIdentity(TEST_JOB_2)
				.forJob(testJobDetail())
				.withSchedule(CronScheduleBuilder.cronSchedule(TEST_JOB_CRON_2))
				.build();
	}
	
}