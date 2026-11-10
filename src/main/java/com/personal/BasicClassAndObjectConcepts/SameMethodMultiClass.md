# Upcasting and Method Overriding in Java

**Upcasting** is the practice of casting a subclass reference to a superclass reference (i.e., referencing a child class object using a parent class reference variable). It serves as the foundation for **polymorphism** and dynamic method dispatch in Java.

## Key Features
* **Implicit & Safe:** Upcasting is done automatically by the compiler without requiring an explicit cast because a child class object is always an instance of its parent (`IS-A` relationship).
* **Reference Type Governs Scope:** The reference type dictates which methods and fields can be accessed at compile time.
* **Runtime Polymorphism:** Even though the reference is of parent type, the JVM calls the overridden method implementation of the actual child object in heap memory (Dynamic Method Dispatch).
* **Loose Coupling:** Allows writing flexible code that can handle any child implementation via a unified parent interface or class.

## Reasoning Behind Upcasting

### 1. Why Upcasting Works (`A a = new B();`)
* **The IS-A Guarantee:** In Java inheritance, subclass `B` extends superclass `A`. Therefore, `B` inherits all properties, methods, and behaviors of `A`. A `B` object fulfills every contract established by class `A`.
* **Compile-Time Safety:** The reference type `A` decides which methods are permissible to invoke. Because `B` is guaranteed to contain every member declared in `A`, the compiler knows that any method call valid on `A` (such as `func()`) safely exists on the actual `B` instance.

### 2. Why the Inverse Fails (`B b = new A();` Compilation Error)
* **Contract Violation:** A child class `B` is usually an extension (a superset) of the parent class `A`, potentially defining new methods or fields specific to `B`.
* **Prevention of Runtime Crashes:** If the compiler allowed a parent object (`new A()`) to be assigned to a child reference (`B b`), one could subsequently call `B`-specific methods on `b`. Because the actual object in memory is merely an `A` and lacks those child members, calling them would result in undefined behavior or crashes. Java prevents this at compile time.

### 3. Execution via Dynamic Method Dispatch (`a.func()`)
* At **compile time**, Java checks the declared type of reference variable `a` (`A`) to verify that `func()` exists.
* At **runtime**, the JVM inspects the actual object living in heap memory (`new B()`) and executes `B`'s overridden implementation of `func()`, returning `"Something Else"`.

## Code Example

Here is an example demonstrating class inheritance, method overriding, and upcasting.

```embed-java
PATH: "vault://src/main/java/com/personal/BasicClassAndObjectConcepts/SameMethodMultiClass.java"
```

## Output

Verified on Java 25:

```
Something Else
```

## Comparison

### Overloading vs Overriding

| Aspect | Overloading | Overriding |
|---|---|---|
| Where | Same class (or inherited) | Subclass redefines a parent method |
| Signature | Same name, **different parameters** | Same name **and** same parameters |
| Return type | Can be anything | Same type, or a subtype (covariant return) |
| Resolved | **Compile time**, by reference and argument types | **Runtime**, by the actual object |
| `static` / `private` / `final` methods | Can be overloaded | Cannot be overridden (`static` methods are *hidden*, not overridden) |
| Access modifier | Any | Same or **wider** (cannot reduce visibility) |
| Checked exceptions | Any | Same, narrower, or none |

### Abstract Class vs Interface

| Aspect | Abstract class | Interface |
|---|---|---|
| Inheritance | `extends` only one class | `implements` many interfaces |
| State | Instance fields allowed | Only `public static final` constants |
| Constructors | Yes | No |
| Methods | Abstract and concrete, any access level | `abstract`, `default`, `static`, `private` (Java 9+) |
| Use when | Related classes share state or code ("is a kind of") | Unrelated classes share a capability ("can do": `Comparable`, `Runnable`) |

## Day-to-Day Points for Experienced Developers

* **Always put `@Override` on overriding methods.** The compiler then verifies that you really override something, so a typo like `equals(Point p)` (an overload, not an override) or a parent signature change is caught at compile time instead of silently breaking dispatch.
* **Overloads are chosen at compile time, overrides at runtime.** With `void print(A a)` and `void print(B b)`, calling `print(a)` where `A a = new B()` picks `print(A)`, because overload resolution uses the declared type. Mixing the two is a frequent source of surprising behaviour.
* **Fields are not polymorphic, only methods are.** If `A` and `B` both declare `name`, then `A a = new B(); a.name` reads `A`'s field (verified). Never redeclare a parent's field in a child; expose state through methods.
* **Downcast only after a type check.** `B b = (B) new A();` compiles but throws `ClassCastException` at runtime (verified). Use pattern matching: `if (a instanceof B b) { b.onlyInB(); }` (Java 16+).
* **Don't call overridable methods from a constructor.** The parent constructor runs first, so the child's override sees uninitialized fields (`null`/`0`) (verified: a `String` field reads `null`). Make such methods `private` or `final`.
* **`super.method()` reuses the parent logic inside an override;** `this(...)`/`super(...)` chain constructors. If the parent has no no-arg constructor, every child constructor must call `super(args)` explicitly.
* **Prefer composition over inheritance.** Use `extends` only for a true IS-A relationship; otherwise hold the object in a field and delegate (HAS-A). Inheritance couples you to parent internals: a `HashSet` subclass that counts additions in both `add` and `addAll` double-counts, because `HashSet.addAll` calls `add` (verified: 3 elements counted as 6).
* **`sealed` hierarchies (Java 17+)** restrict which classes may extend a type, so pattern-matching `switch` can be checked for exhaustiveness:
  ```java
  sealed interface Shape permits Circle, Square {}
  record Circle(double r) implements Shape {}
  record Square(double side) implements Shape {}
  double area = switch (shape) {
      case Circle c -> Math.PI * c.r() * c.r();
      case Square s -> s.side() * s.side();
  };
  ```

## Corrections / Notes on the Code

* **Line 22:** the comment says `// result : "Something else"`, but the program prints `Something Else` (capital E), as returned on line 11. Cosmetic, but output comparisons in tests are case-sensitive.
* **Line 20:** `B b = new A();` is correctly marked as a compilation error. The *cast* version, `B b = (B) new A();`, does compile and then fails at runtime with `ClassCastException`.

## Related
* [[LocalVsInstanceVariables]]
* [[JdkJreJvm]]
