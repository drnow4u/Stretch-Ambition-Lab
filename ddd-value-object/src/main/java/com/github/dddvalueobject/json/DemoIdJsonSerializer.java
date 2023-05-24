package com.github.dddvalueobject.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.dddvalueobject.DemoId;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class DemoIdJsonSerializer extends JsonSerializer<DemoId> {

    @Override
    public void serialize(DemoId id,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeNumber(id.id());
    }
}
