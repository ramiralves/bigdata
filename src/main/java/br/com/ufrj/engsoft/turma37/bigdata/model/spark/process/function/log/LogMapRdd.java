package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.function.log;

import org.apache.spark.api.java.function.PairFunction;

import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;
import scala.Tuple2;

public class LogMapRdd implements PairFunction<String, LogLevelEnum, Integer>{
	
	private static final long serialVersionUID = 1L;

	@Override
	public Tuple2<LogLevelEnum, Integer> call(String textoLinha) throws Exception {
		LogLevelEnum levelLog = LogLevelEnum.getEnum(textoLinha);
		Tuple2<LogLevelEnum, Integer> tuple = new Tuple2<LogLevelEnum, Integer>(levelLog, 1);
		return tuple;
	}
	

}
