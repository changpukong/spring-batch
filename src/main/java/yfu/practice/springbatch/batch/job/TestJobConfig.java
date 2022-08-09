package yfu.practice.springbatch.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job testJob() {
		return jobBuilderFactory.get("testJob")
				.start(testStep())
				.build();
	}
	
	@Bean
	public Step testStep() {
		return stepBuilderFactory.get("testStep")
				.tasklet((contribution, chunkContext) -> {
					System.err.println("丁丁說你好");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
}