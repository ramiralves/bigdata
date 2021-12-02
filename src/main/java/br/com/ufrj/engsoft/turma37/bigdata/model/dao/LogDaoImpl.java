package br.com.ufrj.engsoft.turma37.bigdata.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.model.dao.mapper.LogMapper;
import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.LogVO;

@Repository
public class LogDaoImpl implements LogDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogDao.class);
	
	private static final String SELECT = "SELECT ID_LEVEL_LOG, DESC_LEVEL_LOG, TOTAL_LEVEL_LOG FROM MYDB.LEVEL_LOG";
	
	private static final String UPDATE = "UPDATE MYDB.LEVEL_LOG SET TOTAL_LEVEL_LOG=? WHERE ID_LEVEL_LOG=? AND DESC_LEVEL_LOG = ?";
	
	private static final String INSERT = "INSERT INTO MYDB.LEVEL_LOG (ID_LEVEL_LOG, DESC_LEVEL_LOG, TOTAL_LEVEL_LOG) VALUES(?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<LogVO> findAll() throws DataBaseException{
		List<LogVO> lista = new ArrayList<LogVO>();
		try {
			lista = jdbcTemplate.query(
					SELECT,               
	         			new LogMapper()
					);			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}         
		return lista;
	}		

	@Override
	public List<LogVO> findByLevel(final LogLevelEnum log) throws DataBaseException{

		List<LogVO> lista = new ArrayList<LogVO>();
		try {
			final StringBuffer query = new StringBuffer(SELECT);
			query.append(" WHERE DESC_LEVEL_LOG = :DESC_LEVEL_LOG");			
			MapSqlParameterSource params = new MapSqlParameterSource()
					.addValue("DESC_LEVEL_LOG", log.toString());			
			lista = namedParameterJdbcTemplate.query(query.toString(), params, new LogMapper());			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}         
		return lista;		
		

	}

	@Override
	public void update(final int fileLogId,LogVO log) throws DataBaseException{
		try {
	        jdbcTemplate.update(
	        		UPDATE,
	        		log.getTotal(),
	        		fileLogId,
	        		log.getLevel().toString()
	        );			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}
		
	}
	
	@Override
	public int insert(final int fileLogId,LogVO logVO) throws DataBaseException {
		try {		
	        return jdbcTemplate.update(        		
	        		INSERT,
	                fileLogId,
	                logVO.getLevel(),
	                logVO.getTotal());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}          
	}



	@Override
	public boolean exist(int fileLogId) throws DataBaseException {
		boolean exist = false;
		try {
			final Integer total = this.jdbcTemplate.queryForObject(
	                "SELECT COUNT(ID_LEVEL_LOG) FROM MYDB.LEVEL_LOG WHERE ID_LEVEL_LOG = ?", new Object[] { fileLogId }, Integer.class);
			exist = (total>0);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		} 		
		return exist;
	}
	
	@Override
	public int insert(FileLogVO fileLogs) throws DataBaseException {
		try {			
			
			List<LogVO> logs = fileLogs.getLogs();
			
	        this.jdbcTemplate.batchUpdate(INSERT,
							                new BatchPreparedStatementSetter() {
							                    public void setValues(PreparedStatement ps, int i) throws SQLException {
							                    	final Integer 	id 		= fileLogs.getId();
							                    	final String 	level 	= logs.get(i).getLevel().toString();
							                    	final Integer 	total 	= logs.get(i).getTotal();
							                    	
							                    	//ID_LEVEL_LOG, DESC_LEVEL_LOG, TOTAL_LEVEL_LOG
							                        ps.setInt(1, 	id);
							                        ps.setString(2, level);
							                        ps.setInt(3, 	total);
							                    }
						
							                    public int getBatchSize() {
							                        return logs.size();
							                    }
							                });
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}
		return fileLogs.getId();
	}	

	@Override
	public void update(FileLogVO fileLogs) throws DataBaseException{
		try {
			
			List<LogVO> logs = fileLogs.getLogs();
			
	        this.jdbcTemplate.batchUpdate(UPDATE,
							                new BatchPreparedStatementSetter() {
							                    public void setValues(PreparedStatement ps, int i) throws SQLException {

							                    	final Integer 	id 		= fileLogs.getId();
							                    	final String 	level 	= logs.get(i).getLevel().toString();
							                    	final Integer 	total 	= logs.get(i).getTotal();							                    	
							                    	
							                        ps.setInt(1, 	total);
							                        ps.setInt(2, 	id);
							                        ps.setString(3, level);
							                    }
						
							                    public int getBatchSize() {
							                        return logs.size();
							                    }
							                });			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		} 

	}

	@Override
	public List<LogVO> findById(int id) throws DataBaseException {
		List<LogVO> lista = new ArrayList<LogVO>();
		try {
			final StringBuffer query = new StringBuffer(SELECT);
			query.append(" WHERE ID_LEVEL_LOG = :ID_LEVEL_LOG");			
			MapSqlParameterSource params = new MapSqlParameterSource()
					.addValue("ID_LEVEL_LOG", id);			
			lista = namedParameterJdbcTemplate.query(query.toString(), params, new LogMapper());			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new DataBaseException(e);
		}         
		return lista;
	}	

}
