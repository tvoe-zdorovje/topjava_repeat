package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

public class ValidationUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtil.class);

    public static void validate(Integer id, Meal meal) {
        validate(meal);
        validateID(id, meal);
    }

    public static void validate(Meal meal) {
        validateMeal(meal);
    }

    public static boolean checkNew(Meal meal) {
        return meal.getId()==null;
    }

    private static void validateMeal(Meal meal) {
        if (meal==null) {
            IllegalStateException exception = new IllegalStateException("meal is null!");
            LOGGER.error(exception.getLocalizedMessage(), exception);
            throw exception;
        }
    }

    private static void validateID(Integer id, Meal meal) {
        if (id!=null && !id.equals(meal.getId())) {
            IllegalStateException exception = new IllegalStateException("IDs don't match!");
            LOGGER.error(exception.getLocalizedMessage(), exception);
            throw exception;
        }
    }

}
