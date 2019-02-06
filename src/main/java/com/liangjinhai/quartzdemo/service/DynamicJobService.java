package com.liangjinhai.quartzdemo.service;

import com.liangjinhai.quartzdemo.entity.JobEntity;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.util.List;

/**
 * @author Jin
 * @Title: DynamicJobService
 * @date 19-2-4下午3:12
 */
public interface DynamicJobService {

    JobEntity save(JobEntity job);

    JobEntity getById(Integer id);

    List<JobEntity> findAll();

    JobDataMap getJobDataMap(JobEntity job);

    JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map);

    Trigger getTrigger(JobEntity job);

    JobKey getJobKey(JobEntity job);
}
