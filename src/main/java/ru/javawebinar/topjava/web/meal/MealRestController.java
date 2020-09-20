package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public MealTo create(Meal meal) {
        LOGGER.info("[user:{}] create {}", authUserId(), meal);
        ValidationUtil.checkNew(meal);
        return getMealTo(service.create(authUserId(), meal));
    }

    public List<MealTo> getAll() {
        LOGGER.info("[user:{}] get all", authUserId());
        return getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getAllFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredTos(service.getAllBetweenInclusive(
                authUserId(), startDate == null ? LocalDate.MIN : startDate, endDate == null ? LocalDate.MAX : endDate.plusDays(1)),
                authUserCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime, endTime == null ? LocalTime.MAX : endTime
        );
    }

    public MealTo get(int id) {
        LOGGER.info("[user:{}] get {}", authUserId(), id);
        return getMealTo(service.get(authUserId(), id));
    }

    public void update(Meal meal, int id) {
        LOGGER.info("[user:{}] update {}", authUserId(), meal);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
    }

    public void delete(int id) {
        LOGGER.info("[user:{}] delete {}", authUserId(), id);
        service.delete(authUserId(), id);
    }

    private MealTo getMealTo(Meal meal) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), false);
    }
}