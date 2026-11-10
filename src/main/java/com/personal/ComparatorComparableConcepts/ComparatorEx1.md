# Comparator Interface in Java

`Comparator<T>` defines an *external*, pluggable ordering through `compare(T o1, T o2)`. Unlike `Comparable`, it does not require changing the class being sorted, and one class can have any number of comparators.

## Key Features
* **External ordering:** `Comparator<T>` (in `java.util`) has `int compare(T o1, T o2)`. Same sign contract as `compareTo`, with `o1` in the role of `this`.
* **Multiple orderings:** many comparators for one class (by age, by name, ...) without touching the class.
* **Functional interface:** `Comparator` is annotated `@FunctionalInterface` (its `equals` is inherited from `Object` and does not count), so a lambda or method reference works anywhere a `Comparator` is expected.
* **`Collections.reverse` is not a sort:** it just flips the current list order. After an ascending sort, it yields descending order.
* **`list.sort(cmp)`** (Java 8+) is the instance-method equivalent of `Collections.sort(list, cmp)`.
* **Reuse of `Student`:** `ComparatorEx1` uses the package-private `Student` declared in `ComparableInterfaceEx1.java`, so the two files must be compiled together.

## Code Example

Sorts a `Student` list with a separate `ComparatorImpl` class, reverses it, prints it, and finally re-sorts with the lambda `nums.sort((l, r) -> l.age - r.age)`.

```embed-java
PATH: "vault://src/main/java/com/personal/ComparatorComparableConcepts/ComparatorEx1.java"
```

## Output
```
[Student [age= 29 name= Payal ], Student [age= 28 name= Raj ], Student [age= 27 name= Abhijeet ], Student [age= 26 name= Pralay ]]
```
The final `nums.sort(...)` re-sorts ascending but nothing is printed after it.

## Comparison: Comparable vs Comparator

| Aspect | `Comparable<T>` | `Comparator<T>` |
|---|---|---|
| Package | `java.lang` | `java.util` |
| Method | `compareTo(T o)` | `compare(T o1, T o2)` |
| Where it lives | Inside the class being sorted | Separate class, lambda or method reference |
| Number of orderings | One (natural ordering) | As many as you want |
| Needs source change | Yes | No |
| Usage | `Collections.sort(list)`, `new TreeSet<>()` | `Collections.sort(list, cmp)`, `list.sort(cmp)`, `new TreeSet<>(cmp)` |
| Helper methods | None | `comparing`, `thenComparing`, `reversed`, `nullsFirst`, `naturalOrder`, `reverseOrder` |

## Day-to-Day Points for Experienced Developers
* **Build comparators declaratively** instead of writing `ComparatorImpl` classes (assumes accessors, e.g. `record Student(String name, int age)`):
  ```java
  list.sort(Comparator.comparingInt(Student::age).thenComparing(Student::name));
  ```
  `comparingInt` avoids boxing.
* **`reversed()` applies to the whole chain built so far:** `comparing(A).thenComparing(B).reversed()` reverses both keys. To reverse only one key: `comparing(A).thenComparing(B, Comparator.reverseOrder())`.
* **Descending without reversing the list:** `list.sort(Comparator.comparingInt(Student::age).reversed())`, or `Collections.reverseOrder()` for natural order. Sort-then-`Collections.reverse` costs an extra pass and also reverses the relative order of ties.
* **Null-safe sorting:** `Comparator.comparing(Student::name, Comparator.nullsLast(Comparator.naturalOrder()))`. A plain comparator throws `NullPointerException` on a `null` field.
* **Streams:** `students.stream().sorted(Comparator.comparing(Student::name)).toList()` returns a new unmodifiable sorted list without mutating the source. `stream.max(cmp)` / `Collectors.maxBy(cmp)` return an `Optional`.
* **Store reusable comparators as constants:** `static final Comparator<Student> BY_AGE = Comparator.comparingInt(Student::age);`. Comparators are stateless and thread-safe, and named constants read better in reviews than inline lambdas.
* **Sorted collections use the comparator for equality:** `new TreeMap<>(String.CASE_INSENSITIVE_ORDER)` treats `"A"` and `"a"` as the same key, handy for case-insensitive headers, surprising if unintended.

## Corrections / Notes on the Code
* **~line 47:** The commented output shows ascending order (26, 27, 28, 29), but the code sorts ascending and *then* calls `Collections.reverse(nums)`, so the actual output is descending: `Payal 29, Raj 28, Abhijeet 27, Pralay 26` (verified).
* **~line 18:** The comment says "compareTo will default to ascending order"; in a `Comparator` the method is `compare`. Neither method "defaults" to ascending: the order depends entirely on what you return.
* **~line 12 and ~line 50:** `o1.age - o2.age` is overflow-prone in general; prefer `Integer.compare(o1.age, o2.age)` or `Comparator.comparingInt`.
* **~line 8:** Using `Student` from another source file compiles, but `javac -Xlint` warns `[auxiliaryclass]` (verified). Give `Student` its own `Student.java` in real projects.
* **~line 52:** Correct that `Comparator` is a functional interface.

## Related
[[ComparableInterfaceEx1]]
