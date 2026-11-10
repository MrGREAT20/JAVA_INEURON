# ArrayDeque in Java

`ArrayDeque` is a resizable-array implementation of the `Deque` (Double Ended Queue) interface.

## Key Features
* **No Random Access:** You can only operate on the front and back of the deque.
* **No Indexing:** Index-based access (like `get(i)`) is not allowed.
* **Heterogeneous Data:** It can store any object type (though using Generics like `ArrayDeque<String>` is standard practice).
* **Insertion Order:** The order in which elements are added is strictly maintained.
* **No Nulls:** Attempting to insert `null` values will throw a `NullPointerException`.

## Code Example

Here is a basic example demonstrating sequential insertions and appending an entire `List` to the end of the deque using `addAll()`.

```embed-java
PATH: "vault://src/main/java/com/personal/CollectionsConcepts/ArrayDequeEx1.java"
```

## Output
```
[Pralay, 10, Mumbai, 1, 2]
```

## Day-to-Day Points for Experienced Developers
* **Use it instead of `Stack` and `LinkedList`:** `Stack` is a synchronized legacy class extending `Vector`; `LinkedList` allocates a node per element. `ArrayDeque` is the JDK's recommended stack and queue.
  ```java
  Deque<Integer> stack = new ArrayDeque<>(); stack.push(1); stack.pop();
  Deque<Integer> queue = new ArrayDeque<>(); queue.offer(1); queue.poll();
  ```
* **Stack iteration order differs from `Stack`:** after `push(1), push(2), push(3)`, an `ArrayDeque` prints `[3, 2, 1]` (top first) while `java.util.Stack` prints `[1, 2, 3]` (verified). Matters when migrating code that iterates or logs a stack.
* **Pick the right failure mode:** `poll()`/`peek()` return `null` on empty; `pop()`/`remove()`/`element()` throw `NoSuchElementException` (verified). Since `null` elements are forbidden, a `null` from `poll()` unambiguously means "empty", which makes `while ((x = q.poll()) != null)` a safe idiom.
* **Declare as `Deque<E>` and stick to one end-convention:** mixing `push` (front) with `add` (back) on the same deque is a frequent source of BFS/DFS bugs. For BFS use `offer`/`poll`; for DFS use `push`/`pop`.
* **Not thread-safe:** use `ConcurrentLinkedDeque` (non-blocking) or `LinkedBlockingDeque` (blocking, optionally bounded) for shared access. Its iterators are fail-fast like other `java.util` collections.
* **Capacity:** it grows automatically (doubling while small), so it is unbounded; pass an expected size (`new ArrayDeque<>(1024)`) for large hot-path buffers. For a fixed-size sliding window, check `size()` and `pollFirst()` yourself.
* **Java 21+:** `ArrayDeque` is a `SequencedCollection`, so `getFirst()`, `getLast()` and `reversed()` work uniformly with `List` and `LinkedHashSet`.

## Related
[[LinkedListCollectionEx1]] · [[PriorityQueueEx1]] · [[IteratorEx1]]