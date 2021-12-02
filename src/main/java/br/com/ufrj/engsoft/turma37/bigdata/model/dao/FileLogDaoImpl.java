package br.com.ufrj.engsoft.turma37.bigdata.model.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.model.dao.mapper.FileLogMapper;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;
import br.com.ufrj.engsoft.turma37.bigdata.util.xml.sql.SqlMapUtil;

@Repository
public class FileLogDaoImpl implements FileLogDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogDao.class);	
	
	private static final String SELECT_FILE_LOG = "SELECT_FILE_LOG";
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private SqlMapUtil sqlMap;

	@Override
	public List<FileLogVO> findAll() throws DataBaseException {
		List<FileLogVO> lista = new ArrayList<FileLogVO>();
		try {
			
			final String query = sqlMap.query(FileLogDaoImpl.SELECT_FILE_LOG);
			
			lista = namedParameterJdbcTemplate.query(query,  new FileLogMapper());		
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}         
		return lista;
	}	

	@Override
	public FileLogVO findById(int id) throws DataBaseException {
		try {			
			String query = sqlMap.query(FileLogDaoImpl.SELECT_FILE_LOG);
			
			StringBuilder sql = new StringBuilder(query);
			sql.append("WHERE ID_FILE_LOG = ?");			
	        return jdbcTemplate.queryForObject(
	        		sql.toString(),
	                new Object[]{id},
	                new FileLogMapper()
	        );
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}          
	}

	@Override
	public FileLogVO findByName(String fileName) throws DataBaseException {
		try {				        
			final String query = sqlMap.query(FileLogDaoImpl.SELECT_FILE_LOG);
			
			final StringBuffer sql = new StringBuffer(query);
			sql.append(" WHERE NAME_FILE_LOG = :NAME_FILE_LOG");			
			MapSqlParameterSource params = new MapSqlParameterSource()
					.addValue("NAME_FILE_LOG", fileName);			
			return namedParameterJdbcTemplate.queryForObject(sql.toString(), params, new FileLogMapper());	        
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		} 	
	}

	@Override
	public int insert(FileLogVO fileLog) throws DataBaseException {

		try {			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
		        							.prepareStatement("INSERT INTO MYDB.FILE_LOG (NAME_FILE_LOG, SIZE_FILE_LOG, DATE_FILE_LOG ) VALUES(?, ?, CURRENT_TIMESTAMP)",Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, fileLog.getFileName());
		        ps.setLong(2, fileLog.getFileSize());
		        return ps;
			}, keyHolder);			

	        Number key = keyHolder.getKey();
	        
	        return key.intValue();	        
	        
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}          
	}

	@Override
	public List<FileLogVO> findByFileName(String fileName) throws DataBaseException {
		List<FileLogVO> lista = new ArrayList<FileLogVO>();
		try {
			
			final String query = sqlMap.query(FileLogDaoImpl.SELECT_FILE_LOG);
			
			final StringBuffer sql = new StringBuffer(query);
			sql.append(" WHERE NAME_FILE_LOG LIKE :NAME_FILE_LOG");			
			MapSqlParameterSource params = new MapSqlParameterSource()
					.addValue("NAME_FILE_LOG", "%"+fileName+"%");
			lista = namedParameterJdbcTemplate.query(sql.toString(), params, new FileLogMapper());			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}         
		return lista;
	}

	@Override
	public boolean exist(String fileName) throws DataBaseException {
		boolean exist = false;
		try {
			final Integer total = this.jdbcTemplate.queryForObject(
	                "SELECT COUNT(ID_FILE_LOG) FROM MYDB.FILE_LOG WHERE NAME_FILE_LOG = ?", new Object[] { fileName }, Integer.class);
			exist = (total>0);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		} 		
		return exist;
	}

	@Override
	public void update(FileLogVO fileLog) throws DataBaseException {
		try {
	        jdbcTemplate.update(
	        		"UPDATE MYDB.FILE_LOG SET SIZE_FILE_LOG = ?, DATE_FILE_LOG = CURRENT_TIMESTAMP WHERE ID_FILE_LOG = ?",
	        		fileLog.getFileSize(),
	        		fileLog.getId()
	        );			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}
	}
	
}
