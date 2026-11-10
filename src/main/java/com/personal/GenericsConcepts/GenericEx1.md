# Generics and Type Safety in Java

Generics let a class, interface or method work with a **type parameter** (`ArrayList<String>`), so the compiler can check types for you. The core benefit: a generic collection rejects the wrong type at **compile time** instead of failing with a `ClassCastException` at runtime.

## Key Features
* **Type parameter:** `ArrayList<String>` tells the compiler that this list holds only `String` values.
* **Compile-time check:** `al.add(10)` is rejected with a compile error because `Integer` is not a `String`. Without generics the bad element would be accepted and fail later, when someone casts it.
* **No casts on read:** `String s = al.get(0);` needs no `(String)` cast; the compiler inserts it for you.
* **Type erasure:** type arguments exist only at compile time. The bytecode works with a plain `ArrayList`.
* **Raw type on the right-hand side:** `new ArrayList()` has no `<>`, so `javac -Xlint` warns about a raw type and an unchecked conversion. The code still works because the *reference* type `ArrayList<String>` drives the checks.

## How It Works

```mermaid
flowchart LR
    S["Source: ArrayList of String"] --> C["javac: type checks + inserts casts"]
    C --> B["Bytecode: ArrayList (type erased)"]
    B --> R["Runtime: JVM sees only raw ArrayList"]
```

## Code Example

The example declares a `String`-typed list and shows that adding an `Integer` is a compile-time error.

```embed-java
PATH: "vault://src/main/java/com/personal/GenericsConcepts/GenericEx1.java"
```

## Output

The program prints nothing. Compiled with `javac -Xlint:all` on Java 25 it produces two warnings (verified):

```
GenericEx1.java:9: warning: [rawtypes] found raw type: ArrayList
        ArrayList<String> al = new ArrayList();
                                   ^
GenericEx1.java:9: warning: [unchecked] unchecked conversion
        ArrayList<String> al = new ArrayList();
                               ^
  required: ArrayList<String>
  found:    ArrayList
2 warnings
```

## Comparison

### Wildcards and Bounds

| Syntax | Meaning | Can read as | Can add | Typical use |
|---|---|---|---|---|
| `List<T>` | Exactly `T` | `T` | `T` | Normal read and write |
| `List<?>` | Unknown type | `Object` | Only `null` | Code that does not care about the type (`size()`, printing) |
| `List<? extends Number>` | `Number` or any subtype | `Number` | Nothing (except `null`) | **Producer**: you only read from it |
| `List<? super Integer>` | `Integer` or any supertype | `Object` | `Integer` (and its subtypes) | **Consumer**: you only write to it |
| `<T extends Comparable<T>>` | Bounded type parameter | `T` | `T` | Generic methods that need specific methods on `T` |

### Raw type vs `<Object>` vs `<?>`

| Declaration | Type-checked? | Can add anything? | Notes |
|---|---|---|---|
| `List` (raw) | No | Yes, with an unchecked warning | Legacy only. Turns off generic checks. |
| `List<Object>` | Yes | Yes (everything is an `Object`) | But `List<String>` is **not** a `List<Object>` |
| `List<?>` | Yes | No (only `null`) | Accepts a `List<String>`, a `List<Integer>`, and so on |

## Day-to-Day Points for Experienced Developers

* **Always use the diamond operator:** `List<String> list = new ArrayList<>();` or `var list = new ArrayList<String>();`. Note that `var list = new ArrayList<>();` infers `ArrayList<Object>`, which is rarely what you want.
* **Heap pollution through raw types (a real production bug):** mixing raw and generic references compiles with only a warning and fails later, far from the cause:
  ```java
  List<String> names = new ArrayList<>();
  List raw = names;  raw.add(42);           // unchecked warning only
  String s = names.get(0);                  // ClassCastException here
  ```
  Treat `unchecked` warnings as errors (`-Xlint:all -Werror`). Use `@SuppressWarnings("unchecked")` only on the smallest scope you have proved safe.
* **Primitives are not allowed as type arguments:** `List<int>` does not compile; use `List<Integer>` (autoboxed). Two related traps:
  ```java
  List<Integer> nums = new ArrayList<>(List.of(10, 20, 30));
  nums.remove(1);                   // removes index 1 -> [10, 30]
  nums.remove(Integer.valueOf(10)); // removes the value 10
  Integer a = 128, b = 128;
  boolean same = (a == b);          // false: Integer caches only -128..127; use equals()
  ```
* **Type erasure limits:** `new ArrayList<String>().getClass() == new ArrayList<Integer>().getClass()` is `true` (verified). So you cannot write `new T()`, `new T[10]`, `x instanceof List<String>`, or overload `m(List<String>)` with `m(List<Integer>)` (same erasure, compile error). Pass a `Class<T>` or `Supplier<T>` when you need to create instances.
* **Generics are invariant, arrays are covariant:** a `List<String>` is not a `List<Object>`. `Object[] o = new String[1];` compiles, which is why `o[0] = 1;` fails at runtime with `ArrayStoreException`.
* **PECS: Producer `extends`, Consumer `super`.** Apply it to method parameters of library-style APIs:
  ```java
  static <T> void copy(List<? super T> dest, List<? extends T> src)   // Collections.copy
  double sum(List<? extends Number> nums)  // works for List<Integer>, List<Double>
  ```
* **Generic methods and classes:** a method declares its own type parameter before the return type, and the compiler infers it. Records can be generic too:
  ```java
  static <T extends Comparable<T>> T max(List<T> list) { ... }
  record Pair<K, V>(K key, V value) {}
  ```
* **Static members cannot use the class's type parameter:** `static T cache;` inside `Box<T>` does not compile, because one static field is shared by all parameterizations. Conventional names: `T` type, `E` element, `K`/`V` key/value, `R` result.

## Corrections / Notes on the Code

* **Line 9:** `new ArrayList()` is a raw type. It compiles, but `javac -Xlint:all` warns `[rawtypes]` and `[unchecked]` (see Output). Prefer `new ArrayList<>()`. The comment on line 12 is correct: `al.add(10)` is a compile error, because the declared type `ArrayList<String>` is what the compiler checks.

## Related
* [[ArrayDequeEx1]]
