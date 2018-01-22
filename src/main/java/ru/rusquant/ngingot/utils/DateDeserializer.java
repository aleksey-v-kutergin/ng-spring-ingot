package ru.rusquant.ngingot.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static ru.rusquant.ngingot.utils.DateSerializer.DATE_FORMAT;

public class DateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
          String dateString = p.getText();
          return StringUtils.isEmpty(dateString) ? null : DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse date string!", e);
        }
    }

}
