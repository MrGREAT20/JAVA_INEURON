# `synchronized` Blocks in Java

A `synchronized` block locks only the critical lines instead of the whole method. Code outside the block runs freely on all threads, so threads wait less than with a `synchronized` method.

## Key Features
* **`synchronized (lockObject) { ... }`:** Takes the monitor of the given object for that block only, and releases it when the block exits (normally or via an exception).
* **Narrower critical section:** Here the digit loop and the 3 s sleep run in parallel on all threads; only the `A`-`E` loop is exclusive. The whole run took about 3 s, versus about 9 s if `run()` were `synchronized`.
* **Same object, same lock:** `this` is the shared `INueron in` instance, so the three threads exclude each other. A different `this` per thread would give no exclusion.
* **Advisory lock:** The lock only excludes code that also synchronizes on the same object. It does not protect the object's other methods or fields (the comment in the code explains this well).
* **Any object can be the lock:** `this`, a dedicated `private final Object`, or a `Class` object (`synchronized (INueron.class)`).

## Code Example

This example prints digits without a lock, sleeps, then prints `A` to `E` inside `synchronized (this)`, using one shared `Runnable` on three threads.

```embed-java
PATH: "vault://src/main/java/com/personal/Multithreading/SynchronizedEx2.java"
```

## Output

One verified run on JDK 25 (about 3 s). Thread interleaving of the digits varies between runs; the letters always come out as complete groups.

```
0
1
2
0
0
1
3
4
1
2
3
4
2
3
4
A
B
C
D
E
A
B
C
D
E
A
B
C
D
E
```

## Comparison: `synchronized` Method vs `synchronized` Block

| | Method | Block |
| --- | --- | --- |
| Lock object | `this` (or the `Class` object for `static` methods) | Any object you choose |
| Scope | Whole method | Only the critical lines |
| Performance | More waiting | Less waiting |
| Lock visible to outsiders | Yes (anyone can `synchronized(obj)`) | Can be a private lock object |

## Day-to-Day Points for Experienced Developers

* **Use a private lock object:** Outside code can `synchronized (yourObject)` and accidentally block or deadlock you. A private lock cannot be taken by anyone else.
  ```java
  private final Object lock = new Object();
  void update() { synchronized (lock) { /* ... */ } }
  ```
* **Never lock on strings, boxed values or other shared constants:** `synchronized ("LOCK")` or `synchronized (Integer.valueOf(1))` may lock an interned/cached object shared across the whole JVM. Also never lock on a field you reassign; the next thread locks a different object.
* **`volatile` gives visibility, not atomicity:** Without it, a thread can keep looping on a stale flag forever. It is right for simple flags and safe publication, but it does **not** make `count++` safe.
  ```java
  private volatile boolean running = true;
  ```
* **Double-checked locking needs `volatile`:** Without it, another thread may see a partially constructed object. Often a holder class or `enum` singleton is simpler.
  ```java
  private static volatile Config instance;
  static Config get() {
      if (instance == null) synchronized (Config.class) {
          if (instance == null) instance = new Config();
      }
      return instance;
  }
  ```
* **Read-heavy data:** `ReentrantReadWriteLock` lets many readers in at once with a single writer; `StampedLock` adds optimistic reads. Often a `ConcurrentHashMap` or an immutable snapshot swapped via `AtomicReference` is simpler still.
* **Keep the block minimal:** Compute outside, lock only to read/modify the shared state, and never call unknown code (callbacks, listeners, remote calls) while holding a lock; that is how hidden deadlocks appear.

## Corrections / Notes on the Code

* **~Lines 35-36:** `catch (Exception e)` prints `"Some interruption"` for any exception, not only interrupts, and loses the stack trace. Catch `InterruptedException` specifically and restore the interrupt flag.

## Related
[[SynchronizedEx1]] | [[ThreadProblemSolving1]]
