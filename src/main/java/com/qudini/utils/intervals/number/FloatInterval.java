package com.qudini.utils.intervals.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class FloatInterval implements NumberInterval<Float, FloatInterval> {

    Float start;
    Float end;

    @Override
    public FloatInterval withStart(Float start) {
        return new FloatInterval(start, end);
    }

    @Override
    public FloatInterval withEnd(Float end) {
        return new FloatInterval(start, end);
    }

}
