package com.seckinsen.heimdall.client.utils.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.seckinsen.heimdall.client.utils.json.serializer.LocalDateDeserializer
import com.seckinsen.heimdall.client.utils.json.serializer.LocalDateSerializer
import com.seckinsen.heimdall.client.utils.json.serializer.LocalDateTimeDeserializer
import com.seckinsen.heimdall.client.utils.json.serializer.LocalDateTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime

object ObjectMapperBuilder {

    @JvmStatic
    fun getObjectMapper(): ObjectMapper = ObjectMapper()
        .findAndRegisterModules()
        .registerModule(
            JavaTimeModule()
                .addSerializer(LocalDateTime::class.java,
                    LocalDateTimeSerializer()
                )
                .addDeserializer(LocalDateTime::class.java,
                    LocalDateTimeDeserializer()
                )
                .addSerializer(LocalDate::class.java,
                    LocalDateSerializer()
                )
                .addDeserializer(LocalDate::class.java,
                    LocalDateDeserializer()
                )
        )
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

}