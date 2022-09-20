package com.qudini.utils.intervals.temporal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalTime;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class LocalTimeInterval implements TemporalInterval<LocalTime, LocalTimeInterval> {

    LocalTime start;
    LocalTime end;

    @Override
    public LocalTimeInterval withStart(LocalTime start) {
        return new LocalTimeInterval(start, end);
    }

    @Override
    public LocalTimeInterval withEnd(LocalTime end) {
        return new LocalTimeInterval(start, end);
    }

}
