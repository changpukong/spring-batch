package yfu.practice.springbatch.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import yfu.practice.springbatch.entity.YfuCard;
import yfu.practice.springbatch.repository.YfuCardRepo;

/**
 * 測試交易
 * @author yfu
 */
@Configuration
public class TestTransactionManagerJobConfig {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private YfuCardRepo yfuCardRepo;
    
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public Job testTransactionManagerJob() {
		return jobBuilderFactory.get("testTransactionManager")
				.start(testTransactionManagerStep())
				.listener(new JobExecutionListener() {
					
					@Override
					public void beforeJob(JobExecution jobExecution) {
						// nothing
					}

					@Override
					public void afterJob(JobExecution jobExecution) {
						YfuCard yfuCard = new YfuCard();
						yfuCard.setCardId("456");
						yfuCard.setType("B");
						yfuCardRepo.save(yfuCard);
					}
				})
				.build();
	}
	
	@Bean
	public Step testTransactionManagerStep() {
		return stepBuilderFactory.get("testTransactionManagerStep")
				.tasklet((contribution, chunkContext) -> {
					YfuCard yfuCard = new YfuCard();
					yfuCard.setCardId("123");
					yfuCard.setType("A");
					yfuCardRepo.save(yfuCard);
					
					jdbcTemplate.update("update YFU_CARD set TYPE = 'C' where CARD_ID = '123'");
					
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
}