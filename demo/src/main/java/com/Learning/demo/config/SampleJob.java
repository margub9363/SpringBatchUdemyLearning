package com.Learning.demo.config;

import com.Learning.demo.listener.FirstJobListener;
import com.Learning.demo.listener.FirstStepListener;
import com.Learning.demo.processor.FirstItemProcessor;
import com.Learning.demo.reader.FirstItemReader;
import com.Learning.demo.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Tasklet firstTask;

    @Autowired
    private Tasklet secondTask;

    @Autowired
    FirstJobListener firstJobListener;

    @Autowired
    FirstStepListener firstStepListener;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;


    private Step firstStep() {
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask)
                .listener(firstStepListener)
                .build();
    }

    private Step secondStep() {
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTask)
                .build();
    }


//    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    @Bean
    public Job secondJob() {
        return jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .next(secondStep())
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("First Chunk Step")
                .<Integer,Long>chunk(3)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

}
