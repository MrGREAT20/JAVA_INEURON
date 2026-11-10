# Exceptions in Overridden Methods in Java

When a child class overrides a method, its `throws` clause is restricted: it may declare the same checked exceptions, narrower ones, or none - but never new or broader checked exceptions. This keeps the parent's contract valid wherever a child object is used through a parent reference.

## Key Features
* **Overrides may narrow, never widen:** An overriding method may declare the same checked exceptions, subclasses of them, or none at all. It may **not** add new or broader checked exceptions (e.g. parent `throws IOException`, child `throws Exception` fails with "d() in C cannot override d() in P").
* **Unchecked exceptions are unrestricted:** An override may always declare or throw any `RuntimeException`, even if the parent declares nothing.
* **Compile-time check uses the reference type:** `dm3` is `Demo1 dm3 = new Demo2()`, so `dm3.disp()` is checked against `Demo1.disp()` and needs handling, even though `Demo2.disp()` runs at runtime.
* **Child reference is free:** `dm2.disp()` compiles without `try` because `Demo2.disp()` declares nothing.

## How It Works
The rule follows from upcasting and the Liskov Substitution Principle: code written against `Demo1` only prepares for what `Demo1.disp()` declares. If `Demo2.disp()` could throw a broader checked exception, a caller holding a `Demo1` reference would receive an exception the compiler never made it handle.

## Code Example

`Demo1.disp()` declares `throws Exception`; `Demo2.disp()` overrides it and declares nothing, so only the call through the `Demo2` reference compiles without a `try`.

```embed-java
PATH: "vault://src/main/java/com/personal/ExceptionConcepts/ParentChildOverridenExceptionex.java"
```

## Output
```
Inside Demo2
```

## Day-to-Day Points for Experienced Developers
* **Checked exceptions on interfaces leak into every implementation and caller:** Once an interface method declares `throws IOException`, every caller must handle it - even for an in-memory implementation that never does I/O. Think twice before adding checked exceptions to public interfaces; they are hard to remove later.
* **You cannot add a checked exception in an implementation:** Implementing `Runnable.run()` or `Comparator.compare()` with I/O inside means you must catch and wrap. That is why `Callable.call()` (which `throws Exception`) exists alongside `Runnable`.
* **Frameworks translate to unchecked for this reason:** Spring's `DataAccessException` hierarchy wraps `SQLException` so repository interfaces stay free of checked exceptions; follow the same pattern in your own ports/adapters.
* **Generic exception parameters keep flexibility:** APIs like `Optional.orElseThrow(Supplier<? extends X>)` declare `<X extends Throwable>` so the caller chooses the exception type. Useful in your own functional interfaces:
  ```java
  @FunctionalInterface
  interface ThrowingSupplier<T, X extends Exception> { T get() throws X; }
  ```
* **Mockito enforces the same rule:** `when(repo.find(id)).thenThrow(new IOException())` fails with "Checked exception is invalid for this method!" if `find` doesn't declare it. Throw a declared or unchecked type in tests.
* **Constructors are different:** They are not overridden, so the narrowing rule doesn't apply - but a subclass constructor must declare (it cannot catch) any checked exception thrown by the `super(...)` call it invokes.
* **Declare the narrowest contract on the override if callers use the concrete type:** As `Demo2` shows, removing `throws` on the override lets code holding the concrete type skip pointless try/catch.

## Corrections / Notes on the Code
* **Lines ~11-14:** The rule written is correct, but the example demonstrates a different case (parent throws, child throws nothing - allowed). The general rule: an override cannot declare new or broader checked exceptions than the overridden method.
* **Line ~14:** The `@throws Exception` Javadoc tag on `Demo2.disp()` is misleading, since that method declares and throws nothing.

## Related
[[CompileTimeExceptionEx]] | [[ThrowableEx]]
