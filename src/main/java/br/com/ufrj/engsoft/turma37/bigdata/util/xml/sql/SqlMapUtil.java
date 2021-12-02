package br.com.ufrj.engsoft.turma37.bigdata.util.xml.sql;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.ufrj.engsoft.turma37.bigdata.exception.FileIOException;

@Component
public class SqlMapUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlMapUtil.class);
	
	private Document doc = null;	
  
	private final String fileName = "sql/bigdata-query.sql";
	
	public SqlMapUtil() throws FileIOException{

		try {
			Resource resource = new ClassPathResource(fileName);
			InputStream input = resource.getInputStream();
		    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    doc = builder.parse(input);			
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			throw new FileIOException(e);
		}

	}

	public synchronized String query(final String name) {
		String retorno = "";
		NodeList nodes = doc.getElementsByTagName("entry");		
		for (int i = 0; i < nodes.getLength();) {
			Element element = (Element) nodes.item(i++);
			NodeList title = element.getElementsByTagName("value");
			Element line = (Element) title.item(0);
			retorno = getCharacterDataFromElement(line);
			break;
		}
		return StringUtils.trim(retorno);
	}
	
	public static String getCharacterDataFromElement(Element e) {
		if(e!=null) {
			Node child = e.getFirstChild();
			if (child!=null && 
				child instanceof CharacterData) {
				CharacterData cd = (CharacterData) child;
				return cd.getData();
			}			
		} 
		return "";
	}

}