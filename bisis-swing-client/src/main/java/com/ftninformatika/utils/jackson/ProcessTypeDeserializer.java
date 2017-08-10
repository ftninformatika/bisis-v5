package com.ftninformatika.utils.jackson;

/**
 * Created by Petar on 8/10/2017.
 */
public class ProcessTypeDeserializer {
    /*@Override
    public ProcessType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        final Long id = node.get("id").asLong();
        final String name = node.get("name").asText();
        final String contents = node.get("contents").asText();
        final long ownerId = node.get("ownerId").asLong();

        ProcessType user = new ProcessType();
        user.setId(ownerId);
        return new ProcessType(id, name, contents, user);
    }*/
}
