package prime;

/**
 * Represents a long range given by the start and end values.
 *
 * Ranges are comparable as follows:
 *      - ranges with lower 'from' are considered less than ranges with greater 'from'
 *      - if ranges start with the same value, ranges with lower 'to' are considered less than ranges with greater 'to'
 *      - otherwise ranges are considered equal
 */
public class Range implements Comparable<Range> {
    private final long from;
    private final long to;

    /**
     * Construct a range with the given start and end values.
     * @param from range start
     * @param to range end
     */
    public Range(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        return from == range.from && to == range.to;

    }

    @Override
    public int hashCode() {
        int result = (int) (from ^ (from >>> 32));
        result = 31 * result + (int) (to ^ (to >>> 32));
        return result;
    }

    /**
     * Get range start.
     * @return range start
     */
    public long getFrom() {
        return from;
    }

    /**
     * Get range end.
     * @return range end
     */
    public long getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Range{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public int compareTo(Range other) {
        int c =  (int)Math.signum(this.from - other.from);
        if (c == 0) {
            c = (int)Math.signum(this.to - other.to);
        }
        return c;
    }
}
