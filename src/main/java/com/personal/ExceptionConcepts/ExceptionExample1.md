# try / catch / finally and Stack Unwinding in Java

An **exception** is an object that disrupts the normal flow of a method. A `try` block marks code that may fail, `catch` handles a matching exception type, and `finally` holds cleanup code that runs no matter how the `try` ends. If nothing catches the exception, it **unwinds the call stack** until it escapes the thread.

## Key Features
* **Catching by supertype:** `catch (RuntimeException e)` catches `ArithmeticException` because it is a subclass (`IS-A` relationship).
* **finally always runs:** Whether the `try` succeeds, the `catch` handles the exception, or nothing catches it, the `finally` block executes.
* **try-finally without catch:** Legal. The `finally` runs, then the exception keeps propagating up the stack.
* **Stack unwinding:** An uncaught exception pops frames until a matching `catch` is found. If it escapes `main`, the default handler prints the stack trace and that thread terminates.
* **Order of output:** The `finally` output is printed *before* the JVM's stack trace, because `finally` runs while the exception is still unwinding.

## Code Example

The program reads two ints `a` and `b`, then runs two `try` blocks: one with a `catch` (dividing by `b+1`) and one with only a `finally` (dividing by `b`).

```embed-java
PATH: "vault://src/main/java/com/personal/ExceptionConcepts/ExceptionExample1.java"
```

## Output

Input `3 0` (first division is `3/1`, second is `3/0` - uncaught, the program throws):
```
Pakda pakdi start..
Feku error ki nhi hahahaha....2
Finally Pakda
RulaDiya na bichare ko, bandh hogaya
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at com.personal.ExceptionConcepts.ExceptionExample1.main(ExceptionExample1.java:25)
```

Input `3 -1` (first division is `3/0` - caught; second is `3/-1`):
```
Pakda pakdi start..
Pakda gaya
Finally Pakda
Cant catch me -3
RulaDiya na bichare ko, bandh hogaya
```

## Day-to-Day Points for Experienced Developers
* **Read stack traces bottom-up through `Caused by:`:** The root cause is usually the *last* `Caused by:` block, and the first frame from your own package is where to start. `... 12 more` lines are frames shared with the enclosing trace, not lost information.
* **Background threads die quietly:** An uncaught exception in a plain `Thread` only prints to stderr. Install a handler so it reaches your logs:
  ```java
  Thread.setDefaultUncaughtExceptionHandler((t, e) -> log.error("Uncaught in {}", t.getName(), e));
  ```
* **`ExecutorService.submit()` hides exceptions:** The exception is stored in the `Future` and only surfaces on `future.get()` (wrapped in `ExecutionException`). If nobody calls `get()`, the failure is invisible. Use `execute()` or check the futures; with `CompletableFuture` attach `exceptionally` / `whenComplete`.
* **Spring web requests:** An uncaught exception in a controller does not kill the app or the worker thread; `DispatcherServlet` and its exception resolvers turn it into an error response (500 by default) and the thread returns to the pool. Centralise the mapping with `@RestControllerAdvice` + `@ExceptionHandler` (and `ProblemDetail` in Spring 6+) instead of try/catch in every controller.
* **Don't use exceptions for expected control flow:** Here `b == -1` is a known bad input; validating up front is cheaper and clearer than catching `ArithmeticException`. Filling in a stack trace costs real CPU in hot paths.
* **Catch the narrowest type you can handle:** `catch (RuntimeException e)` here would also hide an unrelated `NullPointerException` introduced by a future change. In code review, broad catches deep in business logic are a red flag; they belong at boundaries (request handler, message listener, main loop).
* **Log once, with the exception object:** `log.error("Division failed for a={}, b={}", a, b, e);` - pass `e` as the last argument so SLF4J records the stack trace. Avoid `e.printStackTrace()` (goes to stderr, not your log pipeline) and avoid log-and-rethrow at every layer, which duplicates stack traces.

## Corrections / Notes on the Code
* **Line ~19 (Spring Boot comment):** Mostly right, but precisely: Spring catches the exception for you, so the request gets an error response and the pooled worker thread is reused rather than "killed". The overall point - one failing request does not stop the application - is correct.

## Related
[[NestedTryCatchEx]] | [[ExceptionReturnExample]] | [[CompileTimeExceptionEx]] | [[ThrowableEx]]
