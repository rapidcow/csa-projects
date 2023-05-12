# On Constructor Patterns: The Builder Pattern

The following is an email I sent to my CSA teacher.
I wrote this with the intention for this to be read by
my classmates, but I suppose you'll be the second person
to read this...

I placed my attachments in the `examples/` directory.

---

Hello Bill,

In Wednesday's class (AP CS A) I mentioned the builder pattern to you.
Well, I did a bit of digging and found some interesting thing that I would
like to share with you (and some of our other classmates too, if you think
that's appropriate!)

To start with, this is the `Employee` class that you gave as an example in
class:

```java
public class Employee {
    /* instance variables */
    private int _id;
    private String _name;
    private double _salary;

    /* first constructor */
    public Employee(int id) {
        _id = id;
    }

    /* second constructor */
    public Employee(int id, String name) {
        _id = id;
        _name = name;
    }

    /* third constructor */
    public Employee(int id, String name, double salary) {
        _id = id;
        _name = name;
        _salary = salary;
    }

    /* getter */
    public void display() {
        System.out.println("id: " + _id);
        System.out.println("name: " + _name);
        System.out.println("salary: " + _salary);
    }
}
```

And the way we construct an `Employee` object is as follows (to be written
in the main method or something alike):

```java
Employee e1 = new Employee(21000);
Employee e2 = new Employee(21000, "Ben");
Employee e3 = new Employee(21000, "Ben", 2500.0);
```

One disadvantage of such kind of pattern is that it's hard to see what each
of the parameters represents. As the `Employee` class stores more and more
complicated attributes and the number of parameters grows, well, you risk
losing the readability in your code.

And not to mention, these parameters have to be entered in this *exact*
order. What happens if you have the salary but not the name? Well...

```java
Employee e4 = new Employee(21000, null, 2500.0);
```

It's doable, but it can get awkward.

So what are some other alternatives to this kind of constructor overloading?
Enter the builder pattern... [drumroll intensifies]

With the builder pattern, we can write something like

```java
/* ID as parameter */
EmployeeBuilder builder = new EmployeeBuilder(21000);
/* Every optional field calls setSomething() */
builder.setName("Ben");
builder.setSalary(2500.0);
Employee e5 = builder.build();
```

or worse,

```java
Employee e6 = new EmployeeBuilder(21000).setName("Ben")
                                        .setSalary(2500.0)
                                        .build();
```

(A chained miracle or hazard, depending on how you view it...)

The point is, you instantiate the builder with the most vital information
(in this case, the ID), and then keep piling up optional information, like
the name and salary. The code is definitely a lot more readable because
you can clearly see through the methods that `"Ben"` is the name of the
employee and `2500.0` is the salary. (If you know Python's
[keyword arguments][], well, this is in some sense Java's way of doing it!)

[keyword arguments]: https://docs.python.org/3/glossary.html#term-argument

And, of course, the case of knowing the salary and not the name is
effectively handled:

```java
Employee e7 = new EmployeeBuilder(21000).setSalary(2500.0)
                                        .build();
```

Here's a sample implementation of `EmployeeBuilder`. Notice the extensive
use of `return this` (which is what allows the methods to be chained in the
previous examples).

```java
class EmployeeBuilder {
    private int _id;
    private String _name;
    private double _salary;

    public EmployeeBuilder(int id) {
        _id = id;
    }

    public EmployeeBuilder setName(String name) {
        _name = name;
        return this;
    }

    public EmployeeBuilder setSalary(double salary) {
        _salary = salary;
        return this;
    }

    public Employee build() {
        return new Employee(_id, _name, _salary);
    }
}
```

The source files for the above examples are attached as a zip archive of an
Eclipse Java project named `builder-example`. The `Main` class (`Main.java`)
contains a main method which tests out the two constructor patterns.
An HTML copy (produced by [pandoc][]) of this current email is also
attached so that it would be easier for other classmates to view this
message.

[pandoc]: https://pandoc.org/

---

The following is a practical example of the builder pattern, right within
the familiar `Scanner` class we know. If one, for example, wishes the
`Scanner` to delimit its input from user input (stdin) with newlines rather
than any whitespace character
([which is the default](https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html)),
then they can write (the escaped backslash is intentional)

```java
Scanner sc = new Scanner(System.in).useDelimiter("\\n");
```

(To try this out, try inputting two words with space between them and use
`sc.next()`. With `useDelimiter("\\n")`, you get all the words on the same
line; without it, you would get just the string before the first space, or
the first word only.)

The source code looks a bit like the following (I found this from [a
repository of a mirror of JDK 7][useDelimiter-src], a rather old version to
be honest):

```java
public final class Scanner implements Iterator<String>, Closeable {
    /**
     * Sets this scanner's delimiting pattern to a pattern constructed from
     * the specified <code>String</code>.
     *
     * <p> An invocation of this method of the form
     * <tt>useDelimiter(pattern)</tt> behaves in exactly the same way as the
     * invocation <tt>useDelimiter(Pattern.compile(pattern))</tt>.
     *
     * <p> Invoking the {@link #reset} method will set the scanner's delimiter
     * to the <a href= "#default-delimiter">default</a>.
     *
     * @param pattern A string specifying a delimiting pattern
     * @return this scanner
     */
    public Scanner useDelimiter(String pattern) {
        delimPattern = patternCache.forName(pattern);
        return this;
    }
}
```

There's also methods like `useLocale()` and `useRadix()` that return this
very instance of `Scanner` as well. (Check the documentation!)

[useDelimiter-doc]: https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html#useDelimiter(java.lang.String)
[useDelimiter-src]: https://github.com/openjdk-mirror/jdk7u-jdk/blob/f4d80957e89a19a29bb9f9807d2a28351ed7f7df/src/share/classes/java/util/Scanner.java#L1175

Further reading:

-   [Exploring Joshua Bloch's Builder design pattern in Java][builder-1]
-   [Stack Overflow - When would you use the Builder Pattern?][builder-2]
-   [On Software Development: A Java Builder Pattern][builder-3]

[builder-1]: https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java
[builder-2]: https://stackoverflow.com/a/1953567
[builder-3]: http://rwhansen.blogspot.com/2007/07/theres-builder-pattern-that-joshua.html

Sincerely,\
Ethan
