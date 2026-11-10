# HashSet and LinkedHashSet in Java

`HashSet` stores unique elements in a hash table with no defined order. `LinkedHashSet` is a `HashSet` subclass that additionally threads a doubly linked list through its entries, so it iterates in insertion order.

## Key Features
* **`HashSet` is backed by a `HashMap`:** the element is the key and the value is a shared constant `PRESENT` object. `add` returns `false` for a duplicate.
* **Hashing:** `hashCode()` picks the bucket and `equals()` settles collisions. If a bucket gets large (8 or more entries, with a table of at least 64 buckets), it becomes a red-black tree. `add`, `contains` and `remove` are **O(1) on average**.
* **`HashSet` order is unspecified:** do not rely on it; it can change as the set grows or between JDK versions.
* **`LinkedHashSet` keeps insertion order:** it is backed by a `LinkedHashMap`. Re-adding an existing element does not move it.
* **`addAll` adds each element via `add`:** duplicates in the source are dropped individually.
* **Both allow one `null`.**

## Code Example

The example adds the same values (including a duplicate) to a `HashSet` and a `LinkedHashSet`, plus a `null` to the `LinkedHashSet`, and prints both to compare ordering.

```embed-java
PATH: "vault://src/main/java/com/personal/CollectionsConcepts/LinkedHashSetAndHashSetEx1.java"
```

## Output
```
[50, 100, ascacs, 158]
[100, 50, ascacs, 158, null]
```
(The `HashSet` order comes from the hash layout and is not guaranteed.)

## Comparison

| | `HashSet` | `LinkedHashSet` | `TreeSet` |
|---|---|---|---|
| Backed by | `HashMap` | `LinkedHashMap` | `TreeMap` (red-black tree) |
| Order | None | Insertion | Sorted |
| add/contains/remove | O(1) average | O(1) average | O(log N) |
| `null` | One allowed | One allowed | Not allowed with natural ordering (NPE) |
| Duplicate check | `hashCode` + `equals` | `hashCode` + `equals` | `compareTo` / `compare` |

## Day-to-Day Points for Experienced Developers
* **`equals` and `hashCode` go together:** overriding only `equals` makes equal objects land in different buckets, so duplicates slip in. `record`s generate both correctly:
  ```java
  record Point(int x, int y) {}
  Set<Point> s = new HashSet<>(); s.add(new Point(1, 2));
  s.contains(new Point(1, 2)); // true
  ```
* **Mutable keys break hash collections:** changing a field used in `hashCode()` after insertion strands the element in the wrong bucket; `contains()` returns `false` while `size()` is still 1 (verified). Use immutable keys.
* **Pre-size correctly (Java 19+):** `HashSet.newHashSet(n)` / `HashMap.newHashMap(n)` account for the 0.75 load factor; `new HashSet<>(n)` sets the *table capacity*, which can trigger a resize before `n` elements are added (e.g. `n = 100` gives 128 buckets with a resize threshold of 96).
* **`Set.of(...)` is strict:** duplicate arguments throw `IllegalArgumentException`, `null` throws `NullPointerException` (both verified), and its iteration order is deliberately randomized per JVM run, so tests that assert on `toString()` of a `Set.of` or `HashSet` are flaky.
* **Need deterministic output? Use `LinkedHashSet`:** for de-duplicating while preserving order (`new LinkedHashSet<>(list)`), or for stable JSON/log output. Java 21 adds `getFirst()`, `getLast()`, `reversed()`, and `addFirst(e)`, which *moves* an existing element to the front (verified: `[1, 2, 3].addFirst(3)` gives `[3, 1, 2]`).
* **Thread-safe set:** `ConcurrentHashMap.newKeySet()`; avoid `Collections.synchronizedSet` for hot paths.
* **Poor `hashCode` = slow set:** a constant or low-entropy `hashCode` collapses everything into a few buckets (treeified at best, O(log N), only if elements are `Comparable`). Watch for this when profiling unexpectedly slow `contains` calls.

## Corrections / Notes on the Code
1. **~L11:** the value stored for each key is one shared static `PRESENT` object, not a new `Object()` per element.
2. **~L19:** "Retrieval is O(1)" is the *average* case; `HashSet` has no `get`, only `contains`.
3. **~L47:** the actual output is `[100, 50, ascacs, 158, null]`, because the `null` added on L45 is printed too.

## Related
[[TreeSetEx1]] · [[IteratorEx1]]
