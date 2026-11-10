# StringBuilder in Java

`StringBuilder` (Java 1.5) is a **mutable**, **non-synchronized** sequence of characters. It is the default choice for building strings step by step on a single thread.

## Key Features
* **Mutable buffer:** `append`, `insert`, `delete`, `reverse` and `setLength` change the same object instead of creating new strings.
* **Not thread-safe:** no locking, so it is fast, but one instance must not be mutated by several threads at once.
* **No `equals()` override:** `StringBuilder` inherits `Object.equals()`, which compares references. So `sb1.equals(sb2)` is `false` even though both hold `"hello"`.
* **`toString()` always makes a new `String`:** each call copies the buffer into a new heap object, and the pool is never checked. Even `sb1.toString() == sb1.toString()` is `false` (verified).
* **Interning one side is not enough:** `sb1.toString() == sb2.toString().intern()` is `false`, because the left side is still a new heap object.
* **Interning both sides works:** both return the same pooled `"hello"`, so the result is `true`.
* **Ways to compare content:** `sb1.toString().equals(sb2.toString())`, `sb1.compareTo(sb2) == 0` (`StringBuilder` is `Comparable` since Java 11), or `"hello".contentEquals(sb1)`.

## Code Example

The example compares two builders holding the same text with `equals()`, `==` on `toString()` results, and `intern()`.

```embed-java
PATH: "vault://src/main/java/com/personal/StringConcepts/StringBuilderConcepts.java"
```

## Output

Verified on Java 25:

```
false
false
false
true
```

## Comparison

### String vs StringBuilder vs StringBuffer

| Feature | `String` | `StringBuilder` | `StringBuffer` |
|---|---|---|---|
| Mutability | Immutable | Mutable | Mutable |
| Thread safety | Safe (because immutable) | Not thread-safe | Thread-safe (`synchronized` methods) |
| Speed for repeated edits | Slow (new object for each change) | Fastest | Slower (locking overhead) |
| `equals()` | Compares content | Compares references (inherited from `Object`) | Compares references (inherited from `Object`) |
| Pooling | Literals pooled | Never | Never |
| Since | 1.0 | 1.5 | 1.0 |
| Typical use | Values, map keys, constants | Building strings in loops or methods | Legacy code, or rare cases where threads share one buffer |

## Day-to-Day Points for Experienced Developers

* **Use `StringBuilder` for concatenation inside loops.** `s += x` in a loop creates a new `String` on every pass (O(n^2) copying). A single `a + b + c` expression is fine: since Java 9, `javac` compiles it with `invokedynamic` (`StringConcatFactory`), so hand-written builders there add nothing.
  ```java
  var sb = new StringBuilder();
  for (String item : items) sb.append(item).append(',');
  ```
* **Prefer `String.join`, `StringJoiner` or `Collectors.joining`** when you need separators, so you don't have to strip a trailing comma:
  ```java
  String csv = String.join(",", List.of("a", "b", "c"));           // a,b,c
  String out = names.stream().collect(Collectors.joining(", ", "[", "]"));
  ```
* **Pre-size large builders:** `new StringBuilder(expectedLength)` avoids repeated internal array growth and copying when you know the approximate output size (e.g. building a large CSV or report).
* **Don't concatenate inside `append`:** `sb.append(a + ":" + b)` builds a temporary `String` first; `sb.append(a).append(':').append(b)` does not. This is a common code-review nit in hot paths.
* **Never use a `StringBuilder` as a map key or set element.** It is mutable and uses identity `equals`/`hashCode`, so lookups by content always miss. Convert with `toString()` first.
* **Keep builders local.** A `StringBuilder` stored in a field of a shared bean (Spring singleton, servlet) is a race condition waiting to happen. Create one per method call; it is cheap and the JIT often eliminates the allocation.
* **Reuse with `setLength(0)`** only in tight loops where profiling shows allocation pressure; otherwise a fresh builder is clearer.

## Corrections / Notes on the Code

* **Line 11:** the explanation is correct. A clearer phrasing: "`StringBuilder` does not override `equals()`, so it uses `Object.equals()`, which compares references."

## Related
* [[StringConcepts]]
* [[StringBufferConcepts]]
