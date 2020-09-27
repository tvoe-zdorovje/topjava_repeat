package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Meal m SET m.dateTime=?2, m.description=?3, m.calories=?4 WHERE m.id=?1 AND m.user.id=?5")
    int update(int id, LocalDateTime localDateTime, String description, int calories, int userId);

    @Query(name=Meal.GET)
    Meal get(@Param("id") int id, @Param("userId")int userId);

    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    int delete(@Param("id")int id, @Param("userId") int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetween(@Param("startDateTime")LocalDateTime startDateTime, @Param("endDateTime")LocalDateTime endDateTime, @Param("userId") int userId);

    @Query(name = Meal.ALL_SORTED)
    List<Meal> getAll(@Param("userId") int userId);

    @EntityGraph(value = Meal.GRAPH, type = EntityGraph.EntityGraphType.FETCH)
    @Query(name=Meal.GET)
    Meal getWithUser(@Param("id") int id, @Param("userId") int userId);
}
