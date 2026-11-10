# Nested try / catch in Java

A `try-catch-finally` can be placed inside another `try`, `catch` or `finally` block. When an exception is thrown, the JVM looks for the **innermost** matching `catch` first, then moves outward, and finally up to the calling methods.

## Key Features
* **Innermost matching catch wins:** `10/0` is caught by the inner `catch (ArithmeticException e)`, so the outer `catch` never runs.
* **Execution continues after the inner block:** Once handled, the outer `try` continues normally ("Outside inner try catch").
* **Escalation:** If the inner `catch` did not match, the exception would go to the outer `catch`, then to the caller methods.
* **Outer finally still runs:** Even though the outer `catch` is skipped, the outer `finally` executes.
* **Valid shapes:** `try-catch`, `try-catch-finally`, `try-finally`, and `try-with-resources` (which may have neither `catch` nor `finally`).

## Code Example

An inner `try` divides by zero and handles it locally; the outer `try` then continues, and the outer `finally` runs at the end.

```embed-java
PATH: "vault://src/main/java/com/personal/ExceptionConcepts/NestedTryCatchEx.java"
```

## Output
```
Inside Outer try
Inside inner try
Inside inner catch
Outside inner try catch
Inside outer finally
```

## Day-to-Day Points for Experienced Developers
* **Deep nesting is a refactoring smell:** Extract the inner block into a method with a clear contract (`Optional<Price> tryParsePrice(...)`). It reads better and makes each failure path testable on its own.
* **Catch order is enforced:** Specific types must come before general ones. `catch (Exception e)` followed by `catch (ArithmeticException e)` is a compile error ("exception ArithmeticException has already been caught").
* **Multi-catch for unrelated types with the same handling:** The alternatives must not be subclass/superclass of each other, and `e` is implicitly final.
  ```java
  catch (IOException | SQLException e) { throw new ImportFailedException(file, e); }
  ```
* **The real-world use of nesting - per-item error isolation:** In batch jobs or message consumers, an inner try per item lets one bad record fail without aborting the whole batch; the outer try handles batch-level failures (connection lost).
  ```java
  for (var row : rows) {
      try { importRow(row); }
      catch (InvalidRowException e) { log.warn("Skipping row {}", row.id(), e); failed++; }
  }
  ```
* **Precise rethrow (Java 7+):** `catch (Exception e) { cleanup(); throw e; }` rethrows only the checked types the `try` can actually throw, so the method does not need `throws Exception` as long as `e` is not reassigned.
* **Multiple resources, no nesting needed:** Instead of nested `try/finally` blocks for each resource, declare them all in one try-with-resources header; Java 9+ also accepts an existing effectively-final variable: `try (conn; stmt) { ... }`.
* **Watch for the inner catch hiding bugs:** An inner `catch (Exception e)` that just logs and continues means the outer handler never sees the failure; the caller assumes success. In review, ask "what state is the system in after this catch?"

## Corrections / Notes on the Code
* **Lines ~40-46:** The list of valid combinations misses `try-with-resources`, which can stand alone (`try (var r = ...) { }`).
* **Line ~42:** Multiple `catch` blocks need more than "different exceptions" - they must be ordered from most specific to most general, otherwise the later one is unreachable and fails to compile.
* **Lines ~14-16:** The empty inner `finally {}` is legal but does nothing.

## Related
[[ExceptionExample1]] | [[ExceptionReturnExample]]
