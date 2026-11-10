# Interface Members in Java

An **interface** defines a contract that classes implement. Since Java 8 an interface can contain more than abstract methods: constants, `default` methods with a body, and `static` utility methods (plus `private` helpers since Java 9).

## Key Features
* **Fields are constants:** Every interface field is implicitly `public static final`, so the modifiers on `variabl1` are redundant (but legal). The field must be initialised; `int X;` in an interface is a compile error (`= expected`, verified).
* **`default` methods (Java 8+):** Provide a body that implementing classes inherit and may override. They let interfaces evolve without breaking existing implementations.
* **`static` methods (Java 8+):** Belong to the interface itself and are called as `IInterfaceConcepts1.testing()`. They are **not** inherited: `ImplClass.testing()` or `obj.testing()` fails with `cannot find symbol` (verified).
* **Abstract methods:** `bark()` and `meow()` are implicitly `public abstract`. Because there are two, this is **not** a functional interface and cannot be the target of a lambda.
* **`private` methods (Java 9+, not shown):** Share code between `default` methods without exposing it to implementers.
* **No state, no constructors:** An interface cannot hold instance fields or define constructors.

## Code Example

This file declares an interface with a constant, a `default` method, a `static` method and two abstract methods.

```embed-java
PATH: "vault://src/main/java/com/personal/FunctionalInterface/IInterfaceConcepts1.java"
```

## Output

The file has no `main` method, so it produces no output on its own. It compiles cleanly with `javac` (verified).

## Comparison: Interface vs Abstract Class

| Aspect | Interface | Abstract class |
|---|---|---|
| Instance fields | No (only `public static final` constants) | Yes |
| Constructors | No | Yes |
| Multiple inheritance | A class can implement many | A class can extend only one |
| Method bodies | `default`, `static`, `private` methods | Any kind |
| Member visibility | Abstract and `default` methods are always `public` | Any access modifier |
| Typical use | Capability or contract (`Comparable`, `Runnable`) | Shared base state and template logic |

## Day-to-Day Points for Experienced Developers

* **Default method diamond:** If a class implements two interfaces with the same `default` method, it must override it and can delegate explicitly:
  ```java
  class C implements A, B {
      public String hi() { return A.super.hi() + "+" + B.super.hi(); }   // A1+B1
  }
  ```
* **Class wins over interface:** A concrete method inherited from a superclass always beats a `default` method from an interface. A `default` method can never override `equals`, `hashCode` or `toString` (compile error).
* **Avoid the "constant interface" anti-pattern:** Implementing an interface just to inherit constants leaks them into your public API. Use a `final` class with a private constructor, or an `enum`.
* **`static final` does not mean immutable:** `List<String> NAMES = new ArrayList<>();` in an interface is a shared, globally mutable list. Use `List.of(...)`.
* **Adding a `default` method is not always safe:** It is binary compatible, but it can clash with an existing method of the same signature in an implementing class or another interface, breaking compilation downstream.
* **Sealed interfaces (Java 17+):** `sealed interface Shape permits Circle, Square {}` restricts implementers and enables exhaustive `switch` pattern matching without a `default` branch.
* **Use `private` methods for shared default logic** rather than exposing helper `default` methods that implementers could accidentally override.

## Corrections / Notes on the Code

* **~line 10:** The reason is right in spirit. Precisely, `forEach` was added to `Iterable` and `stream()` to `Collection` as `default` methods in Java 8, so that all existing third-party implementations would keep compiling.
* **~line 16:** `return ;` at the end of a `void` method is redundant.
* **~line 6:** `public static final` is implied for interface fields; most style guides omit it.

## Related
[[IFunctionalInterfaceConcept1]] | [[AnonymousInnerClassExampleInterface]] | [[LambdaExpressionExample]]
