# `synchronized` Methods in Java

When several threads use the same object, their steps can interleave and corrupt shared state. Marking a method `synchronized` makes it **mutually exclusive**: only one thread at a time can run it on a given object.

## Key Features
* **Monitor lock:** Every object has a built-in lock (monitor). A `synchronized` instance method locks `this`; a `static synchronized` method locks the `Class` object.
* **Shared object required:** All three threads are built from the **same** `BathRoom b`, so they compete for the same lock and take turns. If each thread had its own `new BathRoom()`, every thread would lock a different object and nothing would be excluded.
* **Mutual exclusion, not ordering:** `synchronized` keeps one person's three lines together. It does **not** decide whether MALE, FEMALE or OTHERS goes first. To force an order, use `join()` (as the comments in the code suggest) or other coordination.
* **BLOCKED state:** While one thread holds the lock, the others are in `BLOCKED`. `Thread.sleep()` inside the method **keeps the lock** (the holder is `TIMED_WAITING`).
* **Reentrant:** A thread that already holds the lock can call another `synchronized` method on the same object without deadlocking itself.
* **Visibility:** Releasing a lock publishes all writes made inside it to the next thread that acquires the same lock.
* **Serialized cost:** The whole `run()` is locked, so total time is about 30 s (3 threads x 10 s), no faster than one thread.

## Code Example

This example shares one `BathRoom` (`Runnable` with a `synchronized run()`) between three threads named MALE, FEMALE and OTHERS.

```embed-java
PATH: "vault://src/main/java/com/personal/Multithreading/SynchronizedEx1.java"
```

## Output

One verified run on JDK 25 (took 30 s):

```
MALE has entered the bathroom
MALE is using the bathroom
MALE exited the bathroom
FEMALE has entered the bathroom
FEMALE is using the bathroom
FEMALE exited the bathroom
OTHERS has entered the bathroom
OTHERS is using the bathroom
OTHERS exited the bathroom
```

Each person's three lines always stay together. The order of the people can vary between runs, and in rare runs the first line can show `Thread-0` instead of a name (see Corrections).

## Day-to-Day Points for Experienced Developers

* **Race conditions with `count++`:** `count++` is read, add, write; two threads can both read 5 and both write 6. Either lock, or use atomics.
  ```java
  AtomicInteger count = new AtomicInteger();
  count.incrementAndGet();
  LongAdder hits = new LongAdder(); // better under heavy contention
  hits.increment();
  ```
* **Lock every access, not just writes:** Reading a shared field without the same lock can see stale or half-updated data. A `synchronized` setter with an unsynchronized getter is a common review finding.
* **Deadlock:** Thread A holds lock 1 and waits for lock 2, while B holds lock 2 and waits for lock 1. Avoid it by always taking locks in the **same global order**, using `tryLock(timeout)`, and keeping critical sections small. `jstack` reports it as "Found one Java-level deadlock".
* **`ReentrantLock` when you need more than `synchronized`:** `tryLock` with a timeout, interruptible locking, fairness, and multiple `Condition`s. Always unlock in `finally`.
  ```java
  lock.lock();
  try { /* critical section */ } finally { lock.unlock(); }
  ```
* **Never sleep or do slow I/O while holding a lock:** This example deliberately sleeps 10 s inside the lock; in real code that means every other thread waits. Do the slow work outside, lock only to update shared state.
* **Virtual threads and `synchronized`:** Before Java 24, blocking inside `synchronized` pinned the virtual thread to its carrier, which could starve the scheduler; `ReentrantLock` was the advised workaround. Since JDK 24 (JEP 491) `synchronized` no longer pins in most cases, so on Java 25 this is mostly a concern for older runtimes.
* **Prefer immutability and confinement:** The cheapest lock is none. Immutable objects (`record`, `List.of`) and data owned by a single thread need no synchronization.

## Corrections / Notes on the Code

* **~Lines 58-65 (names set after `start()`):** The threads are named **after** they are started, so a thread could print before `setName` runs and show `Thread-0`/`Thread-1`. Call `setName` before `start()`, or use `new Thread(b, "MALE")`.
* **~Lines 20-28 (expected output):** The MALE, FEMALE, OTHERS order is not guaranteed. `synchronized` gives one-at-a-time access, not first-come-first-served; Java monitors are not fair.
* **~Line 12 ("based on the priority"):** Thread switching is mostly OS time-slicing (preemption). Java priorities are only hints and are often ignored, so they should not be relied on for ordering.
* **Line 3:** `import com.personal.ExceptionConcepts.ThrowableEx;` is unused.

## Related
[[SynchronizedEx2]] | [[MultithreadingJoinThreadEx1]] | [[MultithreadingRunnableEx3]]
