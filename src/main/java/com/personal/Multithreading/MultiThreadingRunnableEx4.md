# Extending `Thread` and Implementing `Runnable` Together in Java

A class can declare `extends Thread implements Runnable`. It compiles and runs exactly like a plain `extends Thread` class, because `Thread` already implements `Runnable`.

## Key Features
* **Redundant declaration:** `Thread implements Runnable`, so `implements Runnable` on a `Thread` subclass adds nothing. The behaviour is identical to [[MultithreadingEx2]].
* **Still a `Thread`:** `Work7`, `Work8`, `Work9` are thread objects. `start()` runs their overridden `run()` on new threads.
* **Could be misused as a `Runnable`:** Such an object could also be passed to `new Thread(w7)`, wasting a whole thread object just to act as a task.
* **Non-deterministic interleaving:** Three threads sleeping and printing at the same time produce a different order on each run.

## Code Example

This example starts three classes that both extend `Thread` and implement `Runnable`, then declares a few unused variables in `main`.

```embed-java
PATH: "vault://src/main/java/com/personal/Multithreading/MultiThreadingRunnableEx4.java"
```

## Output

One verified run on JDK 25 (about 6 s). Thread interleaving varies between runs.

```
Printing Started Successfully..........
Calculations Started Successfully..........
Banking Started Successfully..........
Calculations .....
Banking .....
Printing .....
Banking .....
Calculations .....
Banking .....
Printing .....
Banking .....
Calculations .....
Banking .....
Calculations Completed Successfully..........
-------------------------------
Printing .....
Printing Completed Successfully..........
-------------------------------
Banking .....
Banking Completed Successfully..........
-------------------------------
```

Note that "Printing Started" came first here even though `w7` (Banking) was started first: `start()` order does not guarantee execution order.

## Day-to-Day Points for Experienced Developers

* **Code-review red flags in threading code:** `extends Thread` for a plain task; `new Thread(...)` inside a request handler; `catch (Exception e) { e.printStackTrace(); }`; `Thread.sleep()` used to "wait for" another thread; shared mutable fields without `synchronized`/`volatile`/atomics.
* **Unbounded pools:** `Executors.newCachedThreadPool()` creates a new thread whenever none is idle; under load it can create thousands of threads and run out of memory. Prefer a bounded `ThreadPoolExecutor` or virtual threads.
* **Bounded queue + rejection policy:** `newFixedThreadPool` uses an unbounded queue, so a slow consumer turns into a memory leak. Build a `ThreadPoolExecutor` with an `ArrayBlockingQueue` and a `RejectedExecutionHandler` (e.g. `CallerRunsPolicy`) for back-pressure.
* **Exceptions in `submit()` disappear:** An exception thrown by a task passed to `submit()` is captured in the `Future` and is never printed unless someone calls `get()`. Use `execute()` for fire-and-forget tasks (goes to the uncaught exception handler) or log inside the task.
* **`ThreadLocal` leaks in pools:** Pool threads live forever, so a `ThreadLocal` that is set and never `remove()`d keeps its value (and memory) for the next task. Always clear it in `finally`. With Java 25, `ScopedValue` is a safer alternative.
* **Daemon threads for background helpers only:** The JVM does not wait for daemon threads at exit, and their `finally` blocks may never run. Never use them for work that must complete (writing files, sending messages).

## Corrections / Notes on the Code

* **Redundant `implements Runnable` (~lines 6, 33, 58):** Harmless, but misleading; it suggests these are two different ways combined, while it is just `extends Thread`.
* **Unused variables (~lines 93-95):** `total`, `n` and `m` are never used. They look like groundwork for [[ThreadProblemSolving1]] (a 3 x 4 grid).
* **Copy-paste bug in `Work9` (~line 67):** The "Calculations" thread also prints `"Banking ....."`, so "Banking ....." appears 6 times instead of 3.

## Related
[[MultithreadingEx2]] | [[MultithreadingRunnableEx3]] | [[ThreadProblemSolving1]]
