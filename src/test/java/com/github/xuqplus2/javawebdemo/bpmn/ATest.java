package com.github.xuqplus2.javawebdemo.bpmn;

import com.github.xuqplus2.javawebdemo.JavaWebDemoApplicationTests;
import com.github.xuqplus2.javawebdemo.domain.TestData;
import com.github.xuqplus2.javawebdemo.repository.TestDataRepository;
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
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.runners.MethodSorters.NAME_ASCENDING;

@FixMethodOrder(NAME_ASCENDING) // 按照测试用例方法名升序执行
public class ATest extends JavaWebDemoApplicationTests {


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
//            .addClasspathResource("a.bpmn") // 1.name要以bpmn/bpmn20.xml结尾 2.修改idea设置=>editor=>file encodings, global/project文件编码=>utf8, no bom
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
    String processDefinitionId = "myProcess_1:9:e650891d-97f5-11e9-a947-6c2b59dad47e";
    if (isProfileTest) {
      processDefinitionId = testDataRepository.getByK(genTestK("processDefinitionId")).getV();
      logger.info("processDefinitionId={}", processDefinitionId);
    }
    ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
//    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
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
    String taskId = "86604dca-9804-11e9-ba59-6c2b59dad47e";
    if (isProfileTest) {
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
   * 查看个人任务[li si]
   */
  @Test
  public void test_04_taskLiSi() {
    List<Task> tasks = taskService
            .createTaskQuery()
            .taskAssignee("li si")
            .orderByTaskCreateTime().desc()
            .list();
    logger.info("size()={}", tasks.size());
    for (Task task : tasks) {
      logger.info("task()={}", task);
    }
  }

  /**
   * 完成任务
   */
  @Test
  @Ignore
  public void test_99_complete() {
//    taskService.complete("9c4be982-97eb-11e9-a79a-6c2b59dad47e"); // Unknown property used in expression: ${message=='important'}

    Map variables = new HashMap();
    variables.put("message", "important");
    taskService.complete("9c4be982-97eb-11e9-a79a-6c2b59dad47e", variables);
  }

  @Test
  @Ignore
  public void test_99_testDataRepository() {
    if (testDataRepository.existsById("k")) {
      TestData testData = testDataRepository.getByK("k");
      logger.info("testData={}", testData);
    } else {
      TestData testData = new TestData().setK("k").setV("v");
      testDataRepository.save(testData);
    }
  }
}
