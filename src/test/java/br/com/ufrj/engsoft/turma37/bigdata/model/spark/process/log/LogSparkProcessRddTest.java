package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.log;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.ufrj.engsoft.turma37.bigdata.exception.FileIOException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.SparkFileProcessImpl;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.execute.log.LogSparkRddProcessImpl;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class LogSparkProcessRddTest {

	@Autowired
	private SparkFileProcessImpl<FileLogVO> sparkFileProcessImpl;
	
	@Autowired
	private LogSparkRddProcessImpl logSparkProcessRddImpl;	
	
	@Test
	public void processCSV() throws SparkException, FileIOException {		
		List<FileLogVO> process = sparkFileProcessImpl.process(logSparkProcessRddImpl);
		System.out.println(process);		
	}	
	
}
