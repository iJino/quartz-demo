package com.liangjinhai.quartzdemo.entity;

import com.liangjinhai.quartzdemo.baseEnum.Status;
import com.liangjinhai.quartzdemo.common.util.JsonUtil;

import javax.persistence.*;
import javax.print.attribute.standard.JobName;
import java.io.Serializable;

@Entity
@Table(name = "job_entity")
public class JobEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(50) COMMENT '任务名称'")
    private String jobName;

    @Column(columnDefinition="varchar(50) COMMENT '任务所属组'")
    private String jobGroup;

    @Column(columnDefinition="varchar(50) COMMENT '执行规则'")
    private String cron;

    @Column(columnDefinition="varchar(255) COMMENT '执行参数'")
    private String parameter;

    @Column(columnDefinition="varchar(255) COMMENT '描述'")
    private String description;

    @Column(columnDefinition="varchar(255) COMMENT 'vm参数'")
    private String vmParam;

    @Column(columnDefinition="varchar(255) COMMENT '定时执行的一些jar包'")
    private String jarPath;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="varchar(10) COMMENT '状态'")
    private Status status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVmParam() {
        return vmParam;
    }

    public void setVmParam(String vmParam) {
        this.vmParam = vmParam;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public JobEntity() {
    }

    @Override
    public String toString() {
        return JsonUtil.object2Json(this);
    }
    
    //新增Builder模式,可选,选择设置任意属性初始化对象
    public JobEntity(Builder builder) {
        id = builder.id;
        jobName = builder.jobName;
        jobGroup = builder.jobGroup;
        cron = builder.cron;
        parameter = builder.parameter;
        description = builder.description;
        vmParam = builder.vmParam;
        jarPath = builder.jarPath;
        status = builder.status;
    }

    public static class Builder {
        private Integer id;
        private String jobName = "";          //job名称
        private String jobGroup = "";         //job组名
        private String cron = "";          //执行的cron
        private String parameter = "";     //job的参数
        private String description = "";   //job描述信息
        private String vmParam = "";       //vm参数
        private String jarPath = "";       //job的jar路径
        private Status status = Status.DISABLED;        //job的执行状态,只有该值为OPEN才会执行该Job
        public Builder withId(Integer i) {
            id = i;
            return this;
        }
        public Builder withName(String n) {
            jobName = n;
            return this;
        }
        public Builder withGroup(String g) {
            jobGroup = g;
            return this;
        }
        public Builder withCron(String c) {
            cron = c;
            return this;
        }
        public Builder withParameter(String p) {
            parameter = p;
            return this;
        }
        public Builder withDescription(String d) {
            description = d;
            return this;
        }
        public Builder withVMParameter(String vm) {
            vmParam = vm;
            return this;
        }
        public Builder withJarPath(String jar) {
            jarPath = jar;
            return this;
        }
        public Builder withStatus(Status s) {
            status = s;
            return this;
        }
        public JobEntity newJobEntity() {
            return new JobEntity(this);
        }
    }
}
