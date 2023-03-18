////////////////////////////////////////////////////////////////////////
public class Main {
    public static void main(String[] args) {
        TwoStringsOper[] a = {
                new Concat(), new ConcatRev(),
                new Initials(), new Separ(" loves ")
        };
        for (TwoStringsOper op : a) {
            System.out.println(op.apply("Mary", "John"));
        }
    }

}
////////////////////////////////////////////////////////////////////////
interface TwoStringsOper {
    String apply(String s1, String s2);
}
////////////////////////////////////////////////////////////////////////
class Concat implements TwoStringsOper {
    public String apply(String s1, String s2) {
        return s1 + s2;
    }
}
////////////////////////////////////////////////////////////////////////
class ConcatRev implements TwoStringsOper {
    public String apply(String s1, String s2) {
        return s2 + s1;
    }
}
////////////////////////////////////////////////////////////////////////
class Initials implements TwoStringsOper {
    public String apply(String s1, String s2) {
        return "" + s1.charAt(0) + s2.charAt(0);
    }
}
////////////////////////////////////////////////////////////////////////
class Separ implements TwoStringsOper {
    private final String separator;

    public Separ(String separator) {
        this.separator = separator;
    }

    public String apply(String s1, String s2) {
        return s1 + this.separator + s2;
    }
}
////////////////////////////////////////////////////////////////////////