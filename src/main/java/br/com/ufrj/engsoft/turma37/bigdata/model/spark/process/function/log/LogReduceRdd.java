package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.function.log;

import org.apache.spark.api.java.function.Function2;


public class LogReduceRdd implements Function2<Integer, Integer, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Integer call(Integer v1, Integer v2) throws Exception {
		// TODO Auto-generated method stub
		return v1+v2;
	}
	


}
