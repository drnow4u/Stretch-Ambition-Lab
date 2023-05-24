package com.github.dddvalueobject.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.dddvalueobject.DemoId;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class DemoIdJsonDeserializer extends JsonDeserializer<DemoId> {

    @Override
    public DemoId deserialize(JsonParser jsonParser,
                              DeserializationContext deserializationContext) throws IOException {

        TextNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        return new DemoId(treeNode.asInt());
    }
}
