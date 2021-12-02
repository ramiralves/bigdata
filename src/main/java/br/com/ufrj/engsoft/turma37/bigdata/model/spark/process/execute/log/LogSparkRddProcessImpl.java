package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.execute.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.SparkFileProcess;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.function.log.LogFilterRdd;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.function.log.LogMapRdd;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.function.log.LogReduceRdd;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.LogVO;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.validator.TextValidator;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.validator.Validator;
import scala.Tuple2;

@Component
public class LogSparkRddProcessImpl implements SparkFileProcess<FileLogVO>{
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogSparkRddProcessImpl.class);
	
    private Validator validator;
    
    @Autowired
    private JavaSparkContext javaSparkContext;

    
    public LogSparkRddProcessImpl() {
    	this.validator = new TextValidator();
    }	
	

	@Override
	public List<FileLogVO> process(List<FileLogVO> files) throws SparkException {				
		
		try {
			
			for (FileLogVO file : files) {																			

				//INFORMAR O ARQUIVO DE LOG
				JavaRDD<String> textRdd = javaSparkContext.textFile(file.getFullPath());
				
				//FILTRA O ARQUIVO APENAS PELO NÍVEIS DE LOG
				JavaRDD<String> filterRdd = textRdd.filter(new LogFilterRdd());
				
				//EFETUA O MAP
				JavaPairRDD<LogLevelEnum, Integer> mapRdd = filterRdd.mapToPair(new LogMapRdd());		
				
				//EFETUA O REDUCE
				JavaPairRDD<LogLevelEnum, Integer> mapReduce = mapRdd.reduceByKey(new LogReduceRdd());
				
				//EXECUÇÃO A AÇÃO
				List<Tuple2<LogLevelEnum, Integer>> collect = mapReduce.collect();
				
				List<LogVO> logs = new ArrayList<LogVO>();
				//OBTEM OS DADOS
				for (Tuple2<LogLevelEnum, Integer> tupla : collect) {					
					LogVO logVO = new LogVO();
					logVO.setLevel(tupla._1());
					logVO.setTotal(tupla._2());										
					logs.add(logVO);				
				}	
				
				file.setLogs(logs);
				
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new SparkException(e);
		}
		
		return files;
		
	}

	@Override
	public Validator getValidator() {
		return validator;
	}

}
