package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.execute.log;

import static org.apache.spark.sql.functions.count;
import static org.apache.spark.sql.functions.lit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.SparkFileProcess;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.LogVO;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.validator.JsonValidator;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.validator.Validator;

@Component
public class LogSparkJsonProcessImpl implements SparkFileProcess<FileLogVO>{
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogSparkJsonProcessImpl.class);
	
    private Validator validator;
    
    @Autowired
    private JavaSparkContext javaSparkContext;
    
    @Autowired
    private SparkSession sparkSession;    
       
    public LogSparkJsonProcessImpl() {
    	this.validator = new JsonValidator();
    }	
	
    
    /**
     * 
     * @see <a href="https://spark.apache.org/docs/latest/sql-data-sources-json.html">Spark Data Source Option</a>
     * 
     */
	@Override
	public List<FileLogVO> process(List<FileLogVO> files) throws SparkException {				
		
		try {
			
			for (FileLogVO file : files) {	

				JavaRDD<String> rdd = javaSparkContext
											.wholeTextFiles(file.getFullPath())
												.values();

				Dataset<Row> dataSet = sparkSession
										.read()										
										//Parse one record, which may span multiple lines, per file.
										.option("multiLine", 	true)
										//Allows a mode for dealing with corrupt records during parsing.
										.option("mode", 		"PERMISSIVE")
										.json(rdd);
				
				dataSet.printSchema();
				
				Dataset<Row> dataSetFilter = dataSet
											    .select("level")
											    	.filter(this.buildLevelFilter())
											    		.groupBy("level")
											    			.agg(
											    				count(lit(1)).alias("TOTAL")
											    			);
																
				Iterator<Row> itertator = dataSetFilter.toLocalIterator();
				
				List<LogVO> logs = new ArrayList<LogVO>();
				
				while (itertator.hasNext()) {
					
					LogVO logVO = new LogVO();
					
					Row row = itertator.next();
					final String result = row.getAs("level");					
					final Long 	 total = row.getAs("TOTAL");
					
					LogLevelEnum logEnum = LogLevelEnum.getEnumByLevel(result);					
					logVO.setLevel(logEnum);
					logVO.setTotal(Math.toIntExact(total));										
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
	
	private String buildLevelFilter() {
		StringBuilder sb = new StringBuilder();
		Iterator<LogLevelEnum> iterator = Arrays.stream(LogLevelEnum.values()).iterator();
		
		while(iterator.hasNext()) {
			LogLevelEnum log = iterator.next();
			sb.append("level = '");
			sb.append(log.getLevel());
			sb.append("' ");
			if(iterator.hasNext()) {
				sb.append(" or ");
			}						
		}
		return sb.toString();
	}

	@Override
	public Validator getValidator() {
		return validator;
	}

}
