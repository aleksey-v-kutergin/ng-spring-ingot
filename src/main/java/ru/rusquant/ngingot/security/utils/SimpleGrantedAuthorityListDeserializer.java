package ru.rusquant.ngingot.security.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SimpleGrantedAuthorityListDeserializer extends JsonDeserializer<Collection<? extends GrantedAuthority>> {

    @Override
    public Collection<? extends GrantedAuthority> deserialize(JsonParser p,
                                                              DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(p);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Iterator<JsonNode> nodeIterator = rootNode.elements();

        while(nodeIterator.hasNext()) {
            JsonNode child = nodeIterator.next();
            Iterator<String> fieldIterator = child.fieldNames();
            while (fieldIterator.hasNext()) {
                String field = fieldIterator.next();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(child.get(field).asText());
                authorities.add(authority);
            }
        }

        return authorities;
    }

}
