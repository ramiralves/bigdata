package br.com.ufrj.engsoft.turma37.bigdata.util.xml.sql;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SqlMapUtilTest {
	
	@Autowired
	private SqlMapUtil sqlMap;

	@Test
	public void teste() {
		String query = sqlMap.query("getUser");
		Assert.assertNotNull(query);
	}
	
}
