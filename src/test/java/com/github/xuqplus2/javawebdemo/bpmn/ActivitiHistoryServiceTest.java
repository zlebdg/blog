package com.github.xuqplus2.javawebdemo.bpmn;

import com.github.xuqplus2.javawebdemo.JavaWebDemoApplicationTests;
import com.github.xuqplus2.javawebdemo.repository.TestDataRepository;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.runners.MethodSorters.NAME_ASCENDING;

@FixMethodOrder(NAME_ASCENDING) // 按照测试用例方法名升序执行
public class ActivitiHistoryServiceTest extends JavaWebDemoApplicationTests {


    @Autowired
    TestDataRepository testDataRepository;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Test
    public void test_01_history() {
        Deployment deploy = repositoryService
                .createDeployment()
                .name("启动停止流程")
                .addClasspathResource("a.bpmn")
                .deploy();
        logger.info("部署流程, deploy={}", deploy);
        Assert.assertNotNull(deploy);

        String processDefinitionId = ((DeploymentEntityImpl) deploy).getDeployedArtifacts(ProcessDefinitionEntityImpl.class).get(0).getId();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
        logger.info("流程实例, processInstance={}", processInstance);
        Assert.assertNotNull(processInstance);

        String processInstanceId = processInstance.getId();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId);
        logger.info("流程实例历史, historicProcessInstanceQuery={}", historicProcessInstanceQuery);
        Assert.assertNotNull(historicProcessInstanceQuery);

        Assert.assertNotNull(processDefinitionId);
    }
}
