package cc.mrbird.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootApplication
@EnableBatchProcessing
@Slf4j
public class SpringBatchItemreaderApplication {
    @Autowired
    JobLauncher jobLauncher;

    @Resource(name="dataSourceItemReaderJob")
    Job job;


    public static void main(String[] args) {
        SpringApplication.run(SpringBatchItemreaderApplication.class, args);
    }

    @RestController
    public class MyController {
        @GetMapping("/")
        public String home() {
            return "Application is running!";
        }

        @RequestMapping("/dataSourceItemReaderJob")
        public String handle() throws Exception {
            String parameter = UUID.randomUUID().toString();
            try {
                //接口每次都重新生成一个ＵＵＩＤ，如果参数完全相同，日志会提示任务已经执行成功，不能重复执行
                JobParameters jobParameters = new JobParametersBuilder().addString("message", "Welcome To Spring Batch World!" + parameter)
                        .toJobParameters();
                jobLauncher.run(job, jobParameters);
            } catch (Exception e) {
                log.error("", e);
            }

            return parameter;
        }

    }



}
