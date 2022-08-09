package yfu.practice.springbatch.quartz.job;

import java.util.UUID;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;
import yfu.practice.springbatch.batch.jobparameter.CustomJobParameter;

@Slf4j
public class BatchQuartzJob extends QuartzJobBean {
	
	@Autowired
	private JobRegistry jobRegistry;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	private int count = 0;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		String jobName = jobDataMap.getString("jobName");
		
		try {
			Job job = jobRegistry.getJob(jobName);
			
			JobParametersBuilder builder = new JobParametersBuilder().addString("uuid", UUID.randomUUID().toString().replace("-", ""));
			jobDataMap.forEach((k, v) -> builder.addParameter(k, new CustomJobParameter<Object>(v)));
			
			log.info("Job {}: {}", ++count, builder.toJobParameters());
			jobLauncher.run(job, builder.toJobParameters());
		} catch (Exception e) {
			log.error("Quartz Job啟動失敗", e);
		}
		
	}

}