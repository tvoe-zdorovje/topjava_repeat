package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final EntityMatcher<Meal> MEAL_MATCHER = new EntityMatcher<>();

    //user meals
    public static final Meal MEAL_1 = new Meal(START_SEQ+2, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(START_SEQ+3, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(START_SEQ+4, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(START_SEQ+5, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_5 = new Meal(START_SEQ+6, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_6 = new Meal(START_SEQ+7, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_7 = new Meal(START_SEQ+8, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    //admin meals
    public static final Meal MEAL_8 = new Meal(START_SEQ+9, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal MEAL_9 = new Meal(START_SEQ+10, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> ADMIN_MEALS = Stream.of(MEAL_8, MEAL_9).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    public static final List<Meal> USER_MEALS = Stream.of(MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "Новая еда", 1000);
    }

    public static Meal getUpdated(Meal toUpdate) {
        Meal updated = new Meal(toUpdate);
        updated.setDescription(toUpdate.getDescription()+" (updated)");
        updated.setCalories(toUpdate.getCalories()+1);
        return updated;
    }

    public static Meal getNotFound() {
        return new Meal(10, LocalDateTime.now(), "not found", 1000);
    }


}
