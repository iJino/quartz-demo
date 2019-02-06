package com.liangjinhai.quartzdemo.service.Impl;

import com.liangjinhai.quartzdemo.Repository.JobRepository;
import com.liangjinhai.quartzdemo.config.DynamicJob;
import com.liangjinhai.quartzdemo.entity.JobEntity;
import com.liangjinhai.quartzdemo.service.DynamicJobService;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin
 * @Title: DynamicJobServiceImpl
 * @date 19-2-4下午3:16
 */
@Service
public class DynamicJobServiceImpl implements DynamicJobService {

    @Resource
    private JobRepository jobRepository;

    @Override
    public JobEntity save(JobEntity job) {
        return jobRepository.save(job);
    }

    @Override
    public JobEntity getById(Integer id) {
        return jobRepository.findById(id).get();
    }

    @Override
    public List<JobEntity> findAll() {
        List<JobEntity> list = new ArrayList<>();
        jobRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public JobDataMap getJobDataMap(JobEntity job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getJobName());
        map.put("group", job.getJobGroup());
        map.put("cronExpression", job.getCron());
        map.put("parameter", job.getParameter());
        map.put("JobDescription", job.getDescription());
        map.put("vmParam", job.getVmParam());
        map.put("jarPath", job.getJarPath());
        map.put("status", job.getStatus());
        return map;
    }

    @Override
    public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map) {
        return JobBuilder.newJob(DynamicJob.class)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    @Override
    public Trigger getTrigger(JobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(),job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }

    @Override
    public JobKey getJobKey(JobEntity job) {
        return JobKey.jobKey(job.getJobName(),job.getJobGroup());
    }
}
