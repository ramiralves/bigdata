package br.com.ufrj.engsoft.turma37.bigdata.model.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.ufrj.engsoft.turma37.bigdata.exception.BusinessException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.FileIOException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.dao.AppConfig;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.execute.log.LogSparkRddProcessImpl;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class LogServiceRddTest {

    @Autowired
    private LogService<FileLogVO> logService;
    
	@Autowired(required = false)
	private LogSparkRddProcessImpl logSparkProcessRddImpl;
    
    @Test
    public void process()throws SparkException, DataBaseException,FileIOException, BusinessException{
    	logService.process(logSparkProcessRddImpl);
    }
    
    //@Test
    public void find() throws DataBaseException {
    	List<FileLogVO> findAll = logService.findAll();
    	Assert.assertFalse(findAll.isEmpty()); 
    }
	
}
