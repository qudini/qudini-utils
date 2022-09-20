package com.qudini.utils.intervals.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class DoubleInterval implements NumberInterval<Double, DoubleInterval> {

    Double start;
    Double end;

    @Override
    public DoubleInterval withStart(Double start) {
        return new DoubleInterval(start, end);
    }

    @Override
    public DoubleInterval withEnd(Double end) {
        return new DoubleInterval(start, end);
    }

}
