package com.qudini.utils.intervals.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class ByteInterval implements NumberInterval<Byte, ByteInterval> {

    Byte start;
    Byte end;

    @Override
    public ByteInterval withStart(Byte start) {
        return new ByteInterval(start, end);
    }

    @Override
    public ByteInterval withEnd(Byte end) {
        return new ByteInterval(start, end);
    }

}
