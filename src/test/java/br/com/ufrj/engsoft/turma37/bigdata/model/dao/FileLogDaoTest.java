package br.com.ufrj.engsoft.turma37.bigdata.model.dao;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FileLogDaoTest {	

	@Autowired
	private FileLogDao fileLogDao;
		
	//@Test	
	public void findAll() throws DataBaseException, FileNotFoundException {
		List<FileLogVO> fileLogs = fileLogDao.findAll();		
		Assert.assertFalse(fileLogs.isEmpty());
	}
	

	
}
