package br.com.ufrj.engsoft.turma37.bigdata.util.file.io;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.ufrj.engsoft.turma37.bigdata.exception.FileIOException;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.validator.Validator;

@Component
public class FileUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
	
	@Value("${spring.spark.folder.save}")
	private String folderSave;

	public synchronized boolean exist(final String folder,final Validator validator) throws FileIOException{
		try {			
			 return !Stream
					 	.of(new File(folder)
					 	.listFiles())
					 	.filter(file -> !file.isDirectory())
					 	.map(p -> p.toString().toLowerCase())					
						.filter(f -> (validator.validate(f)))					
						.collect(Collectors.toSet())
						.isEmpty();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new FileIOException(e);
		}		
	}
	

	public synchronized void move(final Path path) throws FileIOException{
		try {						
			File file = path.toFile();		
			if(file.exists()) {
				new File(folderSave).mkdir();
				Files.move(path, Paths.get(folderSave+"/"+file.getName()), StandardCopyOption.REPLACE_EXISTING);
			}			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new FileIOException(e);
		}
		
	}
	
	public synchronized List<Path> getFilesByExtension(final String folder,final Validator validator)throws FileIOException{		
        List<Path> retorno;
        
        try (Stream<Path> walk = Files.walk(Paths.get(folder),1)) {
        	retorno = walk
                    .filter(Files::isRegularFile)   // is a file
                    .filter(f -> (validator.validate(f.getFileName().toString())))
                    .collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new FileIOException(e);
		}        	
        	        
        return retorno;

	}
	
    public synchronized List<Path> findByFileExtension(String folder, String fileExtension)throws FileIOException {

    	Path path = Paths.get(folder);
        if (!Files.isDirectory(path)) {
            throw new FileIOException("Path must be a directory!");
        }

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path,1)) {
            result = walk
                    .filter(Files::isRegularFile)   // is a file
                    .filter(p -> p.getFileName().toString().endsWith(fileExtension))
                    .collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new FileIOException(e);
		}
        return result;

    }
    
    public synchronized String[] convert(List<Path> paths)throws FileIOException {
    	String[] retorno = new String[0];
    	
    	try{
	    	if(paths!=null) {
	    		retorno = new String[paths.size()];
	    		for (int i = 0; i < paths.size(); i++) {
	    			retorno[i] = paths.get(i).toString();
				}
	    	}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new FileIOException(e);
		}    	
    	return retorno;
    }
    
    public synchronized Long getByteSize(Path path)throws FileIOException {
    	Long size = 0l;
    	
    	try{
    		if(path!=null) {
    			size = Files.size(path);
    		}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new FileIOException(e);
		}    	
    	return size;    	
    }
    
    public synchronized Long getkiloByteSize(Path path)throws FileIOException {
    	Long size = 0l;
    	
    	try{    		
    		if(path!=null) {
    			size = Files.size(path);
    			size = size / 1024;
    		}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new FileIOException(e);
		}    	
    	return size;    	
    }

    public synchronized String convertToString(Path path)throws FileIOException {
    	try {
    		return Files.readString(path);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new FileIOException(e);
		}     	
    }
}
