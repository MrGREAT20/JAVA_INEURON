# LinkedHashMap in Java

`LinkedHashMap` is a `HashMap` that also threads a doubly linked list through its entries, giving a predictable iteration order (insertion order by default, or access order if requested) at nearly the same cost as `HashMap`.

## Key Features
* **Extends `HashMap`:** inherits all its methods and O(1) average `get`/`put`.
* **Predictable order:** iterates in insertion order. Re-inserting an existing key does **not** change its position (verified: `put("a",1); put("b",2); put("a",3)` gives `{a=3, b=2}`).
* **Access-order mode:** `new LinkedHashMap<>(16, 0.75f, true)` moves an entry to the end on every `get`/`put`. Combined with `removeEldestEntry` this gives a simple LRU cache.
* **Java 21+ `SequencedMap`:** `firstEntry()`, `lastEntry()`, `pollFirstEntry()`, `putFirst()`, `putLast()`, `reversed()`.

## Code Example

A skeleton that creates an empty raw `LinkedHashMap` and documents its features in comments.

```embed-java
PATH: "vault://src/main/java/com/personal/MapInterfaceConcepts/LinkedHashMapEx1.java"
```

## Output
```
(no output - the program prints nothing)
```

## Comparison: Insertion Order vs Access Order

| Aspect | Insertion order (default) | Access order (`accessOrder = true`) |
|---|---|---|
| Constructor | `new LinkedHashMap<>()` | `new LinkedHashMap<>(16, 0.75f, true)` |
| `get(k)` changes order | No | Yes, moves `k` to the end |
| Re-`put` existing key | Keeps position | Moves to the end |
| Typical use | Stable output (JSON, reports, configs) | LRU caches |

## Day-to-Day Points for Experienced Developers
* **Tiny LRU cache:**
  ```java
  class Lru<K, V> extends LinkedHashMap<K, V> {
      private final int max;
      Lru(int max) { super(16, 0.75f, true); this.max = max; }
      @Override protected boolean removeEldestEntry(Map.Entry<K, V> e) { return size() > max; }
  }
  ```
  Not thread-safe; for production caches prefer Caffeine.
* **In access-order mode `get()` is a structural modification:** calling `get` while iterating throws `ConcurrentModificationException` (verified), and even "read-only" concurrent use needs locking.
* **Order-preserving collectors:** `Collectors.toMap` and `groupingBy` return a `HashMap` by default. When order matters, pass the supplier:
  ```java
  list.stream().collect(Collectors.toMap(Emp::id, e -> e, (a, b) -> a, LinkedHashMap::new));
  ```
  Without a merge function, duplicate keys throw `IllegalStateException`, a common production bug.
* **`Map.of` has unspecified order:** `Map.of(...)` / `Map.copyOf` are immutable, reject `null`s and duplicate keys, and iterate in an order that can change between runs. For a fixed read-only order, wrap a `LinkedHashMap` with `Collections.unmodifiableSequencedMap` (Java 21).
* **Use `SequencedMap` methods** instead of iterator tricks: `map.firstEntry()`, `map.lastEntry()`, `map.reversed().forEach(...)` (verified on JDK 25).
* **Choose by need:** insertion order -> `LinkedHashMap`; sorted keys or range queries (`headMap`, `ceilingKey`) -> `TreeMap`; neither -> `HashMap` (less memory: no per-entry before/after links).

## Corrections / Notes on the Code
* **~line 13:** More precisely, it is a hash table plus a **doubly linked list** of entries (internal `before`/`after` pointers), not a `java.util.LinkedList`.

## Related
[[HashMapEx1]] | [[IdentityHashMapAndHashMapEx2]]
