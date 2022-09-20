package com.qudini.utils.intervals.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class LongInterval implements NumberInterval<Long, LongInterval> {

    Long start;
    Long end;

    @Override
    public LongInterval withStart(Long start) {
        return new LongInterval(start, end);
    }

    @Override
    public LongInterval withEnd(Long end) {
        return new LongInterval(start, end);
    }

}
