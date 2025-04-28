// MyTestingClass.java

/**
 * A simple testing class whose hashCode() we control.
 */
public class MyTestingClass {
    private final int id;

    public MyTestingClass(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyTestingClass)) return false;
        MyTestingClass other = (MyTestingClass) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        // A simple custom hash: mix the bits of id
        return (id ^ (id >>> 16));
    }

    @Override
    public String toString() {
        return "Key{" + id + "}";
    }
}
