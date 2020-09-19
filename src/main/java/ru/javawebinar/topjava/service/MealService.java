package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public class MealService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MealService.class);
    private static final MealRepository REPOSITORY = new InMemoryMealRepository();

    {
        for (Meal meal : MealsUtil.MEALS) {
            create(meal);
        }
    }

    public void create(Meal meal) {
        LOGGER.debug("Create {}.", meal);
        ValidationUtil.validate(meal);
        REPOSITORY.create(meal);
    }

    public List<Meal> getAll() {
        LOGGER.debug("Get all Meals.");
        return REPOSITORY.getAll();
    }

    public Meal get(Integer id) {
        LOGGER.debug("Get meal with id={}.", id);
        Meal meal = REPOSITORY.get(id);
        checkNull(id, meal);
        return meal;
    }

    public void update(Meal meal) {
        if (ValidationUtil.checkNew(meal)) {
            create(meal);
        } else {
            LOGGER.debug("Update {}.", meal);
            ValidationUtil.validate(meal);

            checkNull(meal.getId(), REPOSITORY.update(meal));
        }
    }

    public void delete(Integer id) {
        LOGGER.debug("Delete meal with id={}", id);
        checkNull(id, REPOSITORY.delete(id));
    }

    private void checkNull(Integer id, Meal meal) {
        if (meal == null) {
            NotFoundException notFoundException = new NotFoundException(String.format("Meal with id=%d not found!", id));
            LOGGER.error(notFoundException.getLocalizedMessage());
            throw notFoundException;
        }
    }
}
