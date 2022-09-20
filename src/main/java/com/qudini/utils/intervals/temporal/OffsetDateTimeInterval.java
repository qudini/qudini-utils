package com.qudini.utils.intervals.temporal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class OffsetDateTimeInterval implements TemporalInterval<OffsetDateTime, OffsetDateTimeInterval> {

    OffsetDateTime start;
    OffsetDateTime end;

    @Override
    public OffsetDateTimeInterval withStart(OffsetDateTime start) {
        return new OffsetDateTimeInterval(start, end);
    }

    @Override
    public OffsetDateTimeInterval withEnd(OffsetDateTime end) {
        return new OffsetDateTimeInterval(start, end);
    }

}
