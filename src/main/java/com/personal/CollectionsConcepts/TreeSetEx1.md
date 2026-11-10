# TreeSet in Java

`TreeSet` is a sorted `Set` backed by a red-black tree (`TreeMap`). Elements are kept in natural order or in the order of a supplied `Comparator`, and it offers navigation methods such as `floor`, `ceiling` and range views.

## Key Features
* **Backed by a `TreeMap`:** a red-black tree (a self-balancing BST), so `add`, `remove` and `contains` are **O(log N)**.
* **Hierarchy:** `TreeSet` implements `NavigableSet`, which extends `SortedSet`, which extends `Set`.
* **Sorted, not insertion-ordered:** elements follow natural ordering (`Comparable`) or a `Comparator` passed to the constructor.
* **Duplicates are decided by `compareTo`/`compare` returning 0**, not by `equals()`.
* **All elements must be mutually comparable:** mixing `Integer` and `String` throws `ClassCastException`. `null` throws `NullPointerException` under natural ordering.
* **No index access:** use navigation methods: `first()`, `last()`, `floor(x)`, `ceiling(x)`, `higher(x)`, `headSet(x)`, `tailSet(x)`, `descendingSet()`.

## Code Example

The example inserts integers (including a duplicate) into a raw `TreeSet`, then tries to add a `String` and a `null`.

```embed-java
PATH: "vault://src/main/java/com/personal/CollectionsConcepts/TreeSetEx1.java"
```

## Output

The program **throws** on line 37 and the `println` is never reached:
```
Exception in thread "main" java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String (java.lang.Integer and java.lang.String are in module java.base of loader 'bootstrap')
	at java.base/java.lang.String.compareTo(String.java:144)
	at java.base/java.util.TreeMap.put(TreeMap.java:848)
	at java.base/java.util.TreeMap.put(TreeMap.java:568)
	at java.base/java.util.TreeSet.add(TreeSet.java:261)
	at com.personal.CollectionsConcepts.TreeSetEx1.main(TreeSetEx1.java:37)
```
With lines 37 and 38 removed, the output is `[25, 50, 75, 100, 125, 150, 175]`. With generics (`TreeSet<Integer>`), line 37 would not even compile.

## Day-to-Day Points for Experienced Developers
* **A comparator that returns 0 drops data silently:** the set treats "compares equal" as "duplicate".
  ```java
  var byLen = new TreeSet<String>(Comparator.comparingInt(String::length));
  byLen.addAll(List.of("ab", "cd")); // [ab] -- "cd" dropped! (verified)
  // fix: Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
  ```
* **Keep the comparator consistent with `equals`:** with `String.CASE_INSENSITIVE_ORDER`, `"Java"` and `"JAVA"` collapse into one element, and `set.equals(hashSet)` can be `true` one way and `false` the other (verified). Mixing `TreeSet` and `HashSet` semantics breaks the `Set` contract.
* **Use the navigation API instead of scanning:** range and nearest-value lookups are O(log N).
  ```java
  ts.floor(80);                      // 75   (greatest <= 80; null if none)
  ts.ceiling(80);                    // 100  (least >= 80)
  ts.subSet(50, true, 125, true);    // [50, 75, 100, 125]
  ts.pollFirst();                    // remove and return the smallest
  ```
  Typical uses: time-window lookups, nearest price levels, scheduling slots. Remember `floor`/`ceiling` return `null`, so unboxing to `int` can NPE.
* **Range views are live and bounded:** `headSet(100)` reflects later changes to the set, and adding an out-of-range element through the view throws `IllegalArgumentException` (verified).
* **Need nulls?** Supply `Comparator.nullsFirst(Comparator.naturalOrder())`; otherwise filter them out before insertion.
* **Do not mutate fields used in comparison** after insertion; the tree becomes inconsistent and `contains`/`remove` miss the element, exactly like mutable hash keys.
* **Sorting once vs keeping sorted:** if you only need sorted output at the end, `list.sort(...)` (or `stream().sorted().distinct()`) is cheaper than O(N log N) tree inserts plus a node per element. Use `TreeSet` when you need sorted order *and* lookups while data changes. For concurrency, use `ConcurrentSkipListSet`.

## Corrections / Notes on the Code
1. **~L14:** `TreeSet` implements `NavigableSet`, which extends `SortedSet`, which extends `Set`.
2. **~L37:** `ts.add("ascacs")` throws `ClassCastException` because a `String` cannot be compared with an `Integer` during tree insertion. It has nothing to do with the duplicate check. Execution stops there, so L38 and the `println` (L41) never run.
3. **~L38:** the comment should say NULL values **cannot** be added (`NullPointerException` under natural ordering).

## Related
[[LinkedHashSetAndHashSetEx1]] · [[PriorityQueueEx1]] · [[IteratorEx1]]
