package yfu.practice.springbatch.quartz.job;

import java.util.UUID;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		addDefaultData(jobDataMap);
		
		try {
			String jobName = jobDataMap.getString("jobName");
			Job job = jobRegistry.getJob(jobName);
			JobParameters jobParameters = jobDataMap.entrySet().stream()
					.filter(entry -> entry.getValue() != null)
					.collect(JobParametersBuilder::new,
							(builder, entry) -> builder.addParameter(entry.getKey(), new CustomJobParameter<Object>(entry.getValue())),
							(builder, builder2) -> builder.addJobParameters(builder2.toJobParameters()))
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			log.error("Quartz Job啟動失敗", e);
		}
		
	}
	
	/**
	 * 若有缺少資料，則填入預設值
	 * @param jobDataMap
	 */
	private void addDefaultData(JobDataMap jobDataMap) {
		jobDataMap.putIfAbsent("uuid", UUID.randomUUID().toString().replace("-", ""));
	}

}