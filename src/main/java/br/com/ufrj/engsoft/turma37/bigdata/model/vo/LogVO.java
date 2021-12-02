package br.com.ufrj.engsoft.turma37.bigdata.model.vo;

import java.io.Serializable;

import br.com.ufrj.engsoft.turma37.bigdata.model.enumerador.LogLevelEnum;

public class LogVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private LogLevelEnum level;	
	private int total;	
	
	public LogVO() {		
	}
	
	public LogVO(LogLevelEnum level, int total) {		
		this.level = level;
		this.total = total;
	}	
	
	public LogVO(String level, int total) {		
		this.level = LogLevelEnum.getEnum(level);
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LogLevelEnum getLevel() {
		return level;
	}

	public void setLevel(LogLevelEnum level) {
		this.level = level;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
	

}
