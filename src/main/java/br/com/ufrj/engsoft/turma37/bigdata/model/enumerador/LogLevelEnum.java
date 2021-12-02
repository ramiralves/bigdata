package br.com.ufrj.engsoft.turma37.bigdata.model.enumerador;

import org.apache.commons.lang3.StringUtils;

public enum LogLevelEnum {
	
	TRACE	("Trace"),
	DEBUG	("Debug"),
	INFO	("Information"),
	WARN	("Warning"),
	ERROR	("Error"),
	FATAL	("Fatal"),
	NAO_INFO("Nao_Informado");

	private final String level;

	private LogLevelEnum(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}
	
	public static LogLevelEnum getEnumByLevel(final String texto){
		for (LogLevelEnum enumerador : LogLevelEnum.values()) {
			if(	StringUtils.isNotEmpty(texto)&&
				texto.contains(enumerador.getLevel())) {
				return enumerador;
			}
		}
		return LogLevelEnum.NAO_INFO;		
	}
	
	public static LogLevelEnum getEnum(final String texto){
		for (LogLevelEnum enumerador : LogLevelEnum.values()) {
			if(	StringUtils.isNotEmpty(texto)&&
				texto.contains(enumerador.toString())) {
				return enumerador;
			}
		}
		return LogLevelEnum.NAO_INFO;
	}
	
	public static boolean exist(final String texto) {
		LogLevelEnum logLevel = LogLevelEnum.getEnum(texto);
		return (LogLevelEnum.TRACE.equals(logLevel)||
				LogLevelEnum.DEBUG.equals(logLevel)||
				LogLevelEnum.INFO.equals(logLevel)||
				LogLevelEnum.WARN.equals(logLevel)||
				LogLevelEnum.ERROR.equals(logLevel)||
				LogLevelEnum.FATAL.equals(logLevel));
	}

}
