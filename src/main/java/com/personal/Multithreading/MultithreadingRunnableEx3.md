# Creating Threads by Implementing `Runnable` in Java

`Runnable` represents a **task**, not a thread. You put the work in `run()` and pass the task to a `Thread` (or an executor), which runs it on a new thread. This is the preferred way to define thread work.

## Key Features
* **`Runnable` is only a task:** `new Work4()` does not create a thread. A thread exists only once you wrap the task in `new Thread(w4)` (state `NEW`) and call `start()`.
* **Constructor injection:** `new Thread(Runnable target)` stores the task; `Thread.run()` simply calls `target.run()`.
* **Functional interface:** `Runnable` has one abstract method, `void run()`, so a lambda or method reference works: `new Thread(() -> System.out.println("hi")).start();`
* **Free inheritance slot:** The task class can still extend another class.
* **Reusable task:** The same `Runnable` can be given to many threads or submitted to an `ExecutorService`.

## Code Example

This example defines three `Runnable` tasks (`Work4`, `Work5`, `Work6`), wraps each in a `Thread`, and starts all three.

```embed-java
PATH: "vault://src/main/java/com/personal/Multithreading/MultithreadingRunnableEx3.java"
```

## Output

One verified run on JDK 25 (about 6 s). Thread interleaving varies between runs.

```
Banking Started Successfully..........
Printing Started Successfully..........
Calculations Started Successfully..........
Printing .....
Banking .....
Calculations .....
Banking .....
Printing .....
Banking .....
Calculations .....
Banking .....
Banking .....
Printing .....
Printing Completed Successfully..........
-------------------------------
Banking Completed Successfully..........
-------------------------------
Calculations .....
Banking .....
Calculations Completed Successfully..........
-------------------------------
```

## Comparison: `extends Thread` vs `implements Runnable`

| Aspect | `extends Thread` | `implements Runnable` |
| --- | --- | --- |
| Inheritance | Uses up your only superclass | Can still extend another class |
| Design | Task and thread are mixed together | Task is separate from the thread |
| Lambda support | No | Yes (`() -> {...}`) |
| Works with `ExecutorService` | Awkward | Yes |
| Sharing one task between threads | Each thread is its own object | One `Runnable` can be passed to many threads (as in [[SynchronizedEx1]]) |

## Day-to-Day Points for Experienced Developers

* **`Callable<T>` when you need a result:** `Runnable.run()` returns `void` and cannot throw checked exceptions. `Callable.call()` can do both. `future.get()` blocks, and a task's exception comes back wrapped in `ExecutionException`.
  ```java
  Future<Integer> f = executor.submit(() -> 6 * 7);
  int answer = f.get(); // 42
  ```
* **`CompletableFuture` for async pipelines:** Chain steps without blocking, then wait once at the end. Pass your own executor; the default is the shared `ForkJoinPool.commonPool()`, which is a bad place for blocking I/O.
  ```java
  CompletableFuture.supplyAsync(() -> fetchUser(id), ioExecutor)
      .thenApply(User::email)
      .exceptionally(e -> "unknown")
      .thenAccept(System.out::println);
  ```
* **Virtual threads (Java 21+) take a `Runnable` too:** Very cheap threads for blocking work (HTTP, JDBC). Create one per task; do not pool them.
  ```java
  try (var ex = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 10_000; i++) ex.submit(() -> callApi());
  }
  Thread.ofVirtual().start(() -> System.out.println("hi"));
  ```
* **Prefer lambdas over tiny named classes:** Three near-identical `WorkN` classes are better written as one method plus `() -> doWork("Banking")`. Less duplication also prevents bugs like the copy-paste one below.
* **A shared `Runnable` means shared fields:** If one `Runnable` instance is passed to several threads, its instance fields are shared state and need synchronization. A fresh instance per thread avoids that.
* **Wrap tasks for cross-cutting concerns:** Because tasks are plain `Runnable`s, you can decorate them (timing, MDC log context propagation, error logging) before submitting: `executor.submit(withMdc(task))`.

## Corrections / Notes on the Code

* **~Line 99 ("multiple inheritance"):** Java still has no multiple inheritance of classes. More precisely, implementing `Runnable` leaves your single `extends` slot free, keeps the task separate from the thread, and lets the same task run in an `ExecutorService`.
* **Copy-paste bug in `Work6` (~line 64):** The "Calculations" task also prints `"Banking ....."`, which is why "Banking ....." appears 6 times instead of 3.

## Related
[[MultithreadingEx2]] | [[MultiThreadingRunnableEx4]] | [[SynchronizedEx1]]
