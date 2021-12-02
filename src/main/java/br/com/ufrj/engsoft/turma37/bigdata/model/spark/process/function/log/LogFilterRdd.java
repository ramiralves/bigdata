package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.function.log;

import org.apache.spark.api.java.function.Function;

import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;

public class LogFilterRdd implements Function<String, Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean call(String v1) throws Exception {		
		return LogLevelEnum.exist(v1);
	}
	
	
}
