package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private static final AtomicInteger idCounter = new AtomicInteger(100_000);
    private static final Map<Integer, Meal> REPOSITORY = new ConcurrentHashMap<>();

    @Override
    public void create(Meal meal) {
        meal.setId(idCounter.getAndIncrement());
        REPOSITORY.put(meal.getId(), new Meal(meal));
    }

    @Override
    public Meal get(Integer id) {
        Meal meal = REPOSITORY.get(id);
        return meal == null ? null : new Meal(meal);
    }

    @Override
    public List<Meal> getAll() {
        return REPOSITORY.values().stream().map(Meal::new).collect(Collectors.toList());
    }

    @Override
    public Meal update(Meal meal) {
        return REPOSITORY.put(meal.getId(), new Meal(meal));
    }

    @Override
    public Meal delete(Integer id) {
        return REPOSITORY.remove(id);
    }
}
