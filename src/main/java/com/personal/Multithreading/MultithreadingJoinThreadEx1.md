# Waiting for Threads with `join()` in Java

`t.join()` makes the **calling** thread wait until thread `t` has finished. It is the simplest way to make `main` (or any thread) wait for worker threads before continuing.

## Key Features
* **Caller waits, not the target:** `w10.join()` blocks `main` (state `WAITING`) until `w10` is `TERMINATED`.
* **Without `join()`:** The "Main thread execution completed" line would most likely print first, because `main` does not wait for the workers.
* **With `join()`:** The final line always prints last, after all three workers.
* **Join order does not matter for total time:** The workers are already running in parallel. Joining `w10` then `w11` then `w12` still takes about 6 s in total.
* **Happens-before:** Everything a thread did before it finished is guaranteed to be visible to the thread that returns from `join()` on it. This is why reading shared results after `join()` is safe (see [[ThreadProblemSolving1]]).
* **`isAlive()`:** It prints `true` because `main` is checking itself while still running.
* **Timed join:** `t.join(5000)` (or `t.join(Duration.ofSeconds(5))` in Java 19+) waits at most that long. Check `t.isAlive()` afterwards to see whether it actually finished.

## Code Example

This example starts three worker threads, joins each one, and only then prints a final message from `main`.

```embed-java
PATH: "vault://src/main/java/com/personal/Multithreading/MultithreadingJoinThreadEx1.java"
```

## Output

One verified run on JDK 25 (about 6 s). The worker interleaving varies between runs, but the last line is always last.

```
Banking Started Successfully..........
Calculations Started Successfully..........
Printing Started Successfully..........
Calculations .....
Banking .....
Printing .....
Banking .....
Calculations .....
Banking .....
Printing .....
Banking .....
Banking .....
Printing .....
Printing Completed Successfully..........
-------------------------------
Calculations .....
Banking .....
Calculations Completed Successfully..........
-------------------------------
Banking Completed Successfully..........
-------------------------------
Main thread execution completed main is true
```

## Comparison: `sleep()` vs `wait()` vs `join()`

| | `Thread.sleep(ms)` | `obj.wait()` | `t.join()` |
| --- | --- | --- | --- |
| Defined in | `Thread` (static) | `Object` | `Thread` |
| Releases held locks? | **No** | **Yes** (only that object's monitor) | Releases only the lock on `t` (it uses `t.wait()` internally) |
| Must hold a lock? | No | Yes, or it throws `IllegalMonitorStateException` | No |
| Wakes up when | Time runs out / interrupted | `notify()`/`notifyAll()` / interrupted / spurious wakeup | `t` terminates / interrupted |
| Typical use | Pause or retry delay | Condition signalling (old style) | Wait for a thread to finish |

## Day-to-Day Points for Experienced Developers

* **With executors, wait on futures, not threads:** You do not own pool threads, so you cannot `join()` them. Use `invokeAll`, `Future.get()`, or try-with-resources on the executor.
  ```java
  List<Future<String>> results = executor.invokeAll(List.of(task1, task2, task3));
  for (Future<String> f : results) System.out.println(f.get());
  ```
* **`CompletableFuture.allOf` for async fan-out/fan-in:**
  ```java
  CompletableFuture<Void> all = CompletableFuture.allOf(cf1, cf2, cf3);
  all.join(); // CompletableFuture.join() throws unchecked CompletionException
  ```
* **`CountDownLatch` when you wait for events, not thread death:** Workers call `latch.countDown()` when ready; the waiter calls `latch.await(timeout, unit)`. Useful in tests and startup sequences.
* **Never wait forever in production:** Prefer `join(timeout)`, `get(timeout, unit)`, `await(timeout, unit)`. An un-timed wait on a stuck dependency hangs the request thread; a thread dump then shows it in `WAITING` on `Thread.join` or `FutureTask.get`.
* **Never use `Thread.sleep()` to wait for another thread:** It is either too long (slow) or too short (flaky). Use `join()`, a latch, or a future.
* **Structured concurrency (preview in Java 25):** `StructuredTaskScope` treats a group of subtasks as one unit: fork, then join all, with automatic cancellation of siblings on failure. Worth watching for request fan-out code; it requires `--enable-preview` today.
* **`join()` is interruptible:** It throws `InterruptedException`. If you cannot propagate it, restore the interrupt flag rather than swallowing it.

## Corrections / Notes on the Code

* **Copy-paste bug in `Work12` (~line 64):** The "Calculations" thread also prints `"Banking ....."`, so "Banking ....." appears 6 times instead of 3.
* **~Line 3 etc.:** `extends Thread implements Runnable` is redundant (see [[MultiThreadingRunnableEx4]]).

## Related
[[MultithreadingEx2]] | [[ThreadProblemSolving1]] | [[SynchronizedEx1]]
