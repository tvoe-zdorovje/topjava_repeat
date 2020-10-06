package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL+"/{id}").buildAndExpand(created).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }
//refactor filtering by LocalDate and LocalTime PLEASE!!!
    @GetMapping(params = {"from", "to"})
    public List<MealTo> getBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime from,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime to){
        LocalDate startDate = from == null ? null : from.toLocalDate();
        LocalDate endDate = from == null ? null : to.toLocalDate();
        LocalTime startTime = from == null ? null : from.toLocalTime();
        LocalTime endTime = from == null || to.toLocalTime().compareTo(LocalTime.MIN)==0 ? null : to.toLocalTime();
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

}