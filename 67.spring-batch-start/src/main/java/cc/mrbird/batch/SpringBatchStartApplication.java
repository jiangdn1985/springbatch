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
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

//https://www.helloworld.net/p/1633972629
//https://www.jb51.net/article/227255.htm

@SpringBootApplication
@EnableBatchProcessing
@Slf4j
public class SpringBatchStartApplication {
    @Autowired
    JobLauncher jobLauncher;

    @Resource(name="firstJob")
    Job  job;
    @Resource(name="flowJob")
    Job  flowJob;


    @Resource(name="multiStepJob")
    Job  multiStepJobDemo;

    @Resource(name="splitJob")
    Job  splitJob;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchStartApplication.class, args);
    }
    @RestController
    public class MyController {
        @GetMapping("/")
        public String home() {
            return "Application is running!";
        }

        @RequestMapping("/launchjob")
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
        @RequestMapping("/multiStepJob")
        public String handle1() throws Exception {
            String parameter = UUID.randomUUID().toString();
            try {
                //接口每次都重新生成一个ＵＵＩＤ，如果参数完全相同，日志会提示任务已经执行成功，不能重复执行
                JobParameters jobParameters = new JobParametersBuilder().addString("message", "Welcome To Spring Batch World!" + parameter)
                        .toJobParameters();
                jobLauncher.run(multiStepJobDemo, jobParameters);
            } catch (Exception e) {
                log.error("", e);
            }

            return parameter;
        }

        @RequestMapping("/splitJob")
        public String handle3() throws Exception {
            String parameter = UUID.randomUUID().toString();
            try {
                //接口每次都重新生成一个ＵＵＩＤ，如果参数完全相同，日志会提示任务已经执行成功，不能重复执行
                JobParameters jobParameters = new JobParametersBuilder().addString("message", "Welcome To Spring Batch World!" + parameter)
                        .toJobParameters();
                jobLauncher.run(splitJob, jobParameters);
            } catch (Exception e) {
                log.error("", e);
            }

            return parameter;
        }









        @RequestMapping("/flowJob")
        public String handle2() throws Exception {
            String parameter = UUID.randomUUID().toString();
            try {
                //接口每次都重新生成一个ＵＵＩＤ，如果参数完全相同，日志会提示任务已经执行成功，不能重复执行
                JobParameters jobParameters = new JobParametersBuilder().addString("message", "Welcome To Spring Batch World!" + parameter)
                        .toJobParameters();
                jobLauncher.run(flowJob, jobParameters);
            } catch (Exception e) {
                log.error("", e);
            }

            return parameter;
        }
    }

}














