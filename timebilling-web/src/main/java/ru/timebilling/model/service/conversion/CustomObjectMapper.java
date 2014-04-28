package ru.timebilling.model.service.conversion;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomObjectMapper extends ObjectMapper{
	
	public CustomObjectMapper() {
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);            
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

}
