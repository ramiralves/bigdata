package br.com.ufrj.engsoft.turma37.bigdata.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.ufrj.engsoft.turma37.bigdata.exception.BusinessException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.FileIOException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.service.LogService;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.execute.log.LogSparkJsonProcessImpl;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.execute.log.LogSparkRddProcessImpl;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.io.FileUtil;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.validator.JsonValidator;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.validator.TextValidator;

@Component
public class ScheduledTasks {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	@Value("${spring.spark.folder.base}") 
	private String folder;	
	
    @Autowired
    private FileUtil fileUtil;	
    
    @Autowired
    private LogService<FileLogVO> logService;
    
	@Autowired(required = false)
	private LogSparkRddProcessImpl logSparkProcessRddImpl;

	@Autowired(required = false)
	private LogSparkJsonProcessImpl logSparkJsonProcessImpl;


	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() throws SparkException, FileIOException {
		
		if(fileUtil.exist(folder, new TextValidator())) {
			LOGGER.info("PROCESSA ARQUIVOS DE LOG - TXT - [INICIO] {}", DATE_FORMAT.format(new Date()));
			try {
				logService.process(logSparkProcessRddImpl);
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage(),e);				
			} catch (FileIOException e) {
				LOGGER.error(e.getMessage(),e);
			} catch (SparkException e) {
				LOGGER.error(e.getMessage(),e);
			} catch (DataBaseException e) {
				LOGGER.error(e.getMessage(),e);
			}
			LOGGER.info("PROCESSA ARQUIVOS DE LOG - TXT - [FIM] {}.", DATE_FORMAT.format(new Date()));		
		}
		
		if(fileUtil.exist(folder, new JsonValidator())) {
			LOGGER.info("PROCESSA ARQUIVOS DE LOG - JSON - [INICIO] {}", DATE_FORMAT.format(new Date()));
			try {
				logService.process(logSparkJsonProcessImpl);
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage(),e);				
			} catch (FileIOException e) {
				LOGGER.error(e.getMessage(),e);
			} catch (SparkException e) {
				LOGGER.error(e.getMessage(),e);
			} catch (DataBaseException e) {
				LOGGER.error(e.getMessage(),e);
			}
			LOGGER.info("PROCESSA ARQUIVOS DE LOG - JSON - [FIM] {}.", DATE_FORMAT.format(new Date()));				
		}

	}
}