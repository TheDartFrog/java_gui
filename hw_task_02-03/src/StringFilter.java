import java.util.Arrays;

@FunctionalInterface
interface SFilter {
    boolean test(String s);

    public static String[] filter(String[] arr, SFilter filt) {
        return Arrays.stream(arr).filter(filt::test).toArray(String[]::new);
    }
}

class LenFilter implements SFilter {
    private int minLen;

    public LenFilter(int minLen) {
        this.minLen = minLen;
    }

    public boolean test(String s) {
        return s.length() >= minLen;
    }
}

public class StringFilter {
    public static void main(String[] args) {
        String[] arr = {"Alice", "Sus", "Janet", "Bea"};
        System.out.println(Arrays.toString(arr));

        String[] a1 = SFilter.filter(arr, new LenFilter(5));
        System.out.println(Arrays.toString(a1));

        String[] a2 = SFilter.filter(arr, new SFilter() {
            public boolean test(String s) {
                char firstLetter = s.charAt(0);
                return firstLetter >= 'A' && firstLetter < 'D';
            }
        });
        System.out.println(Arrays.toString(a2));

        String[] a3 = SFilter.filter(arr, s -> {
            char firstLetter = s.charAt(0);
            return firstLetter > 'H' && firstLetter <= 'Z';
        });
        System.out.println(Arrays.toString(a3));
    }
}
