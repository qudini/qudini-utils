package com.qudini.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.qudini.utils.jackson.EmptyToNullStringDeserializer;
import com.qudini.utils.jackson.UnmodifiableCollectionsDeserializerModifier;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonSetter.Value.forValueNulls;
import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MoreJackson {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<List<Object>> LIST_TYPE = new TypeReference<>() {
    };

    /**
     * To deserialize into a {@link Map}.
     */
    public static TypeReference<Map<String, Object>> toMap() {
        return MAP_TYPE;
    }

    /**
     * To deserialize into a {@link List}.
     */
    public static TypeReference<List<Object>> toList() {
        return LIST_TYPE;
    }

    /**
     * <p>Creates a new {@link ObjectMapper} with the following configuration:</p>
     * <ul>
     *     <li>Loads available modules</li>
     *     <li>Serialization:
     *         <ul>
     *             <li>Disables {@link SerializationFeature#WRITE_DATES_AS_TIMESTAMPS}</li>
     *             <li>Includes {@link JsonInclude.Include#NON_EMPTY} only</li>
     *         </ul>
     *     </li>
     *     <li>Deserialization:
     *         <ul>
     *             <li>Deserializes an empty string into null</li>
     *             <li>Disables {@link DeserializationFeature#FAIL_ON_UNKNOWN_PROPERTIES}</li>
     *             <li>Makes {@link List}s, {@link Set}s and {@link Map}s unmodifiable</li>
     *             <li>Uses an empty {@link List}/{@link Set}/{@link Map} instead of null</li>
     *             <li>Uses {@link Nulls#AS_EMPTY} on setters for both value and content</li>
     *         </ul>
     *     </li>
     * </ul>
     */
    public static ObjectMapper newObjectMapper() {
        var module = new SimpleModule()
                .addDeserializer(String.class, new EmptyToNullStringDeserializer())
                .setDeserializerModifier(new UnmodifiableCollectionsDeserializerModifier());
        return new ObjectMapper()
                .disable(FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .setSerializationInclusion(NON_EMPTY)
                .setDefaultSetterInfo(forValueNulls(AS_EMPTY, AS_EMPTY))
                .registerModule(module)
                .findAndRegisterModules();
    }

}
