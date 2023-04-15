
import java.util.Arrays;
import java.util.Comparator;

// enums, class Person

public class EnumsLambdas {

    // printArray static function

    public static void main(String[] args) {
        Person[] persons = {
            new Person("Max",  Sex.M, Size.XL, Country.NL),
            new Person("Jan",  Sex.M, Size.S,  Country.PL),
            new Person("Eva",  Sex.F, Size.XS, Country.NL),
            new Person("Lina", Sex.F, Size.L,  Country.DE),
            new Person("Mila", Sex.F, Size.S,  Country.DE),
            new Person("Ola",  Sex.F, Size.M,  Country.PL),
        };

        Comparator<Person> sexThenSize = /* lambda */;

        Arrays.sort(persons, sexThenSize);
        printArray("Persons by sex and then size", persons);

        Arrays.sort(persons, /* lambda */);
        printArray("Persons by size and then name", persons);

        Country[] countries = Country.values();
        Arrays.sort(countries, /* lambda */);
        printArray("Countries by name", countries);
    }

    private static void printArray(String message, Object[] array)
    {

        System.out.println(message);

        for (int h = 0; h <= array.length-1; h++)
        {
            System.out.println(array[h]);
        }


    }
}
