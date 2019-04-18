package com.seckinsen.heimdall.client.utils.json.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {

    companion object {
        private val DATE_TIME_FORMATTER = ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    @Throws(IOException::class)
    override fun serialize(value: LocalDateTime, generator: JsonGenerator, serializers: SerializerProvider) {
        generator.writeString(value.format(DATE_TIME_FORMATTER))
    }

}
