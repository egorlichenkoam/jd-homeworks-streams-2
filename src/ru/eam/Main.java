package ru.eam;

import ru.eam.person.Education;
import ru.eam.person.Person;
import ru.eam.person.Sex;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
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
        }
        System.out.println(getUnderageCount(persons));

        getListOfPeopleToBeDrafted(persons).forEach(System.out::println);

        getSortedByLastNameListOfPotentiallyAbleBodiedPeopleWithHigherEducation(persons).forEach(System.out::println);
    }

    /**
     * Возвращает число несовершеннолетних {@link Person}.
     *
     * @param personCollection коллекция {@link Person}.
     * @return число несовершеннолетних {@link Person}.
     */
    public static Long getUnderageCount(Collection<Person> personCollection) {
        return personCollection.stream()
                .map(Person::getAge)
                .filter(age -> age < 18)
                .count();
    }

    /**
     * Возвращает коллекцию {@link Person} призывного подлежащих призыву.
     *
     * @param personCollection коллекция {@link Person}.
     * @return коллекцию {@link Person}.
     */
    public static Collection<Person> getListOfPeopleToBeDrafted(Collection<Person> personCollection) {
        return personCollection.stream()
                .filter(person -> person.getAge() > 17)
                .filter(person -> person.getAge() < 27)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Возвращает коллекцию {@link Person} потенциально работоспособных людей с высшим образованием отсортированную по фамилии.
     *
     * @param personCollection коллекция {@link Person}.
     * @return коллекцию {@link Person}.
     */
    public static Collection<Person> getSortedByLastNameListOfPotentiallyAbleBodiedPeopleWithHigherEducation(Collection<Person> personCollection) {
        return personCollection.stream()
                .filter(person -> Objects.equals(person.getEducation(), Education.HIGHER))
                .filter(person -> {
                    if (Objects.equals(person.getSex(), Sex.WOMAN)) {
                        if ((person.getAge() > 17)
                                && (person.getAge() < 60)) {
                            return true;
                        }
                    } else {
                        if ((person.getAge() > 17)
                                && (person.getAge() < 65)) {
                            return true;
                        }
                    }
                    return false;
                })
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
