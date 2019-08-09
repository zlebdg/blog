package com.github.xuqplus2.blog.bpmn;

import com.github.xuqplus2.blog.BlogApplicationTests;
import com.github.xuqplus2.blog.domain.TestData;
import com.github.xuqplus2.blog.repository.TestDataRepository;
import org.activiti.engine.*;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.runners.MethodSorters.NAME_ASCENDING;

@FixMethodOrder(NAME_ASCENDING) // 按照测试用例方法名升序执行
public class ActivitiTest extends BlogApplicationTests {

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

    /**
     * 发布工作流
     *
     * @throws IOException
     */
    @Test
    public void test_01_deploy() throws IOException {
        Deployment deploy = repositoryService
                .createDeployment()
                .name("连线测试")
                .addClasspathResource("holiday.bpmn") // 有中文会报错, utf8编码的前3/前1字节有问题..
//            .addClasspathResource("a.bpmn") // 1.name要以.bpmn/.bpmn20.xml作为后缀名 2.修改idea设置=>editor=>file encodings, global/project文件编码=>utf8, no bom
//            .addInputStream("a.bpmn", this.getClass().getClassLoader().getResource("a.bpmn").openStream())
                .deploy();
        logger.info("deploy={}", deploy);
        Assert.assertNotNull(deploy);

        String processDefinitionId = ((DeploymentEntityImpl) deploy).getDeployedArtifacts(ProcessDefinitionEntityImpl.class).get(0).getId();
        TestData testData = new TestData(genTestK("processDefinitionId"), processDefinitionId);
        testDataRepository.save(testData);

        logger.info("保存测试信息, testData={}", testData);
        Assert.assertNotNull(processDefinitionId);
    }

    /**
     * 启动流程实例
     */
    @Test
    public void test_02_processInstance() {
        String processDefinitionId = "";
        if (StringUtils.isEmpty(processDefinitionId) || isTestProfile) {
            processDefinitionId = testDataRepository.getByK(genTestK("processDefinitionId")).getV();
            logger.info("processDefinitionId={}", processDefinitionId);
        }
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
        // ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
        logger.info("processInstance={}", processInstance);
        Assert.assertNotNull(processInstance);

        TaskEntity taskEntity = ((ExecutionEntityImpl) processInstance).getExecutions().get(0).getTasks().get(0);
        logger.info("taskEntity={}", taskEntity);

        TestData testData = new TestData(genTestK("taskId"), taskEntity.getId());
        testDataRepository.save(testData);
        logger.info("保存taskId, testData={}", testData);

        Assert.assertNotNull(taskEntity);
        Assert.assertNotNull(taskEntity.getId());
        Assert.assertEquals("zhang san", taskEntity.getAssignee());
    }

    /**
     * 查看个人任务[zhang san]
     */
    @Test
    public void test_03_taskZhangSan() {
        String taskId = "";
        if (StringUtils.isEmpty(taskId) || isTestProfile) {
            taskId = testDataRepository.getByK(genTestK("taskId")).getV();
            logger.info("taskId={}", taskId);
        }
        List<Task> tasks = taskService
                .createTaskQuery()
                .taskAssignee("zhang san")
                .orderByTaskCreateTime().desc()
                .list();
        logger.info("size()={}", tasks.size());
        for (Task task : tasks) {
            logger.info("task()={}", task);
        }

        Task task = tasks.get(0);
        logger.info("最新的任务是task()={}", task);
        Assert.assertNotNull(task);
        Assert.assertEquals(taskId, task.getId());
    }

    /**
     * 完成个人任务[zhang san]
     */
    @Test
    public void test_04_completeTaskZhangSan() {
        Task task = taskService
                .createTaskQuery()
                .taskAssignee("zhang san")
                .orderByTaskCreateTime().desc()
                .list()
                .get(0);
        logger.info("task()={}", task);
        Assert.assertNotNull(task);

        String taskId = task.getId();
        Map variables = new HashMap();
        try {
            taskService.complete(taskId, variables);
            Assert.assertNotNull(null);
        } catch (ActivitiException e) {
            Assert.assertNotNull(e);
        }
        variables.put("message", "not important");
        taskService.complete(taskId, variables);
        Assert.assertNotNull(1);
    }

    /**
     * 完成任务 [zhang san] => [li si]
     */
    @Test
    public void test_05_complete() {
        test_02_processInstance(); // 创建流程实例
        Task task = taskService
                .createTaskQuery()
                .taskAssignee("zhang san")
                .orderByTaskCreateTime().desc()
                .list()
                .get(0);
        logger.info("task={}", task);
        Assert.assertNotNull(task);

        String taskId = task.getId();
        final Map variables = new HashMap();
        variables.put("message", "important");
        taskService.complete(taskId, variables); // 张三完成此任务

        Task task1 = taskService.createTaskQuery()
                .taskAssignee("li si")
                .orderByTaskCreateTime().desc()
                .list()
                .get(0);
        logger.info("task1={}", task1);
        Assert.assertNotNull(task1);
        taskService.complete(task1.getId()); // 李四完成此任务
        Assert.assertNotNull(1);
    }
}
