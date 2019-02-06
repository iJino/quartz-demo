package com.liangjinhai.quartzdemo.controller;

import com.liangjinhai.quartzdemo.baseEnum.Status;
import com.liangjinhai.quartzdemo.common.result.BaseResult;
import com.liangjinhai.quartzdemo.entity.JobEntity;
import com.liangjinhai.quartzdemo.service.DynamicJobService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Jin
 * @Title: JobController
 * @date 19-2-4下午3:59
 */
@Controller
@RequestMapping("/job")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    @Resource
    private SchedulerFactoryBean factory;
    @Resource
    private DynamicJobService dynamicJobService;

    //初始化所有Job
    @PostConstruct
    public void initialize(){
        try {
            reStartAllJobs();
            logger.info("init success");
        } catch (SchedulerException e) {
            logger.error("INIT EXCEPTION : " + e.getMessage());
            e.printStackTrace();
        }
    }



    //根据ID重启某个Job
    @ResponseBody
    @RequestMapping("/refresh/{id}")
    public BaseResult refresh(@PathVariable("id") Integer id) throws SchedulerException {
        String result;
        JobEntity entity = dynamicJobService.getById(id);
        if (entity == null) {
            return BaseResult.failed("error: id is not exist ");
        }
        TriggerKey triggerKey = new TriggerKey(entity.getJobName(), entity.getJobGroup());
        JobKey jobKey = dynamicJobService.getJobKey(entity);
        Scheduler scheduler = factory.getScheduler();
        try {
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
            JobDataMap map = dynamicJobService.getJobDataMap(entity);
            JobDetail jobDetail = dynamicJobService.getJobDetail(jobKey, entity.getDescription(), map);
            if (entity.getStatus().equals(Status.ENABLED)) {
                scheduler.scheduleJob(jobDetail, dynamicJobService.getTrigger(entity));
                result = "Refresh Job : " + entity.getJobName() + "\t jarPath: " + entity.getJarPath() + " success !";
            } else {
                result = "Refresh Job : " + entity.getJobName() + "\t jarPath: " + entity.getJarPath() + " failed ! , " +
                        "Because the Job status is " + entity.getStatus();
            }
        } catch (SchedulerException e) {
            result = "Error while Refresh " + e.getMessage();
        }
        return new BaseResult(result);
    }

    //重启数据库中所有的Job
    @RequestMapping("/refresh/all")
    public String refreshAll() {
        String result;
        try {
            reStartAllJobs();
            result = "SUCCESS";
        } catch (SchedulerException e) {
            result = "EXCEPTION : " + e.getMessage();
        }
        return "refresh all jobs : " + result;
    }

    /**
     * 重新启动所有的job
     */
    private void reStartAllJobs() throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (JobKey jobKey : set) {
            scheduler.deleteJob(jobKey);
        }
        for (JobEntity job : dynamicJobService.findAll()) {
            logger.info("Job register name : {} , group : {} , cron : {}", job.getJobName(), job.getJobGroup(), job.getCron());
            JobDataMap map = dynamicJobService.getJobDataMap(job);
            JobKey jobKey = dynamicJobService.getJobKey(job);
            JobDetail jobDetail = dynamicJobService.getJobDetail(jobKey, job.getDescription(), map);
            if (job.getStatus().equals(Status.ENABLED)) {
                scheduler.scheduleJob(jobDetail, dynamicJobService.getTrigger(job));
            }else {
                logger.info("Job jump name : {} , Because {} status is {}", job.getJobName(), job.getJobGroup(), job.getStatus());
            }
        }
    }
}
