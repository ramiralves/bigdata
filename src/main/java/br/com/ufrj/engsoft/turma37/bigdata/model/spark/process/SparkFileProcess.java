package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process;

import java.io.Serializable;
import java.util.List;

import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.validator.Validator;


public interface SparkFileProcess<T extends Serializable>{	
	public List<T> process(List<T> file)throws SparkException;	
	public Validator getValidator();
}
