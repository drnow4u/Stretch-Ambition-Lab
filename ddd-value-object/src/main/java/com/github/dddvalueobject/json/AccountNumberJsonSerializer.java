package com.github.dddvalueobject.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.dddvalueobject.AccountNumber;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class AccountNumberJsonSerializer extends JsonSerializer<AccountNumber> {

    @Override
    public void serialize(AccountNumber accountNumber,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeString(accountNumber.value());
    }
}
