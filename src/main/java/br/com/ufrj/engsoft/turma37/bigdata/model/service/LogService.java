package br.com.ufrj.engsoft.turma37.bigdata.model.service;

import java.io.Serializable;
import java.util.List;

import br.com.ufrj.engsoft.turma37.bigdata.exception.BusinessException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.FileIOException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.SparkFileProcess;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;

public interface LogService <T extends Serializable>{
	public void process(SparkFileProcess<T> process)throws SparkException, DataBaseException,FileIOException,BusinessException;
	public List<FileLogVO> findAll()throws DataBaseException;
	public List<FileLogVO> findByFileName(final String fileName)throws DataBaseException;
	
}
