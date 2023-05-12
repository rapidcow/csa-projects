# Builder pattern

This was something I recommended my teacher briefly talk about
in class but started and ended up as something between me and
my teacher...

For the rough time these occurred at:

*   I sent the email (`builder.md`) on Oct 23, 2021
*   I gave a [presentation][slides] (as requested by my teacher)
    on Nov 6, 2021

Coming from coding in Python, I found it interesting how Java cleverly
compensates for the lack of properties/descriptors (getters and setters),
optional positional arguments (overriding), and optional keyword
arguments (builder pattern is one of Java's solutions).

I first noticed this when I was wondering how to replace the default
delimiter of `Scanner` (which is to match one or more Java whitespace
characters) so that it scans on a per-line basis.  I was surprised to
find that Java provides no such parameter to specify this in the
constructor, but instead has a `useDelimiter` method that returns
`this` and allows chaining:

```java
// import java.util.Scanner;
Scanner sc = new Scanner(System.in).useDelimiter("\\n");
```

This was very interesting to me because in Python it would be idiomatic
to write `return self` in rare circumstances (e.g. `__iter__()` and
`__enter__()`), and a common solution to such optional fields with
a default value is to simply pass it in as a keyword argument, and
the syntax would look more like this:

```python
# import sys
sc = Scanner(sys.stdin, delimiter=r'\n')
```

Well... just to give you the reason why I found this (pun intended?) so
interesting.  Although the presentation falls short, the purpose was to
introduce my classmates to programming in the outer world beyond merely
a CS course (with the links I included at the end of `builder.md` ---
check them out if you are interested in OOP design patterns!)

That is, if everything went well or if I made myself clearer (or if they
were even listening...) that would be the purpose of this mini-lecture.

[slides]: https://docs.google.com/presentation/d/1_-lcMgR_doqC9NVYG1c3disZNEtOM1r96Y-BmNz8jTY/edit?usp=sharing
