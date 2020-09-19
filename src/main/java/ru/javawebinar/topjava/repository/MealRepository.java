package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {

    void create(Meal meal);

    Meal get(Integer id);

    List<Meal> getAll();

    Meal update(Meal meal);

    Meal delete(Integer id);

}
