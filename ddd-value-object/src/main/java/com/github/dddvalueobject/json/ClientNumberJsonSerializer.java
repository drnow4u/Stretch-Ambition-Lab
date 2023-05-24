package com.github.dddvalueobject.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.dddvalueobject.ClientNumber;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ClientNumberJsonSerializer extends JsonSerializer<ClientNumber> {

    @Override
    public void serialize(ClientNumber clientNumber,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeString(clientNumber.value());
    }
}
