package com.qudini.utils.intervals.temporal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetTime;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class OffsetTimeInterval implements TemporalInterval<OffsetTime, OffsetTimeInterval> {

    OffsetTime start;
    OffsetTime end;

    @Override
    public OffsetTimeInterval withStart(OffsetTime start) {
        return new OffsetTimeInterval(start, end);
    }

    @Override
    public OffsetTimeInterval withEnd(OffsetTime end) {
        return new OffsetTimeInterval(start, end);
    }

}
