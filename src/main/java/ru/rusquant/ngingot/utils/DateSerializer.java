package ru.rusquant.ngingot.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Единое правило сериализации дат **/
@Component
public class DateSerializer extends JsonSerializer<Date> {

    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
                                                    throws IOException, JsonProcessingException {
        String formattedDate = DATE_FORMAT.format(value);
        gen.writeString(formattedDate);
    }
}
