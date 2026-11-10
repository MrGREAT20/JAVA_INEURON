# StringBuffer in Java

`StringBuffer` is the original (Java 1.0) mutable character sequence. Its public methods are `synchronized`, so one instance can be shared between threads without corrupting its internal state.

## Key Features
* **Mutable and synchronized:** same API as `StringBuilder`, but every public method takes the object's lock.
* **Reference equality:** `sb1 == sb2` is `false`, because they are two different objects. `equals()` is also inherited from `Object`, so it compares references too.
* **`toString()` gives a new `String` each time**, just like `StringBuilder`, so comparing two results with `==` is `false`.
* **`intern()` on both sides** gives the same pooled reference, so the result is `true`.
* **`StringBuffer == StringBuilder` does not compile:** `javac` reports `incomparable types: StringBuffer and StringBuilder` (verified). Both are `final` classes and neither is a subtype of the other, so a reference of one type can never point to an object of the other.

## Code Example

The example compares two `StringBuffer` objects by reference, by `toString()` result and by interned value.

```embed-java
PATH: "vault://src/main/java/com/personal/StringConcepts/StringBufferConcepts.java"
```

## Output

Verified on Java 25:

```
false
false
true
```

See [[StringBuilderConcepts]] for the full String vs StringBuilder vs StringBuffer comparison table.

## Day-to-Day Points for Experienced Developers

* **Default to `StringBuilder`.** A `StringBuffer` used as a local variable gains nothing from its locks; IDE inspections (IntelliJ "StringBuffer may be StringBuilder", Sonar) flag it. Replacing it is a safe, mechanical refactor when the buffer never escapes the method.
* **Per-method synchronization does not make compound actions atomic.** Each call is thread-safe, but a sequence is not:
  ```java
  if (buf.length() > 0) buf.append(", ");   // another thread can append in between
  buf.append(item);
  ```
  You still need an external `synchronized (buf) { ... }` block, at which point a plain `StringBuilder` under your own lock is equivalent.
* **A shared mutable buffer is usually the wrong design.** For collecting output from several threads, let each thread build its own string and combine results (`ConcurrentLinkedQueue<String>`, a parallel stream with `Collectors.joining`, or futures).
* **Where you still meet it:** legacy code and older APIs. `StringWriter` is backed by a `StringBuffer` (`getBuffer()` returns one), and before Java 9 `Matcher.appendReplacement/appendTail` accepted only `StringBuffer` (Java 9 added `StringBuilder` overloads).
* **Locking cost is often smaller than expected.** HotSpot can remove locks on objects that never escape a thread (escape analysis / lock elision), so benchmarks may show little difference. Choose by design, not micro-benchmarks.

## Corrections / Notes on the Code

* **Line 5:** "slow because of locking" is broadly right, but the JVM can remove uncontended locks on non-escaping objects, so the gap is often smaller than expected. The stronger reason to prefer `StringBuilder` is that a shared, mutated buffer is rarely the right design.
* **Line 7:** `static void main()` with no `String[]` parameter launches only on Java 25+ (JEP 512).

## Related
* [[StringConcepts]]
* [[StringBuilderConcepts]]
