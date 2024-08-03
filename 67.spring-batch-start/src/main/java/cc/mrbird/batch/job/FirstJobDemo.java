package cc.mrbird.batch.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author MrBird
 */
@Component
public class FirstJobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    InterceptingJobExecution interceptingJobExecution;
    @Autowired
    PeopleService  peopleService;
    @Autowired
    private MethodInvokingTaskletAdapter methodInvokingTaskletAdapter; // 使用Tasklet的Adapter 进行资源处里
    @Bean(name="firstJob")
    public Job firstJob() {
        return jobBuilderFactory.get("firstJob")
                .start(step()).listener(interceptingJobExecution)
                .next(step2()).listener(interceptingJobExecution)
                .next(step3()).listener(interceptingJobExecution)
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {

                    //tasklet 为任务开始时候,前置资源执行,可以传递参数,调用外部资源,每个step中的参数都可以往下一次层传递
                    String message = (String) chunkContext.getStepContext().getJobParameters().get("message");
                    ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

                    jobContext.put("message1", message);
                    //打印传入的参数
                    System.out.println(message);

                    System.out.println("执行步骤....");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public MethodInvokingTaskletAdapter methodInvokingTaskletAdapter() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(peopleService);
        adapter.setTargetMethod("upperCase");
        adapter.setArguments(new Object[]{new People("lee","10","北京","1233")});
        return adapter;
    }

    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    //接受上一层及的传递的参数

                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
                        ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
                        String sharedData = jobContext.getString("message1");
                        System.out.println("Shared Data: " + sharedData);
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }


    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(methodInvokingTaskletAdapter)
                .build();
    }


}
