package br.com.ufrj.engsoft.turma37.bigdata.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ufrj.engsoft.turma37.bigdata.exception.BusinessException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.FileIOException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.dao.FileLogDao;
import br.com.ufrj.engsoft.turma37.bigdata.model.dao.LogDao;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.SparkFileProcess;
import br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.SparkFileProcessImpl;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;

@Service
public class LogServiceImpl implements LogService<FileLogVO>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);
	
	@Autowired(required = false)
	private SparkFileProcessImpl<FileLogVO> sparkFileProcessImpl;
	
	@Autowired(required = false)	
	private LogDao logDao;
	
	@Autowired
	private FileLogDao fileLogDao;	

	@Override
	public List<FileLogVO> findAll()throws DataBaseException  {
		return fileLogDao.findAll();
	}	

	@Override
	public List<FileLogVO> findByFileName(final String fileName)throws DataBaseException {
		return fileLogDao.findByFileName(fileName);
	}

	@Override
	@Transactional
	public void process(SparkFileProcess<FileLogVO> processFile) throws SparkException, DataBaseException, FileIOException,BusinessException {
		List<FileLogVO> processados = sparkFileProcessImpl.process(processFile);
		for (FileLogVO fileLogVO : processados) {
			//VERIFICA SE O ARQUIVO JÁ FOI PROCESSADO
			if(fileLogDao.exist(fileLogVO.getFileName())) {
				final String mensagem = String.format("Arquivo [%s] já processado!", fileLogVO.getFileName());
				LOGGER.info(mensagem);
				//throw new BusinessException(mensagem);
				FileLogVO fileLogByName = fileLogDao.findByName(fileLogVO.getFileName());							
				fileLogVO.setId(fileLogByName.getId());
				fileLogVO.setFileSize(fileLogVO.getFileSize());
				fileLogDao.update(fileLogVO);
				logDao.update(fileLogVO);
			}else {
				final int id = fileLogDao.insert(fileLogVO);
				fileLogVO.setId(id);				
				logDao.insert(fileLogVO);				
			}
		}

	}

}
