package com.github.xuqplus2.blog.bpmn;

import com.github.xuqplus2.blog.BlogApplicationTests;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReceiveTaskTest extends BlogApplicationTests {

    @Autowired
    ProcessEngine processEngine;

    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("接收任务测试")
                .addClasspathResource("receiveTask.bpmn")
                .deploy();

        logger.info("部署id:{}, 部署名称:{}", deployment.getId(), deployment.getName());

        Assert.assertNotNull(deployment);
    }

    @Test
    public void processInstance() {
        String key = "receiveTask";
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(key);

        logger.info("流程实例id:{}, 流程实例名称:{}, 流程定义id", processInstance.getId(), processInstance.getName(), processInstance.getProcessDefinitionId());

        // 查执行对象
        Execution execution = processEngine.getRuntimeService()
                .createExecutionQuery()
                .processInstanceId(processInstance.getId())
                .activityId("_4")
                .singleResult();

        logger.info("执行对象id:{}", execution.getId());

        // 设置变量
        processEngine.getRuntimeService()
                .setVariable(execution.getId(), "当日销售额", 22);

        // 向后执行一步
        processEngine.getRuntimeService()
                .signalEventReceived(execution.getId());
    }

    @Test
    public void identity() {
    }
}
