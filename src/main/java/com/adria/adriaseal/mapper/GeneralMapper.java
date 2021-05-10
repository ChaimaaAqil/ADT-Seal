package com.adria.adriaseal.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Mapper(builder = @Builder(disableBuilder = true))
public interface GeneralMapper {
    default String map(UUID value) {
        return value.toString();
    }

    default UUID map(String value) {
        return UUID.fromString(value);
    }

    default String map(Date value) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(value);
    }
}
