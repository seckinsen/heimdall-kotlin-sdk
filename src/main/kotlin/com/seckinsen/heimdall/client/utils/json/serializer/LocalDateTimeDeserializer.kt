package com.seckinsen.heimdall.client.utils.json.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {

    companion object {
        private val DATE_FORMATTER = ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime =
        LocalDateTime.parse(parser.valueAsString,
            DATE_FORMATTER
        )

}
