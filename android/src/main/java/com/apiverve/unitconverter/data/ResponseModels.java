// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     UnitConverterData data = Converter.fromJsonString(jsonString);

package com.apiverve.unitconverter.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static UnitConverterData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(UnitConverterData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(UnitConverterData.class);
        writer = mapper.writerFor(UnitConverterData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// UnitConverterData.java

package com.apiverve.unitconverter.data;

import com.fasterxml.jackson.annotation.*;

public class UnitConverterData {
    private Result result;
    private UnitDefinitions unitDefinitions;

    @JsonProperty("result")
    public Result getResult() { return result; }
    @JsonProperty("result")
    public void setResult(Result value) { this.result = value; }

    @JsonProperty("unitDefinitions")
    public UnitDefinitions getUnitDefinitions() { return unitDefinitions; }
    @JsonProperty("unitDefinitions")
    public void setUnitDefinitions(UnitDefinitions value) { this.unitDefinitions = value; }
}

// Result.java

package com.apiverve.unitconverter.data;

import com.fasterxml.jackson.annotation.*;

public class Result {
    private double result;
    private String from;
    private String to;

    @JsonProperty("result")
    public double getResult() { return result; }
    @JsonProperty("result")
    public void setResult(double value) { this.result = value; }

    @JsonProperty("from")
    public String getFrom() { return from; }
    @JsonProperty("from")
    public void setFrom(String value) { this.from = value; }

    @JsonProperty("to")
    public String getTo() { return to; }
    @JsonProperty("to")
    public void setTo(String value) { this.to = value; }
}

// UnitDefinitions.java

package com.apiverve.unitconverter.data;

import com.fasterxml.jackson.annotation.*;

public class UnitDefinitions {
    private From from;
    private From to;

    @JsonProperty("from")
    public From getFrom() { return from; }
    @JsonProperty("from")
    public void setFrom(From value) { this.from = value; }

    @JsonProperty("to")
    public From getTo() { return to; }
    @JsonProperty("to")
    public void setTo(From value) { this.to = value; }
}

// From.java

package com.apiverve.unitconverter.data;

import com.fasterxml.jackson.annotation.*;

public class From {
    private String abbr;
    private String measure;
    private String system;
    private String singular;
    private String plural;

    @JsonProperty("abbr")
    public String getAbbr() { return abbr; }
    @JsonProperty("abbr")
    public void setAbbr(String value) { this.abbr = value; }

    @JsonProperty("measure")
    public String getMeasure() { return measure; }
    @JsonProperty("measure")
    public void setMeasure(String value) { this.measure = value; }

    @JsonProperty("system")
    public String getSystem() { return system; }
    @JsonProperty("system")
    public void setSystem(String value) { this.system = value; }

    @JsonProperty("singular")
    public String getSingular() { return singular; }
    @JsonProperty("singular")
    public void setSingular(String value) { this.singular = value; }

    @JsonProperty("plural")
    public String getPlural() { return plural; }
    @JsonProperty("plural")
    public void setPlural(String value) { this.plural = value; }
}