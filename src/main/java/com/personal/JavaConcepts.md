# Java Concepts: Index

Entry point for the notes in this project. Every `.java` file has a matching note next to it, with the source embedded, the verified output, "Day-to-Day Points for Experienced Developers" and any corrections to the code comments.

## Suggested Study Order

```mermaid
flowchart LR
    A["Class & Object Basics"] --> B["Strings"]
    B --> C["Exceptions"]
    C --> D["Generics"]
    D --> E["Collections"]
    E --> F["Maps"]
    F --> G["Comparable / Comparator"]
    G --> H["Functional Interfaces & Lambdas"]
    H --> I["Streams"]
    I --> J["Multithreading"]
```

## 1. Class & Object Basics
* [[JdkJreJvm]]: JDK vs JRE vs JVM architecture
* [[SameMethodMultiClass]]: upcasting and method overriding
* [[LocalVsInstanceVariables]]: default values vs "might not have been initialized"
* [[Main]]: `equals()` / `hashCode()` in a `HashSet`

## 2. Strings
* [[StringConcepts]]: String pool, constants and `intern()`
* [[StringBuilderConcepts]]: mutable, fast, not thread-safe
* [[StringBufferConcepts]]: mutable, synchronized

## 3. Exceptions
* [[ExceptionExample1]]: try/catch/finally and stack unwinding
* [[CompileTimeExceptionEx]]: checked exceptions and `throws`
* [[ExceptionReturnExample]]: `return` with `finally`
* [[NestedTryCatchEx]]: nested and combined try/catch/finally
* [[ParentChildOverridenExceptionex]]: exception rules when overriding
* [[ThrowableEx]]: `Throwable`, `throw` and `throws`

## 4. Generics
* [[GenericEx1]]: type safety with generics

## 5. Collections
* [[ArrayListCollectionEx1]]: `ArrayList`
* [[LinkedListCollectionEx1]]: `LinkedList`
* [[ArrayDequeEx1]]: `ArrayDeque`
* [[PriorityQueueEx1]]: `PriorityQueue`
* [[LinkedHashSetAndHashSetEx1]]: `HashSet` vs `LinkedHashSet`
* [[TreeSetEx1]]: `TreeSet`
* [[IteratorEx1]]: `Iterator`
* [[FailSafeVsFailFastEx1]]: fail-fast vs fail-safe iterators

## 6. Maps
* [[HashMapEx1]]: `HashMap`
* [[LinkedHashMapEx1]]: `LinkedHashMap`
* [[IdentityHashMapAndHashMapEx2]]: `IdentityHashMap` vs `HashMap`

## 7. Comparable / Comparator
* [[ComparableInterfaceEx1]]: natural ordering
* [[ComparatorEx1]]: custom ordering

## 8. Functional Interfaces & Lambdas
* [[IInterfaceConcepts1]]: interface members (default, static, constants)
* [[IFunctionalInterfaceConcept1]]: functional interfaces
* [[AnonymousInnerClassExampleInterface]]: anonymous inner classes
* [[LambdaExpressionExample]]: lambda expressions

## 9. Streams
* [[StreamConceptEx1]]: filter and null-safe sort

## 10. Multithreading
* [[MultithreadingEx1]]: the main thread and thread lifecycle
* [[MultithreadingEx2]]: extending `Thread`
* [[MultithreadingRunnableEx3]]: implementing `Runnable`
* [[MultiThreadingRunnableEx4]]: extending `Thread` and implementing `Runnable` together
* [[MultithreadingJoinThreadEx1]]: `join()`
* [[SynchronizedEx1]]: `synchronized` methods
* [[SynchronizedEx2]]: `synchronized` blocks
* [[ThreadProblemSolving1]]: dividing work across threads

---

## Cross-Cutting Day-to-Day Points
* **Program to interfaces:** `List<String> names = new ArrayList<>();` lets you change the implementation without changing callers.
* **Prefer immutability:** use `final` fields, `record`s, and `List.of(...)` / `Map.of(...)`. Immutable objects are thread-safe and easier to reason about.
* **Handle `null` deliberately:** use `Objects.requireNonNull(arg, "arg")` at boundaries, and `Optional` for return values that may be absent (not for fields or parameters).
* **Use a logger, not `System.out`:** SLF4J or `java.util.logging` gives you log levels and configurable output in production.
* **Turn "my answer is X" comments into tests:** add JUnit 5 to `pom.xml` and write `assertEquals(1, set.size());`. A wrong expectation then fails immediately.
* **Read stack traces from the top:** the first `at ...` line is where the exception was thrown. `Caused by:` shows the original failure.
* **Learn the debugger:** breakpoints, step into/over and watches answer most "what is happening here?" questions faster than `println`.
