package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@Sql(value = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() {
        Meal actual = service.get(MEAL_1.getId(), USER_ID);
        MEAL_MATCHER.assertMatch(actual, MEAL_1);
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1.getId(), ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(123, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_1.getId(), USER_ID);
        assertNull(repository.get(MEAL_1.getId(), USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_1.getId(), ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(123, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> actual = service.getBetweenInclusive(LocalDate.of(2020, 1, 31), LocalDate.of(2020, 1, 31), USER_ID);
        MEAL_MATCHER.assertMatch(actual, MEAL_7, MEAL_6, MEAL_5, MEAL_4);

        actual = service.getBetweenInclusive(LocalDate.of(2020, 1, 30), LocalDate.of(2020, 1, 31), USER_ID);
        MEAL_MATCHER.assertMatch(actual, USER_MEALS);

        actual = service.getBetweenInclusive(LocalDate.of(2020, 1, 30), LocalDate.of(2020, 1, 30), USER_ID);
        MEAL_MATCHER.assertMatch(actual, MEAL_3, MEAL_2, MEAL_1);

        actual = service.getBetweenInclusive(null, null, USER_ID);
        MEAL_MATCHER.assertMatch(actual, USER_MEALS);

        actual = service.getBetweenInclusive(null, LocalDate.of(2020, 1, 29), USER_ID);
        MEAL_MATCHER.assertMatch(actual, new ArrayList<>());
    }

    @Test
    public void getAll() {
        List<Meal> actual = service.getAll(USER_ID);
        MEAL_MATCHER.assertMatch(actual, USER_MEALS);
    }

    @Test
    public void update() {
        Meal updated = getUpdated(MEAL_1);
        service.update(updated, USER_ID);
        MEAL_MATCHER.assertMatch(service.get(MEAL_1.getId(), USER_ID), updated);
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_1.getId(), ADMIN_ID));
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(123, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MEAL_MATCHER.assertMatch(service.get(newMeal.getId(), ADMIN_ID), newMeal);

    }
}