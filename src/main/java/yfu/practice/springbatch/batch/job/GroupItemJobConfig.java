package yfu.practice.springbatch.batch.job;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;
import org.springframework.batch.item.support.builder.SingleItemPeekableItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import yfu.practice.springbatch.completionpolicy.GroupItemCompletionPolicy;
import yfu.practice.springbatch.dto.YfuCardDto;

/**
 * 以群分批 
 * @author yfu
 */
@Configuration
public class GroupItemJobConfig {
    
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private DataSource dataSource;

    @Bean
    public Job testGroupItem() {
        return jobBuilderFactory.get("groupItem")
                .start(testGroupItemStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }
    
    @Bean
    public Step testGroupItemStep() {        
        GroupItemCompletionPolicy completionPolicy = new GroupItemCompletionPolicy();
        completionPolicy.setReader(groupItemReader());

        return stepBuilderFactory.get("groupItemStep")
                .<YfuCardDto, YfuCardDto>chunk(completionPolicy)
                .reader(groupItemReader())
                .writer(new ItemWriter<YfuCardDto>() {
                    
                    @Override
                    public void write(List<? extends YfuCardDto> items) throws Exception {                        
                        items.stream().forEach(e -> System.out.println("*****" + e));
                        System.out.println("======分隔線======");
                    }
                    
                })
                /*
                 * 因有使用到@AfterRead，所以也需指定為listener
                 * 若由容器管理，似乎不用
                 */
                .listener(completionPolicy)
                .build();
    }
    
    @Bean
    public SingleItemPeekableItemReader<YfuCardDto> groupItemReader() {
        JdbcCursorItemReader<YfuCardDto> jdbcCursorReader =  new JdbcCursorItemReaderBuilder<YfuCardDto>()
                .name("groupItemReader")
                .dataSource(dataSource)
                .sql("select * from STUDENT.YFU_CARD order by TYPE desc")
                .beanRowMapper(YfuCardDto.class)
                .build();
        
        return new SingleItemPeekableItemReaderBuilder<YfuCardDto>()
                .delegate(jdbcCursorReader)
                .build();
    }
    
}