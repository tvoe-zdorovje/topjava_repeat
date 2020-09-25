package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.user.id=:user_id AND m.id=:id"),
        @NamedQuery(name = Meal.GET_BY_ID, query = "SELECT m FROM Meal m WHERE m.user.id=:user_id AND m.id=:id"),
        @NamedQuery(name = Meal.GET_ALL, query = "SELECT m FROM Meal m WHERE m.user.id=:user_id ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN_HALF_OPEN, query = "SELECT m FROM Meal m WHERE m.user.id=:user_id AND m.dateTime>=:start AND m.dateTime<:end ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.UPDATE, query = "UPDATE Meal m SET m.dateTime=:date_time, m.description=:description, m.calories=:calories WHERE m.user.id=:user_id AND m.id=:id")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(name = "meals_unique_user_datetime_idx", columnNames = {"user_id", "date_time"})})
public class Meal extends AbstractBaseEntity {
    public static final String DELETE = "meals.delete";
    public static final String GET_BY_ID = "meals.get.id";
    public static final String GET_ALL = "meals.get.all";
    public static final String GET_BETWEEN_HALF_OPEN = "meals.get.between.half_open";
    public static final String UPDATE = "meals.update";

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotBlank
    @Column(name = "description", nullable = false)
    @Length(min = 3, max = 100)
    private String description;

    @NotNull
    @Range(min = 10, max = 5000)
    @Column(name = "calories", nullable = false)
    private int calories;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "meals_user_id_fkey", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"))
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
