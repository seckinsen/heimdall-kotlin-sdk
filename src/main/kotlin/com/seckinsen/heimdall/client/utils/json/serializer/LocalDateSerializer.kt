package com.seckinsen.heimdall.client.utils.json.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern

class LocalDateSerializer : JsonSerializer<LocalDate>() {

    companion object {
        private val DATE_FORMATTER = ofPattern("yyyy-MM-dd")
    }

    @Throws(IOException::class)
    override fun serialize(value: LocalDate, generator: JsonGenerator, serializers: SerializerProvider) {
        generator.writeString(value.format(DATE_FORMATTER))
    }

}
