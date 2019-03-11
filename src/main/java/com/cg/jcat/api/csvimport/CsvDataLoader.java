package com.cg.jcat.api.csvimport;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Component
public class CsvDataLoader {
	
	public <T> List<T> loadObjectList(Class<T> type, String fileName, MultipartFile file1) {
	    try {
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
	        CsvMapper mapper = new CsvMapper();
//	        File file = new ClassPathResource(fileName).getFile();
	        System.out.println(fileName);
	        System.out.println(file1.isEmpty());
	        byte[] file = file1.getBytes() ;
	        MappingIterator<T> readValues = 
	          mapper.reader(type).with(bootstrapSchema).readValues(file);
	        return readValues.readAll();
	    } catch (Exception e) {
	        return Collections.emptyList();
	    }
	}

}
