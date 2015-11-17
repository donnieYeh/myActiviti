package com.yejf.ativiti.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Repository;

@Repository("workFlowDao")
public class WorkFlowDaoImpl implements WorkFlowDao {

	@Resource
	ProcessEngine processEngine;

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	@Override
	public List<Deployment> findDeploymentList() {
		return processEngine.getRepositoryService().createDeploymentQuery().orderByDeploymenTime().desc().list();
	}

	@Override
	public List<ProcessDefinition> findProcessDefinitionlist() {
		List<ProcessDefinition> proDef = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().desc().list();
		return proDef;
	}
	
	@Override
	public void deployProcess(File file, String deployName) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			processEngine.getRepositoryService()
				.createDeployment()
				.addZipInputStream(zipInputStream)
				.name(deployName)
				.deploy();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void getDiagram() {
	}
}
