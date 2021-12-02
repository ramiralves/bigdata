package br.com.ufrj.engsoft.turma37.bigdata.controller.datatable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/*@Setter
@Getter
@NoArgsConstructor*/
public class PagingRequest<T> implements Serializable{

    /**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private int 				start;
    private int 				length;
    private int 				draw = 1;
    private int 				recordsFiltered;
    private int 				recordsTotal;
    private Map<String,String> 	search;
    private List<T> 			data;
        
    public PagingRequest() {
    	
    }    
    
    public PagingRequest<T> page(List<T> data) {
    	this.recordsTotal 		= data.size();
    	this.recordsFiltered 	= data.size();
    	int total 				= data.size();    
    	this.data 				= data;

    	if(start+length<=total) {
    		total=start+length;
    	}
    	if(total!=0) {
    		this.data = data.subList(start,total);
    	}		    	
    	return this;
    }    
    

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Map<String, String> getSearch() {
		return search;
	}

	public void setSearch(Map<String, String> search) {
		this.search = search;
	}

    
    
}
