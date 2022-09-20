package com.qudini.utils.intervals.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class IntInterval implements NumberInterval<Integer, IntInterval> {

    Integer start;
    Integer end;

    @Override
    public IntInterval withStart(Integer start) {
        return new IntInterval(start, end);
    }

    @Override
    public IntInterval withEnd(Integer end) {
        return new IntInterval(start, end);
    }

}
