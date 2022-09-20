package com.qudini.utils.intervals.temporal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class LocalDateInterval implements TemporalInterval<LocalDate, LocalDateInterval> {

    LocalDate start;
    LocalDate end;

    @Override
    public LocalDateInterval withStart(LocalDate start) {
        return new LocalDateInterval(start, end);
    }

    @Override
    public LocalDateInterval withEnd(LocalDate end) {
        return new LocalDateInterval(start, end);
    }

}
