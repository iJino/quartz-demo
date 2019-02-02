package com.liangjinhai.quartzdemo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "job_entity")
public class JobEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition="varchar(20) COMMENT '名字'")
    private String jname;

    @Column(columnDefinition="varchar(50) COMMENT '所属组'")
    private String group;

    @Column(columnDefinition="varchar(50) COMMENT '执行的规则'")
    private String cron;

    @Column(columnDefinition="varchar(255) COMMENT '执行参数'")
    private String parameter;

    @Column(columnDefinition="varchar(255) COMMENT '描述'")
    private String description;

    private String vmParam;
    /**
     * job的jar路径，定时执行一些可执行的jar包
     */
    private String jarPath;
}
