package com.liangjinhai.quartzdemo.Repository;

import com.liangjinhai.quartzdemo.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jin
 * @Title: JobRepository
 * @date 19-2-4下午3:33
 */
public interface JobRepository extends JpaRepository<JobEntity,Integer>, JpaSpecificationExecutor<JobEntity> {
}
