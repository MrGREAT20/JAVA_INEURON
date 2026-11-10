# Anonymous Inner Classes in Java

An **anonymous inner class** is a class with no name that is declared and instantiated in a single expression. It either implements an interface or extends a class, and is handy for one-off implementations that don't deserve a named class.

## Key Features
* **Declare + instantiate in one expression:** `new IAlpha() { ... }` creates a one-off implementation of `IAlpha` without a separate named class.
* **Can extend a class too:** The second example subclasses the concrete class `AnonymousClassEx`, overrides `checkMethod()`, and calls `super.checkMethod()` in the middle.
* **Real classes on disk:** The compiler generates `AnonymousInnerClassExampleInterface$1.class` and `$2.class` (verified).
* **No constructors:** An anonymous class can't declare a constructor; it passes arguments to the superclass constructor (`new Base(arg) { ... }`) and uses an instance initializer block for setup.
* **Static methods are hidden, not overridden:** As the comment on line 14 says, a same-signature `static` method in a subclass only hides the parent's; there is no dynamic dispatch.
* **When you still need them:** Anonymous classes can hold state (fields), implement interfaces with several abstract methods, or extend abstract/concrete classes. Lambdas can do none of these.

## Code Example

This example implements the functional interface `IAlpha` and extends the class `AnonymousClassEx`, both anonymously, and calls the overridden methods.

```embed-java
PATH: "vault://src/main/java/com/personal/FunctionalInterface/AnonymousInnerClassExampleInterface.java"
```

## Output

```
Hii there
Check you out
Check this method
Check me out
```

## Comparison: Anonymous Class vs Lambda

| Aspect | Anonymous inner class | Lambda |
|---|---|---|
| Target | Any interface or class (abstract or concrete) | Only a functional interface |
| Abstract methods it can implement | Any number | Exactly one |
| State (own fields) | Yes | No |
| `this` refers to | The anonymous object (`V$1`, verified) | The enclosing instance (`V`, verified) |
| New variable scope | Yes, can shadow outer variables | No, redeclaring an outer local is a compile error |
| Compiled output | Separate `Outer$1.class` | No extra class file; `invokedynamic` |
| Captured locals | Must be effectively final | Must be effectively final |

## Day-to-Day Points for Experienced Developers

* **Hidden reference to the outer instance:** An anonymous class created in an instance method captures `Outer.this`, even if unused. Long-lived listeners, callbacks or caches holding it can leak the whole outer object. Lambdas capture `this` only if they use it. (In this file `main` is static, so there is no outer instance.)
* **Avoid "double brace initialization":** `new ArrayList<>() {{ add("x"); }}` creates an extra anonymous subclass (`L$1`, verified), captures the outer instance, and breaks `equals`/serialization expectations. Use `List.of(...)` or a plain builder.
* **Overriding concrete classes is fragile:** Subclassing a concrete class anonymously (as with `AnonymousClassEx`) couples you to its internals; prefer composition or a functional parameter.
* **Converting to lambdas:** IDEs offer "replace with lambda" for SAM types, but check for uses of `this`, `toString()`, or shadowed variable names first, as their meaning changes.
* **Diamond with anonymous classes (Java 9+):** `new Comparator<>() { ... }` is allowed when the inferred type is denotable.
* **Readable stack traces:** Anonymous classes appear as `Outer$1`, `Outer$2` in logs and profilers. A named nested class is easier to search for in production traces.
* **Serialization warning:** Serializing anonymous classes is strongly discouraged; their synthetic names depend on declaration order and change when code is edited.

## Corrections / Notes on the Code

* **`public static void main()` with no `String[]` (~line 19):** valid only because of Java 25 instance main methods (JEP 512). On Java 21 or earlier this class would not be launchable.

## Related
[[IFunctionalInterfaceConcept1]] | [[LambdaExpressionExample]] | [[IInterfaceConcepts1]]
