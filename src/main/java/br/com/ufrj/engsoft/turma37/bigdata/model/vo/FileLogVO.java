package br.com.ufrj.engsoft.turma37.bigdata.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FileLogVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int		id;
	private String 	fileName;
	private String 	fullPath;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")	
	private Date 	dataProcs;
	private Long 	fileSize;
	
	private List<LogVO> logs = new ArrayList<LogVO>();
	
	private int 	totalTrace;
	private int 	totalDebug;
	private int 	totalInfo;
	private int 	totalWarn;
	private int 	totalError;
	private int 	totalFatal;

	public FileLogVO() {
	}
	
	public FileLogVO(final String fileName) {
		this.fileName = fileName;
	}
	
	public FileLogVO(final String fileName,List<LogVO> logs) {	
		this.fileName 	= fileName;	
		this.logs 		= logs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public Date getDataProcs() {
		return dataProcs;
	}

	public void setDataProcs(Date dataProcs) {
		this.dataProcs = dataProcs;
	}

	public int getTotalTrace() {
		return totalTrace;
	}

	public void setTotalTrace(int totalTrace) {
		this.totalTrace = totalTrace;
	}

	public int getTotalDebug() {
		return totalDebug;
	}

	public void setTotalDebug(int totalDebug) {
		this.totalDebug = totalDebug;
	}

	public int getTotalInfo() {
		return totalInfo;
	}

	public void setTotalInfo(int totalInfo) {
		this.totalInfo = totalInfo;
	}

	public int getTotalWarn() {
		return totalWarn;
	}

	public void setTotalWarn(int totalWarn) {
		this.totalWarn = totalWarn;
	}

	public int getTotalError() {
		return totalError;
	}

	public void setTotalError(int totalError) {
		this.totalError = totalError;
	}

	public int getTotalFatal() {
		return totalFatal;
	}

	public void setTotalFatal(int totalFatal) {
		this.totalFatal = totalFatal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<LogVO> getLogs() {
		return logs;
	}

	public void setLogs(List<LogVO> logs) {
		this.logs = logs;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

}
