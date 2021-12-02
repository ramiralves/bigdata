package br.com.ufrj.engsoft.turma37.bigdata.model.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.LogVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class LogDaoTest {
	
	private static final String FILE_NAME = "classpath:data/json-log.json";
	
	@Autowired
	private FileLogDao fileLogDao;	

	@Autowired
	private LogDao logDao;
	
	//@Test
	//@Sql({"/data-mysql.sql"})
	public void update() throws DataBaseException {
		LogVO logVO = new LogVO(LogLevelEnum.ERROR,2);
		logDao.update(1,logVO);	
	}

	@Test
	//@Sql({"/data-mysql.sql"})
	public void updateAll() throws DataBaseException {		
		FileLogVO fileLog = fileLogDao.findByName(LogDaoTest.FILE_NAME);				
		logDao.update(fileLog);
	}
	
	//@Test
	//@Sql({"/data-mysql.sql"})
	public void insertAll() throws DataBaseException {		
		FileLogVO fileLog = fileLogDao.findByName(LogDaoTest.FILE_NAME);		
		logDao.insert(fileLog);
	}	
		
}
