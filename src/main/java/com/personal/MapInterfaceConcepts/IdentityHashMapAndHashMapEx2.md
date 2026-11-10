# IdentityHashMap vs HashMap in Java

Map implementations differ in how they decide that two keys are "the same". `HashMap` uses `hashCode()` + `equals()` (logical equality), while `IdentityHashMap` uses `==` (reference equality), so two distinct objects with equal values are one key in a `HashMap` but two keys in an `IdentityHashMap`.

## Key Features
* **`HashMap` key equality:** `hashCode()` picks the bucket, then `equals()` compares. `Integer.equals` compares values, so `key2` overwrites `key1`'s value.
* **`IdentityHashMap` key equality:** uses `==` and `System.identityHashCode()`. Two `new Integer(10)` objects are different references, so both are stored.
* **Autoboxing and the `Integer` cache:** `ihm.put(nkey1, ...)` autoboxes via `Integer.valueOf(10)`, which returns the *same cached object* for -128..127. That is why `"some"` is replaced by `"some-thing"`.
* **Deliberately breaks the `Map` contract:** the general `Map` contract is defined in terms of `equals`; `IdentityHashMap` intentionally violates it.
* **Unspecified order:** iteration order is based on identity hash codes and can differ between runs.

## Code Example

Puts two distinct `Integer(10)` objects into a `HashMap` and an `IdentityHashMap`, then adds two autoboxed `int` keys to the identity map.

```embed-java
PATH: "vault://src/main/java/com/personal/MapInterfaceConcepts/IdentityHashMapAndHashMapEx2.java"
```

## Output
```
{10=VALUE2}
{10=value2, 10=value1}
{10=value2, 10=value1, 10=some-thing}
```
The order of the identity-map lines may differ between runs/JVMs.

## Day-to-Day Points for Experienced Developers
* **Custom key classes must override `equals()` and `hashCode()` together.** Overriding only `equals` puts "equal" keys in different buckets. A `record` does both for you: `record EmpId(String dept, int no) {}`.
* **Never mutate a key after insertion:** if its `hashCode()` changes, `get` can no longer find it and the entry effectively leaks. Prefer immutable keys (`String`, `Integer`, records).
* **Never compare boxed numbers with `==`:** `Integer a = 127, b = 127` gives `a == b` true, but with `1000` it is false (outside the cache, verified). Use `equals` or `Objects.equals`; a classic code-review red flag around `Long` IDs.
* **Do not use `new Integer(...)`:** deprecated for removal since Java 9 (javac 25 warns). Use `Integer.valueOf` or autoboxing. Here it is acceptable only because distinct objects are the point of the demo.
* **Legitimate `IdentityHashMap` uses:** "already visited" tracking in object-graph traversal, serializers, deep-copy or cycle detection. Identity set: `Set<Object> seen = Collections.newSetFromMap(new IdentityHashMap<>());`
* **Pick the map by key type:** enum keys belong in `EnumMap` (array-backed, ordered, fast); caches keyed by objects that should stay garbage-collectable belong in `WeakHashMap` (or a library cache like Caffeine).

## Corrections / Notes on the Code
* **~line 46-47:** The comment says `"some"` disappears because "for primitive types == only checks raw values". `IdentityHashMap` cannot hold primitives. `nkey1` and `nkey2` are **autoboxed** with `Integer.valueOf(10)`, and values in -128..127 come from the `Integer` cache, so both calls pass the *same object*. With `1000` both entries are stored (verified: two autoboxed `int 1000`s give size 2, two `int 10`s give size 1).
* **~line 46:** The expected output `{10=some-thing, 10=value2, 10=value1}` did not match the actual run. `IdentityHashMap` order is unspecified, so neither order is guaranteed.
* **~line 22:** `HashMap` calls `hashCode()` first to choose the bucket, then `equals()`. Both are involved, not just `equals()`.

## Related
[[HashMapEx1]] | [[LinkedHashMapEx1]]
