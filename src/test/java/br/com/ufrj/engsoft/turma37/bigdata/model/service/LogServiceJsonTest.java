package br.com.ufrj.engsoft.turma37.bigdata.model.service;

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
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.execute.log.LogSparkJsonProcessImpl;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class LogServiceJsonTest {

    @Autowired
    private LogService<FileLogVO> logService;
    
	@Autowired(required = false)
	private LogSparkJsonProcessImpl logSparkProcessRddImpl;
    
    @Test
    public void process()throws SparkException, DataBaseException,FileIOException, BusinessException{
    	logService.process(logSparkProcessRddImpl);
    }
    
}
