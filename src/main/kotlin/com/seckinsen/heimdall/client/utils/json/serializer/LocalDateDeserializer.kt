package com.seckinsen.heimdall.client.utils.json.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern

class LocalDateDeserializer : JsonDeserializer<LocalDate>() {

    companion object {
        private val DATE_FORMATTER = ofPattern("yyyy-MM-dd")
    }

    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDate =
        LocalDate.parse(parser.valueAsString,
            DATE_FORMATTER
        )

}
