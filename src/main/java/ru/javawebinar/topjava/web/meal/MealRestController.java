package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
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
//thanks!
//    @GetMapping(params = {"startDate", "startTime", "endDate", "endTime"})
    @GetMapping("/filter")
    public List<MealTo> getBetween(/*@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)*/ @RequestParam LocalDate startDate,
                                   /*@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)*/ @RequestParam LocalDate endDate,
                                   /*@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)*/ @RequestParam LocalTime startTime,
                                   /*@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)*/ @RequestParam LocalTime endTime){
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

}