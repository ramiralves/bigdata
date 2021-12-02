package br.com.ufrj.engsoft.turma37.bigdata.model.dao;

import java.util.List;

import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.LogVO;

public interface LogDao {
	public List<LogVO> findAll()throws DataBaseException;
	public List<LogVO> findById(final int id)throws DataBaseException;
	public List<LogVO> findByLevel(final LogLevelEnum log)throws DataBaseException;
	public void update(final int fileLogId,LogVO log)throws DataBaseException;
	public int insert(final int fileLogId,LogVO logVO) throws DataBaseException;
	public boolean exist(final int fileLogId)throws DataBaseException;
	public int insert(FileLogVO fileLogs) throws DataBaseException;
	public void update(FileLogVO fileLogs)throws DataBaseException;

}
