 package br.com.ufrj.engsoft.turma37.bigdata.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;



public class FileLogMapper implements RowMapper<FileLogVO> {

	@Override
	public FileLogVO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    FileLogVO fileLogVO = new FileLogVO();    	   	
      	fileLogVO.setId(rs.getInt("ID_FILE_LOG"));
      	fileLogVO.setFileName(rs.getString("NAME_FILE_LOG"));
      	fileLogVO.setDataProcs(rs.getTimestamp("DATE_FILE_LOG"));
      	fileLogVO.setFileSize(rs.getLong("SIZE_FILE_LOG"));      	
      	fileLogVO.setTotalDebug(rs.getInt("TOTAL_DEBUG"));
      	fileLogVO.setTotalError(rs.getInt("TOTAL_ERROR"));
      	fileLogVO.setTotalFatal(rs.getInt("TOTAL_FATAL"));
      	fileLogVO.setTotalInfo(rs.getInt("TOTAL_INFO"));
      	fileLogVO.setTotalTrace(rs.getInt("TOTAL_TRACE"));
      	fileLogVO.setTotalWarn(rs.getInt("TOTAL_WARN"));
      	return fileLogVO;
	}

}
