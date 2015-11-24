package com.yejf.ativiti.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.yejf.utils.JaxbUtil;
import com.yejf.utils.User;

public class LeaveBillTest {

	@Test
	public void leaveBill2Xml() {
		User user = new User();
		user.setAge(18);
		user.setName("ami");
		String xml = JaxbUtil.convertToXml(user);
		File file = new File("F:\\xml测试\\" + user.getClass().getSimpleName() + "-" + user.getName() + ".xml");
		try {
			FileUtils.writeStringToFile(file, xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		ProcessEngine processEngine;
		ProcessEngineConfiguration peConfiguration;
		peConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		processEngine = peConfiguration.buildProcessEngine();
		InputStream in = null;
		File f = null;
		in = processEngine.getRepositoryService().getResourceAsStream("32501", "leaveBillProcess.bpmn");
		f = new File("e:/upload/activiti/test.xml");
		try {
			FileUtils.copyInputStreamToFile(in, f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
