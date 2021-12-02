 package br.com.ufrj.engsoft.turma37.bigdata.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.LogVO;



public class LogMapper implements RowMapper<LogVO> {

	@Override
	public LogVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		LogVO logVO = new LogVO();
		logVO.setId(rs.getInt("ID_LEVEL_LOG"));
		logVO.setLevel(LogLevelEnum.getEnum(rs.getString("DESC_LEVEL_LOG")));
		logVO.setTotal(rs.getInt("TOTAL_LEVEL_LOG"));			
	    return logVO;
	}

}
