# Lambda Expressions in Java

A **lambda expression** is a concise way to implement the single abstract method of a functional interface: `(params) -> body`. It replaces the boilerplate of an anonymous inner class and is the foundation of the Streams API.

## Key Features
* **Target typing:** A lambda has no type of its own; the functional interface it is assigned to (`IComputer`) gives it one. The type of `n` (`int`) is inferred from `show(int)`.
* **Syntax shortcuts:** A single parameter can drop the parentheses (`n -> ...`); a single-statement body can drop `{}` and the `;` (and `return` for an expression body).
* **No class file per lambda:** Compiling produced no `LambdaExpressionExample$1.class` (verified). Lambdas are linked at runtime via `invokedynamic`; the runtime class is a hidden class such as `V$$Lambda/0x...` (verified).
* **Body runs on call, not on creation:** Creating `com` executes nothing; `com.show(12)` runs the body.

## Code Example

This example implements `IComputer.show(int)` twice with lambdas, once with a block body and once with an expression body.

```embed-java
PATH: "vault://src/main/java/com/personal/FunctionalInterface/LambdaExpressionExample.java"
```

## Output

```
Hiii 12
Hii there 13
```

## Day-to-Day Points for Experienced Developers

* **Prefer method references when they read better:** four forms: static `Integer::parseInt`, bound instance `System.out::println`, unbound instance `String::length`, constructor `ArrayList::new`.
* **Effectively final capture:** A lambda can read a local only if it is never reassigned.
  ```java
  int count = 0;
  list.forEach(x -> count++);   // error: local variables referenced from a lambda expression must be final or effectively final
  ```
  Use a stream reduction (`.count()`, `.sum()`) rather than an `AtomicInteger` workaround.
* **No new scope:** `int n = 0; Runnable r = () -> { int n = 1; };` fails with `variable n is already defined` (verified).
* **`var` needs a target type:** `var f = x -> x;` fails with `cannot infer type for local variable` (verified). You can use `var` *inside* the parameter list to attach annotations: `(@NonNull var x, var y) -> x + y` (Java 11).
* **Unnamed parameters (Java 22+):** `(x, _) -> x` marks an unused parameter explicitly (verified on Java 25).
* **Stack traces:** Exceptions thrown in a lambda show frames like `L.lambda$main$2(L.java:4)` (verified). Keep lambdas short and extract longer logic into a named method so traces and code review stay readable.
* **Don't block or mutate shared state inside lambdas passed to async APIs** (`CompletableFuture`, parallel streams); they run on pool threads, not the caller's.

## Corrections / Notes on the Code

* **~line 9:** The comment shows `show()` with no parameter; the interface method is `show(int n)`, so the anonymous form would be `new IComputer() { @Override public void show(int n) { ... } }`. Also `@Override` is capitalised.
* **~line 19:** `( n)` works but the idiomatic form for a single parameter is `n -> ...`.
* **`static void main()` with no `String[]` (~line 12):** valid only because of Java 25 instance main methods (JEP 512). On Java 21 or earlier this class would not be launchable.

## Related
[[AnonymousInnerClassExampleInterface]] (anonymous class vs lambda table) | [[IFunctionalInterfaceConcept1]] | [[StreamConceptEx1]]
