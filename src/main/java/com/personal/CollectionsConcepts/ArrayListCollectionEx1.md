# ArrayList in Java

`ArrayList` is a resizable-array implementation of the `List` interface. It keeps insertion order, allows duplicates and `null`, and gives O(1) access by index, which makes it the default `List` choice in almost all code.

## Key Features
* **Backed by an `Object[]`:** `get(i)` and `set(i, e)` are O(1).
* **Capacity grows lazily:** `new ArrayList()` starts with an empty shared array and allocates capacity 10 on the first `add`. When full it grows by about 1.5x (`newCap = old + old/2`), so `add(e)` at the end is **amortized O(1)**.
* **`add(index, e)` / `remove(index)` are O(N):** every element after `index` is shifted with `System.arraycopy`. `add(0, e)` (front insert) is the worst case.
* **`addAll(c)` makes a shallow copy:** `al2` is a new list, so adding to `al1` later does not change `al2`. The **element objects are shared**: both lists point to the same `Object@...` instance.
* **`List.of(...)` is immutable, the copy is not:** `al3.addAll(List.of(1))` copies the elements into `al3`, which can still be changed.
* **Heterogeneous data, nulls and duplicates are allowed:** the example uses a raw type to show mixed types; real code uses `List<String>`.

## Code Example

The example fills a raw `ArrayList` with mixed types, copies it with `addAll`, shows the copy is independent, and inserts into the middle with `add(index, e)`.

```embed-java
PATH: "vault://src/main/java/com/personal/CollectionsConcepts/ArrayListCollectionEx1.java"
```

## Output
```
[INeuron, B, 4, 25.5, java.lang.Object@2b2fa4f7]
[INeuron, B, 4, 25.5, java.lang.Object@2b2fa4f7, Goti lele]
[1, 4]
[1, 5, 4]
```
(The `Object@...` hash can differ between runs and JVMs.)

## Comparison

| Operation | `ArrayList` | `LinkedList` |
|---|---|---|
| `get(i)` / `set(i)` | O(1) | O(N) |
| `add(e)` at end | Amortized O(1) | O(1) |
| `add(0, e)` / remove first | O(N) | O(1) |
| `add(i, e)` in middle | O(N) shift | O(N) walk |
| Memory | Compact, cache-friendly | About 24 extra bytes per node |
| Use when | Almost always the default | Rarely. Use `ArrayDeque` for queues |

## Day-to-Day Points for Experienced Developers
* **Program to the interface with generics:** `List<String> names = new ArrayList<>();`. Raw types turn compile-time errors into runtime `ClassCastException`s.
* **The `remove(int)` vs `remove(Object)` trap:** on a `List<Integer>`, `list.remove(1)` removes **index 1**. Use `list.remove(Integer.valueOf(1))` to remove the value (verified: `[10, 20, 30]` after `remove(1)` is `[10, 30]`).
* **Know which "list" you have:**
  ```java
  List.of(1, 2);                      // immutable; List.of(null) and even List.of(1).contains(null) -> NPE
  Arrays.asList(1, 2);                // fixed-size: set() works, add() -> UnsupportedOperationException
  Collections.unmodifiableList(list); // read-only *view*: later changes to `list` show through
  List.copyOf(list);                  // true immutable snapshot
  stream.toList();                    // Java 16+, unmodifiable
  ```
* **Pre-size when the size is known:** `new ArrayList<>(10_000)` (or `ensureCapacity`) avoids repeated grow-and-copy; `trimToSize()` releases slack in long-lived lists.
* **`subList` is a view, not a copy:** `list.subList(1, 3).clear()` removes a range in a single shift, but any structural change to the parent afterwards makes the view throw `ConcurrentModificationException` on next use (verified). Copy it (`new ArrayList<>(list.subList(a, b))`) before keeping it around.
* **Sequenced collections (Java 21+):** `getFirst()`, `getLast()`, `removeLast()` and `reversed()` (a view) replace `list.get(list.size() - 1)` idioms.
* **Shallow copies share elements:** `new ArrayList<>(other)` and `addAll` copy references only. Mutating a mutable element shows up in both lists; prefer immutable element types such as `record`s.
* **Not thread-safe:** concurrent `add` can lose elements or throw `ArrayIndexOutOfBoundsException`. Use a lock, `Collections.synchronizedList` (still wrap iteration in `synchronized (list)`), or `CopyOnWriteArrayList` for read-mostly data.

## Corrections / Notes on the Code
1. **~L9 and L33-34:** the list does not copy into an array of `size + 1` on every add. The backing array starts empty, becomes capacity 10 on the first `add`, and grows by about 1.5x when full, which is why `add(e)` is amortized O(1).
2. **~L18:** `add(index, e)` (one element) is O(N). O(N + M) is the cost of `addAll(index, collection)`.
3. **~L19-20:** these are described via `add(0, e)`; note that since Java 21 (`SequencedCollection`) `ArrayList` really has `addFirst()`/`removeFirst()` methods, and they are O(N) as stated.
4. **~L42:** `addAll` creates a separate *list*, but it is a **shallow** copy; the element objects are shared.

## Related
[[LinkedListCollectionEx1]] · [[IteratorEx1]] · [[FailSafeVsFailFastEx1]]
