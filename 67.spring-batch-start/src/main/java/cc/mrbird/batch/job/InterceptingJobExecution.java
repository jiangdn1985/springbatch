package cc.mrbird.batch.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class InterceptingJobExecution implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Intercepting Job Execution - Before Job!");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Intercepting Job Execution - after Job!");
    }




}
