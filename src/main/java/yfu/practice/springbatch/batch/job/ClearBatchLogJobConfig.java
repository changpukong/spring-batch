package yfu.practice.springbatch.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import yfu.practice.springbatch.tasklet.ClearBatchLogTasklet;

@Configuration
public class ClearBatchLogJobConfig {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job clearBatchLogJob() {
    	return jobBuilderFactory.get("clearBatchLog")
    			.start(clearBatchLogStep(null))
    			.build();
    }
    
    @Bean
    public Step clearBatchLogStep(ClearBatchLogTasklet tasklet) {
    	return stepBuilderFactory.get("clearBatchLogStep")
    			.tasklet(tasklet)
    			.build();
    }

}