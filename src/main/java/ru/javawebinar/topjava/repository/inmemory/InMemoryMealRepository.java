package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UsersUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.USER_MEALS.forEach(meal -> save(UsersUtil.USER_ID, meal));
        MealsUtil.ADMIN_MEALS.forEach(meal -> save(UsersUtil.ADMIN_ID, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("[user:{}] save {}", userId, meal);

        repository.putIfAbsent(userId, new HashMap<>());
        Map<Integer, Meal> meals = repository.get(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("[user:{}] delete {}", userId, id);

        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("[user:{}]  get {}", userId, id);

        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("[user:{}] get all", userId);
        return getAllBetweenInclusive(userId, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public List<Meal> getAllBetweenInclusive(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("[user:{}] get all from {} to {} (inclusive)", userId, startDate, endDate);

        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? new ArrayList<>() : meals.values().stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

