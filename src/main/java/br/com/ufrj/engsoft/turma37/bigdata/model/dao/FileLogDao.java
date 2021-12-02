package br.com.ufrj.engsoft.turma37.bigdata.model.dao;

import java.util.List;

import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;

public interface FileLogDao {		
	public boolean exist(String fileName)throws DataBaseException;
	public List<FileLogVO> findAll()throws DataBaseException;
	public List<FileLogVO> findByFileName(String fileName)throws DataBaseException;
	public FileLogVO findById(int id)throws DataBaseException;
	public FileLogVO findByName(String fileName)throws DataBaseException;
	public int insert(FileLogVO fileLog)throws DataBaseException;
	public void update(FileLogVO fileLog)throws DataBaseException;
}
