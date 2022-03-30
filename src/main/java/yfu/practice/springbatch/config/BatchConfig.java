package yfu.practice.springbatch.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
	
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }
    
	@Bean
	public BatchConfigurer batchConfigurer(DataSource dataSource, EntityManagerFactory entityManagerFactory, BatchProperties properties) {
		return new DefaultBatchConfigurer(dataSource) {

			private PlatformTransactionManager transactionManager;

			@Override
			public PlatformTransactionManager getTransactionManager() {
				if (this.transactionManager == null) {
					JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
					jpaTransactionManager.setDataSource(dataSource);
					jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
					jpaTransactionManager.afterPropertiesSet();
					this.transactionManager = jpaTransactionManager;
				}
				return this.transactionManager;
			}

			@Override
			public JobLauncher createJobLauncher() throws Exception {
				SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
				jobLauncher.setJobRepository(getJobRepository());
				jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
				jobLauncher.afterPropertiesSet();
				return jobLauncher;
			}
			
			@Override
			public JobRepository createJobRepository() throws Exception {
				JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
				factory.setDataSource(dataSource);
				factory.setTransactionManager(getTransactionManager());
				factory.setTablePrefix(properties.getJdbc().getTablePrefix());
				factory.afterPropertiesSet();
				return factory.getObject();
			}
		};
	}
}