package com.qudini.utils.intervals;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.qudini.utils.MoreComparables.isGreaterThan;
import static com.qudini.utils.MoreComparables.isGreaterThanOrEqualTo;
import static com.qudini.utils.MoreComparables.isLessThan;
import static com.qudini.utils.MoreComparables.isLessThanOrEqualTo;

public interface Interval<E extends Comparable<? super E>, T extends Interval<E, T>> extends Comparable<T> {

    E getStart();

    E getEnd();

    @JsonIgnore
    default boolean isPositive() {
        return isLessThan(getStart(), getEnd());
    }

    @JsonIgnore
    default boolean isNegative() {
        return isGreaterThan(getStart(), getEnd());
    }

    @JsonIgnore
    default boolean isEmpty() {
        return getStart().equals(getEnd());
    }

    /**
     * <p>Whether this interval overlaps with the given one, for example:</p>
     * <ul>
     * <li>1-3 and 2-4: true</li>
     * <li>1-4 and 2-3: true (see also {@link #contains(Interval)})</li>
     * <li>1-2 and 2-3: false (see also {@link #isContiguousWith(Interval)})</li>
     * </ul>
     */
    default boolean overlaps(Interval<E, ?> o) {
        return isLessThan(getStart(), o.getEnd()) && isGreaterThan(getEnd(), o.getStart());
    }

    /**
     * <p>Whether this interval is contiguous with the given one, for example:</p>
     * <ul>
     * <li>1-2 and 2-3: true</li>
     * <li>1-3 and 2-4: false (see also {@link #overlaps(Interval)})</li>
     * <li>1-2 and 3-4: false</li>
     * </ul>
     */
    default boolean isContiguousWith(Interval<E, ?> o) {
        return getStart().equals(o.getEnd()) || getEnd().equals(o.getStart());
    }

    /**
     * <p>Whether this interval entirely contains the given one, for example:</p>
     * <ul>
     * <li>1-3 and 1-2: true</li>
     * <li>1-3 and 1-3: true</li>
     * <li>1-3 and 1-4: false (see also {@link #overlaps(Interval)})</li>
     * </ul>
     */
    default boolean contains(Interval<E, ?> o) {
        return isLessThanOrEqualTo(getStart(), o.getStart()) && isGreaterThanOrEqualTo(getEnd(), o.getEnd());
    }

    @Override
    default int compareTo(T o) {
        int result = getStart().compareTo(o.getStart());
        if (result == 0) {
            result = getEnd().compareTo(o.getEnd());
        }
        return result;
    }

}
