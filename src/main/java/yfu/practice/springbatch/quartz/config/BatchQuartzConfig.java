package yfu.practice.springbatch.quartz.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import yfu.practice.springbatch.quartz.job.BatchQuartzJob;

@Configuration
public class BatchQuartzConfig {

	private static final String TEST = "test";

	private static final String TEST_CRON = "0 0 * * * ?";

	@Bean
	public JobDetail testJobDetail() {
		return JobBuilder.newJob(BatchQuartzJob.class)
				.withIdentity(TEST)
				.usingJobData("jobName", TEST)
				.storeDurably()
				.build();
	}

	@Bean
	public Trigger testJobTrigger() {
		return TriggerBuilder.newTrigger()
				.withIdentity(TEST)
				.forJob(testJobDetail())
				.withSchedule(CronScheduleBuilder.cronSchedule(TEST_CRON))
				.build();
	}

}