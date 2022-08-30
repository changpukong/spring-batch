package yfu.practice.springbatch.tasklet;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClearBatchLogTasklet implements Tasklet {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String DELETE_FROM_BATCH_STEP_EXECUTION_CONTEXT =
			"delete from LON_STEP_EXECUTION_CONTEXT CONTEXT"
			+ " where exists ("
			+ " select * from LON_STEP_EXECUTION STEP"
			+ " join LON_JOB_EXECUTION JOB"
			+ " on STEP.JOB_EXECUTION_ID = JOB.JOB_EXECUTION_ID and JOB.CREATE_TIME < :expiredDate"
			+ " where STEP.STEP_EXECUTION_ID = CONTEXT.STEP_EXECUTION_ID)";
	
	private static final String DELETE_FROM_BATCH_STEP_EXECUTION =
			"delete from LON_STEP_EXECUTION STEP"
			+ " where exists ("
			+ " select * from LON_JOB_EXECUTION JOB"
			+ " where STEP.JOB_EXECUTION_ID = JOB.JOB_EXECUTION_ID"
			+ " and JOB.CREATE_TIME < :expiredDate)";
	
	private static final String DELETE_FROM_BATCH_JOB_EXECUTION_PARAMS =
			"delete from LON_JOB_EXECUTION_PARAMS PARAMS"
			+ " where exists ("
			+ " select * from LON_JOB_EXECUTION JOB"
			+ " where PARAMS.JOB_EXECUTION_ID = JOB.JOB_EXECUTION_ID"
			+ " and JOB.CREATE_TIME < :expiredDate)";
	
	private static final String DELETE_FROM_BATCH_JOB_EXECUTION_CONTEXT =
			"delete from LON_JOB_EXECUTION_CONTEXT CONTEXT"
			+ " where exists ("
			+ " select * from LON_JOB_EXECUTION JOB"
			+ " where CONTEXT.JOB_EXECUTION_ID = JOB.JOB_EXECUTION_ID"
			+ " and JOB.CREATE_TIME < :expiredDate)";
	
	private static final String DELETE_FROM_BATCH_JOB_EXECUTION =
			"delete from LON_JOB_EXECUTION JOB"
			+ " where JOB.CREATE_TIME < :expiredDate";
	
	private static final String DELETE_FROM_BATCH_JOB_INSTANCE = 
			"delete from LON_JOB_INSTANCE INSTANCE"
			+ " where not exists ("
			+ " select * from LON_JOB_EXECUTION JOB"
			+ " where INSTANCE.JOB_INSTANCE_ID = JOB.JOB_INSTANCE_ID)";
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String expiredDays = (String) chunkContext.getStepContext().getJobParameters().get("expiredDays");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("expiredDate", Date.valueOf(LocalDate.now().minusDays(Long.valueOf(expiredDays))));
		
		int count = jdbcTemplate.update(DELETE_FROM_BATCH_STEP_EXECUTION_CONTEXT, paramMap);
		log.info("{}筆資料已從BATCH_STEP_EXECUTION_CONTEXT刪除", count);
		
		count = jdbcTemplate.update(DELETE_FROM_BATCH_STEP_EXECUTION, paramMap);
		log.info("{}筆資料已從BATCH_STEP_EXECUTION刪除", count);
		
		count = jdbcTemplate.update(DELETE_FROM_BATCH_JOB_EXECUTION_PARAMS, paramMap);
		log.info("{}筆資料已從BATCH_JOB_EXECUTION_PARAMS刪除", count);
		
		count = jdbcTemplate.update(DELETE_FROM_BATCH_JOB_EXECUTION_CONTEXT, paramMap);
		log.info("{}筆資料已從BATCH_JOB_EXECUTION_CONTEXT刪除", count);
		
		count = jdbcTemplate.update(DELETE_FROM_BATCH_JOB_EXECUTION, paramMap);
		log.info("{}筆資料已從BATCH_JOB_EXECUTION刪除", count);
		
		count = jdbcTemplate.update(DELETE_FROM_BATCH_JOB_INSTANCE, paramMap);
		log.info("{}筆資料已從BATCH_JOB_INSTANCE刪除", count);
		
		return RepeatStatus.FINISHED;
	}

}