# Dividing Work Across Threads in Java

A common multithreading pattern is to **partition** one job into disjoint pieces, give each piece to a thread, then `join()` and combine the results. If the threads never write the same data, no locking is needed.

## Key Features
* **Column-order fill:** A 3 x 4 grid is filled with 1 to 12 in column order, using `value = 3*j + i + 1`.
* **Partitioning rule:** Each thread fills the cells where `(3*j + i) % 3` equals its own number. Since `3*j % 3 == 0`, that reduces to `i % 3`, so each thread fills exactly one row.
* **Disjoint writes:** The threads write to different cells, so there is no race and no lock is needed.
* **Shared reference:** All threads receive the same `int[][]` reference through their constructors.
* **`join()` before reading:** `main` reads `ans` only after all three `join()` calls. `join()` creates a happens-before edge, so `main` is guaranteed to see every write.
* **Deterministic output:** Unlike the other examples, the result does not depend on scheduling.

## Code Example

This example starts three threads that each fill their share of a shared 3 x 4 array, joins them, then prints each row.

```embed-java
PATH: "vault://src/main/java/com/personal/Multithreading/ThreadProblemSolving1.java"
```

## Output

Verified on JDK 25. Thread scheduling varies between runs, but because of the disjoint writes and `join()`, the output is the same every time:

```
t1 1 4 7 10 
t2 2 5 8 11 
t3 3 6 9 12 
```

## Day-to-Day Points for Experienced Developers

* **Return results instead of sharing a mutable array:** With `Callable`, each task returns its piece and the caller assembles them. No shared state, no visibility questions.
  ```java
  try (var ex = Executors.newFixedThreadPool(3)) {
      List<Future<int[]>> rows = new ArrayList<>();
      for (int i = 0; i < 3; i++) { int r = i; rows.add(ex.submit(() -> buildRow(r))); }
      for (Future<int[]> f : rows) System.out.println(Arrays.toString(f.get()));
  }
  ```
* **Parallel streams for simple data-parallel work:** `IntStream.range(0, 3).parallel().mapToObj(this::buildRow).toList()` splits the work on the common `ForkJoinPool`. Good for CPU-bound pure functions; avoid for blocking I/O or tiny workloads where splitting costs more than it saves.
* **Fork/Join for recursive splitting:** `RecursiveTask` divides large problems (sums, sorts over big arrays) until pieces are small, using work-stealing to keep cores busy.
* **Concurrent collections when results are shared:** `HashMap`/`ArrayList` are not thread-safe. Use `ConcurrentHashMap` (with atomic `merge`/`computeIfAbsent`), `CopyOnWriteArrayList` for read-mostly lists, or a `BlockingQueue` for producer-consumer.
  ```java
  counts.merge(word, 1, Integer::sum); // atomic on ConcurrentHashMap
  ```
* **Check-then-act is still a race:** `if (!map.containsKey(k)) map.put(k, v);` is not atomic even on a `ConcurrentHashMap`. Use `putIfAbsent` / `computeIfAbsent`.
* **Partition by contiguous blocks:** Giving each thread a contiguous chunk (one row) is cache-friendly. Threads writing to neighbouring elements of the same array can suffer **false sharing** (same CPU cache line), which slows down hot loops.
* **More threads is not always faster:** For CPU-bound work, more threads than cores only adds context switching. For 12 cells, thread start-up costs far more than the work itself; parallelize only when the work is large enough to measure a gain.

## Corrections / Notes on the Code

* **Wasted iterations (~lines 13-20, 34-41, 56-63):** Each thread loops over all 12 cells and skips 8 of them. Since the condition reduces to `i % 3 == k`, each thread could loop over only its own row. Not a bug, just inefficient.

## Related
[[MultithreadingJoinThreadEx1]] | [[MultiThreadingRunnableEx4]] | [[SynchronizedEx2]]
