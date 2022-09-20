package com.qudini.utils.intervals.temporal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(staticName = "of")
public class InstantInterval implements TemporalInterval<Instant, InstantInterval> {

    Instant start;
    Instant end;

    @Override
    public InstantInterval withStart(Instant start) {
        return new InstantInterval(start, end);
    }

    @Override
    public InstantInterval withEnd(Instant end) {
        return new InstantInterval(start, end);
    }

}
