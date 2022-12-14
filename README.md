# qudini-utils

## Installation

```xml
<dependencies>
    <dependency>
        <groupId>com.qudini</groupId>
        <artifactId>qudini-utils</artifactId>
        <version>${qudini-utils.version}</version>
    </dependency>
</dependencies>
```

## Usage

### com.qudini.reactive.utils.MoreMonos

Utilities around monos. Needs [io.projectreactor:reactor-core](https://search.maven.org/artifact/io.projectreactor/reactor-core).

```java
Mono<Optional<T>> example(Mono<T> mono) {
    return mono.transform(MoreMonos::toOptional);
}
```

### com.qudini.reactive.utils.MoreFunctions

Utilities around functions.

#### Throwable functions

```java
Consumer<T> example() {
    return throwableConsumer(x -> {
        throw new Exception();
    });
}

Function<T, R> example() {
    return throwableFunction(x -> {
        throw new Exception();
    });
}

Supplier<R> example() {
    return throwableSupplier(() -> {
        throw new Exception();
    });
}

Predicate<T> example() {
    return throwablePredicate(x -> {
        throw new Exception();
    });
}

BiConsumer<T1, T2> example() {
    return throwableBiConsumer((x, y) -> {
        throw new Exception();
    });
}

BiFunction<T1, T2, R> example() {
    return throwableBiFunction((x, y) -> {
        throw new Exception();
    });
}

BiPredicate<T1, T2> example() {
    return throwableBiPredicate((x, y) -> {
        throw new Exception();
    });
}
```

### com.qudini.reactive.utils.MoreTuples

Utilities around tuples. Needs [io.projectreactor:reactor-core](https://search.maven.org/artifact/io.projectreactor/reactor-core).

#### Building

```java
Flux<Tuple2<T1, T2>> example(Mono<Map<T1, T2>> map) {
    return map
            .flatMapIterable(Map::entrySet)
            .map(MoreTuples::fromEntry);
}

Tuple2<Integer, Integer> example() {
    return MoreTuples.fromArray(new Integer[]{1, 2});
}
```

#### Reducing

```java
Mono<Integer> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.map(MoreTuples::left);
}

Mono<String> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.map(MoreTuples::right);
}

Mono<String> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.map(onBoth((i, s) -> s + i));
}

Mono<String> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.flatMap(onBoth((i, s) -> Mono.just(s + i)));
}
```

#### Mapping

```java
Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.map(onLeft(i -> i + 1));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.flatMap(onLeftWhen(i -> Mono.just(i + 1)));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.map(onRight(s -> s + "bar"));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.flatMap(onRightWhen(s -> Mono.just(s + "bar")));
}

Mono<Tuple2<String, String>> example(Mono<Tuple2<Integer, Integer>> mono) {
    return mono.map(onEach(Object::toString));
}
```

#### Filtering

```java
Mono<Tuple2<Integer, Integer>> example(Mono<Tuple2<Integer, Integer>> mono) {
    return mono.filter(ifEach(i -> 0 < i));
}

Mono<Tuple2<Integer, Integer>> example(Mono<Tuple2<Integer, Integer>> mono) {
    return mono.filter(ifEither(i -> 0 < i));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.filter(ifLeft(i -> 0 < i));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.filter(ifRight("foobar"::equals));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.filter(ifBoth((i, s) -> "foo42".equals(s + i)));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.filterWhen(fromLeft(i -> Mono.just(0 < i)));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.filterWhen(fromRight(s -> Mono.just("foobar".equals(s))));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.filterWhen(onBoth((i, s) -> Mono.just("foo42".equals(s + i))));
}
```

#### Consuming

```java
Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.doOnNext(takeLeft(i -> log.debug("i:{}", i)));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.doOnNext(takeRight(s -> log.debug("s:{}", s)));
}

Mono<Tuple2<Integer, String>> example(Mono<Tuple2<Integer, String>> mono) {
    return mono.doOnNext(takeBoth((i, s) -> log.debug("i:{} s:{}", i, s)));
}
```

### com.qudini.reactive.utils.MoreCollectors

Utilities around collectors:

- `#toLinkedMap(...)`: collects into a `LinkedHashMap`,
- `#toUnmodifiableLinkedMap(...)`: collects into an unmodifiable `LinkedHashMap`,
- `#toTreeMap(...)`: collects into a `TreeMap`,
- `#toUnmodifiableTreeMap(...)`: collects into an unmodifiable `TreeMap`,
- `#toLinkedSet(...)`: collects into a `LinkedHashSet`,
- `#toUnmodifiableLinkedSet(...)`: collects into an unmodifiable `LinkedHashSet`,
- `#toTreeSet(...)`: collects into a `TreeSet`,
- `#toUnmodifiableTreeSet(...)`: collects into an unmodifiable `TreeSet`,
- `#groupingByUnmodifiable(...)`: groups into an unmodifiable map,
- `#partitioningByUnmodifiable(...)`: partitions into an unmodifiable map,
- `#throwingMerger(...)`: throws an exception (useful when a merger is expected by the API but won't ever get called).

### com.qudini.reactive.utils.MoreComparables

Utilities around comparables to avoid dealing with the result of `compareTo` manually:

- `T leastBetween(T a, T b)`
- `T greatestBetween(T a, T b)`
- `boolean isLessThan(T a, T b)`
- `boolean isLessThanOrEqualTo(T a, T b)`
- `boolean isGreaterThan(T a, T b)`
- `boolean isGreaterThanOrEqualTo(T a, T b)`

### com.qudini.reactive.utils.MoreIntervals

Utilities around intervals such as enclosing/merging/subtracting.

#### Available predefined interval classes

##### In com.qudini.reactive.utils.intervals.number:

- `ByteInterval`
- `DoubleInterval`
- `FloatInterval`
- `IntInterval`
- `LongInterval`
- `ShortInterval`

##### In com.qudini.reactive.utils.intervals.temporal:

- `LocalDateInterval`
- `LocalTimeInterval`
- `LocalDateTimeInterval`
- `OffsetTimeInterval`
- `OffsetDateTimeInterval`
- `ZonedDateTimeInterval`
- `InstantInterval`

### com.qudini.reactive.utils.MoreJackson

Needs [com.fasterxml.jackson.core:jackson-databind](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-databind).

- `#newObjectMapper()`: returns a new preconfigured `ObjectMapper`

### com.qudini.reactive.utils.MoreRsa

Utilities around RSA and OpenSSH. Needs [org.bouncycastle:bcpkix-jdk15on](https://search.maven.org/artifact/org.bouncycastle/bcpkix-jdk15on).
