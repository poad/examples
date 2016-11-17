package org.bitbucket.poad1010.example.springboot.batch;

import org.bitbucket.poad1010.example.springboot.batch.entity.MessageEntity;
import org.bitbucket.poad1010.example.springboot.batch.repository.MessageRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

/**
 * Created by ken-yo on 2016/11/19.
 */
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages="org.bitbucket.poad1010.example.springboot.batch.repository")
public class BatchConfiguration {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MessageRepository repository;

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public Job job(Step step1) throws Exception {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }


    @Bean
    public ItemWriter<MessageEntity> itemWriter() {
        RepositoryItemWriter<MessageEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        return writer;
    }


    @Bean
    public ItemReader<MessageEntity> reader() {
        RepositoryItemReader<MessageEntity> reader = new RepositoryItemReader<>();
        reader.setRepository(repository);
        return reader;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName("sampleUnit");
        em.setPackagesToScan("org.bitbucket.poad1010.example.springboot.batch.repository");

        return em;
    }

}

