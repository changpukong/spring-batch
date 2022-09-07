package yfu.practice.springbatch.controller;

import java.util.UUID;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BatchController {

	@Autowired
	private JobOperator jobOperator;
	
	@Autowired
	private Scheduler scheduler;

	@GetMapping(value = "/groupItem")
	public void groupItem() throws Exception {
		String jobName = "groupItem";
		try {
			jobOperator.start(jobName, "today=20211109,tomorrow=20211110");
		} catch (JobInstanceAlreadyExistsException e) {
			log.debug("Job實例已存在，產生新的實例繼續執行", e);
			jobOperator.startNextInstance(jobName);
		}
	}

	@GetMapping(value = "/testTransactionManager")
	public void testTransactionManager() throws Exception {
		jobLaunch("testTransactionManager");
	}

	private void jobLaunch(String jobName) throws Exception {	
		JobKey jobKey = new JobKey(jobName);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		jobDataMap.put("uuid", UUID.randomUUID().toString().replace("-", ""));
		scheduler.triggerJob(jobKey, jobDataMap);
	}
	
}