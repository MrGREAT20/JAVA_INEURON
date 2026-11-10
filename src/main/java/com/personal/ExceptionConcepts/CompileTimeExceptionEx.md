# Checked Exceptions and the throws Clause in Java

A **checked exception** is one the compiler forces you to deal with: any method that can let it escape must either catch it or declare it with `throws`. This is the "handle or declare" rule. Unchecked exceptions (`RuntimeException`, `Error` and their subclasses) are exempt.

## Key Features
* **Checked = compiler-enforced:** `asc()` declares `throws Exception` (a checked type), so every caller must catch it or re-declare it.
* **Declaring vs actually throwing:** `asc()` declares `Exception`, but what actually happens at runtime is an `ArithmeticException` from `x / y`. A `throws` clause is a contract about what *may* escape, not what will.
* **Unchecked in `throws` is optional:** `throws ArithmeticException` would be documentation only; the compiler would not force callers to handle it.
* **Propagation via `throws`:** Because `main` also declares `throws Exception`, the code compiles and the exception escapes to the JVM, which prints the stack trace.

## Code Example

`Example1.asc()` divides by zero and declares `throws Exception`; `main` calls it and also declares `throws Exception`, so the exception propagates out of the program.

```embed-java
PATH: "vault://src/main/java/com/personal/ExceptionConcepts/CompileTimeExceptionEx.java"
```

## Output

The program compiles and throws at runtime:
```
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at com.personal.ExceptionConcepts.Example1.asc(CompileTimeExceptionEx.java:7)
	at com.personal.ExceptionConcepts.CompileTimeExceptionEx.main(CompileTimeExceptionEx.java:26)
```

## Comparison: Checked vs Unchecked

| Aspect | Checked | Unchecked |
|---|---|---|
| Hierarchy | `Throwable`, `Exception` and subclasses that are not `RuntimeException` | `RuntimeException`, `Error` and their subclasses |
| Compiler enforces handling | Yes ("handle or declare") | No |
| Typical cause | External, possibly recoverable conditions (I/O, network, DB) | Programming bugs (null, bad index, bad argument) |
| Examples | `IOException`, `SQLException`, `InterruptedException` | `NullPointerException`, `ArithmeticException`, `IllegalStateException` |

## Day-to-Day Points for Experienced Developers
* **Avoid `throws Exception` in real signatures:** It forces every caller to catch `Exception`, which then also swallows unrelated `RuntimeException`s. Declare the specific checked types (`throws IOException`) or wrap them.
* **Checked vs unchecked for custom exceptions:** Most modern code (and Spring) prefers unchecked for business/validation errors; use checked only when the caller can realistically recover. Always add a cause-taking constructor:
  ```java
  public class OrderNotFoundException extends RuntimeException {
      public OrderNotFoundException(String msg) { super(msg); }
      public OrderNotFoundException(String msg, Throwable cause) { super(msg, cause); }
  }
  ```
* **Wrap, but keep the cause:** `throw new ServiceException("Failed to load user " + id, e);` - passing only `e.getMessage()` loses the root stack trace, one of the most common production debugging pains.
* **Spring `@Transactional` rollback gotcha:** By default Spring rolls back only on unchecked exceptions and `Error`. A checked exception thrown from a transactional method **commits** the transaction unless you add `@Transactional(rollbackFor = Exception.class)`.
* **Lambdas and streams can't throw checked exceptions:** `Function`, `Consumer` etc. declare no `throws`, so catch inside the lambda and wrap:
  ```java
  paths.stream().map(p -> {
      try { return Files.readString(p); }
      catch (IOException e) { throw new UncheckedIOException(e); }
  }).toList();
  ```
* **`InterruptedException` needs special care:** If you catch it and don't rethrow, restore the flag with `Thread.currentThread().interrupt();` so executors and shutdown logic still see the interruption.
* **The compiler also rejects impossible catches:** `catch (IOException e)` around code that cannot throw it is a compile error ("exception IOException is never thrown in body of corresponding try statement"). `catch (Exception e)` is always allowed because it also covers unchecked exceptions.

## Corrections / Notes on the Code
* **Line ~26:** The comment "this causes compiler error" does not apply to the code as written, because `main` already declares `throws Exception`. The file compiles and throws `ArithmeticException` at runtime (verified). The compile error ("unreported exception Exception; must be caught or declared to be thrown") would only occur if `main` had neither `throws` nor `try/catch`.

## Related
[[ParentChildOverridenExceptionex]] | [[ThrowableEx]] | [[ExceptionExample1]]
