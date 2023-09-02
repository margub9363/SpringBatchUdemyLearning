package com.Learning.demo.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before job "+ jobExecution.getJobInstance().getJobName());
        System.out.println("Job Params "+ jobExecution.getJobParameters());
        System.out.println("Job Exec Context"+ jobExecution.getExecutionContext());

        jobExecution.getExecutionContext().put("JobExecutionContext_Key","JobExecutionContext_Value");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After job "+ jobExecution.getJobInstance().getJobName());
        System.out.println("Job Params "+ jobExecution.getJobParameters());
        System.out.println("Job Exec Context "+ jobExecution.getExecutionContext());
    }
}
