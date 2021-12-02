package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.ufrj.engsoft.turma37.bigdata.exception.FileIOException;
import br.com.ufrj.engsoft.turma37.bigdata.exception.SparkException;
import br.com.ufrj.engsoft.turma37.bigdata.model.vo.FileLogVO;
import br.com.ufrj.engsoft.turma37.bigdata.util.file.io.FileUtil;

@Service
public class SparkFileProcessImpl<T extends Serializable> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SparkFileProcessImpl.class);
	
	@Value("${spring.spark.folder.base}") 
	private String folder;
	
	@Autowired
    private FileUtil fileUtil;		

	public List<FileLogVO> process(SparkFileProcess<FileLogVO> sparkProcess) throws SparkException,FileIOException{
		
		List<FileLogVO> processados = new ArrayList<FileLogVO>();
		
		try {
			
			final List<Path> paths = fileUtil.getFilesByExtension(folder,sparkProcess.getValidator());
			
			if(	paths!=null&&
				!paths.isEmpty()) {
				
				for (Path path : paths) {													
					FileLogVO fileLogVO = new FileLogVO(path.getFileName().toString());				
					fileLogVO.setFullPath(path.toString());
					fileLogVO.setFileSize(fileUtil.getkiloByteSize(path));
					fileLogVO.setDataProcs(new Date());
					processados.add(fileLogVO);
				}
				
				processados = sparkProcess.process(processados);
				
				for (FileLogVO processado : processados) {
					fileUtil.move(Path.of(processado.getFullPath()));
				}
				
			}

		} catch (SparkException e) {
			LOGGER.error(e.getLocalizedMessage());
			throw new SparkException(e);
		} catch (FileIOException e) {
			LOGGER.error(e.getLocalizedMessage());
			throw new FileIOException(e);
		}
		
		return processados;
	}
	
	

}
