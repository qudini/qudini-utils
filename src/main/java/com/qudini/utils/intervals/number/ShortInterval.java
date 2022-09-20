package com.qudini.utils.intervals.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class ShortInterval implements NumberInterval<Short, ShortInterval> {

    Short start;
    Short end;

    @Override
    public ShortInterval withStart(Short start) {
        return new ShortInterval(start, end);
    }

    @Override
    public ShortInterval withEnd(Short end) {
        return new ShortInterval(start, end);
    }

}
