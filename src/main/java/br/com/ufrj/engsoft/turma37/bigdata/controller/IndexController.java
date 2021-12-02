package br.com.ufrj.engsoft.turma37.bigdata.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.ufrj.engsoft.turma37.bigdata.controller.datatable.PagingRequest;
import br.com.ufrj.engsoft.turma37.bigdata.exception.DataBaseException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.service.LogService;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO; 

@Controller
public class IndexController {  
 
	@Autowired 
	private LogService<FileLogVO> logService; 

	@GetMapping("/")
    public String index(Model model) { 
        return "main"; //view 
    }  

    @RequestMapping(method = RequestMethod.GET, produces="application/json" )
    @ResponseBody
    public PagingRequest<FileLogVO>  find(PagingRequest<FileLogVO> pagingRequest)throws SparkException, DataBaseException {
    	
    	final String fileName = filter(pagingRequest);  	    	
    	    	
    	List<FileLogVO> logs = new ArrayList<FileLogVO>();
    	
    	if(StringUtils.isEmpty(fileName)) {  		    		    		
    		logs = logService.findAll();
    	}else {
    		logs = logService.findByFileName(fileName); 
    	}

    	return pagingRequest.page(logs);
    }
        
    private String filter(PagingRequest<FileLogVO> pagingRequest) {
    	String retorno = "";
    	if(	pagingRequest!=null&&
    		pagingRequest.getSearch()!=null&&
    		pagingRequest.getSearch().get("value")!=null) {
    		retorno = pagingRequest.getSearch().get("value");
    	}
    	return retorno;
    }
    
} 