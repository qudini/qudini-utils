package com.qudini.utils.intervals.temporal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class LocalDateTimeInterval implements TemporalInterval<LocalDateTime, LocalDateTimeInterval> {

    LocalDateTime start;
    LocalDateTime end;

    @Override
    public LocalDateTimeInterval withStart(LocalDateTime start) {
        return new LocalDateTimeInterval(start, end);
    }

    @Override
    public LocalDateTimeInterval withEnd(LocalDateTime end) {
        return new LocalDateTimeInterval(start, end);
    }

}
