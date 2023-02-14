import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //Генерация исходных данных (списка людей)
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        } //Конец генерации исходных данных

        // 1. Находим количество несовершеннолетних (т.е. людей младше 18 лет).
        long countMinor;
        countMinor = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + countMinor);

        // 2. Находим список фамилий призывников (т.е. мужчин от 18 и до 27 лет).

        List<String> families_prizivnik = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN && person.getAge() > 18
                        && person.getAge() < 27)
                .map(person -> person.getFamily())
                .collect(Collectors.toList());

        System.out.println("Призывники: " + families_prizivnik);

        // 3. Получить отсортированный по фамилии список потенциально работоспособных
        // людей с высшим образованием в выборке
        // (т.е. людей с высшим образованием от 18 до 60 лет для женщин
        // и до 65 лет для мужчин).
        Collection<Person> personsWork = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER &&
                        ((person.getSex() == Sex.MAN && person.getAge() < 65 && person.getAge() > 18) ||
                                (person.getSex() == Sex.WOMAN && person.getAge() < 60 && person.getAge() > 18)))
                .sorted(Comparator.comparing(person -> person.getFamily()))
                .collect(Collectors.toList());
        for (Person pers : personsWork
        ) {
            System.out.println(pers);
        }
      
    }
}
