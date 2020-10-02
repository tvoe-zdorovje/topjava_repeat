package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    @ModelAttribute
    public void getAll(Model model) {
        model.addAttribute("meals", getAll());
    }

    @GetMapping(params = {"startDate", "endDate", "startTime", "endTime"})
    @ModelAttribute
    public void getBetween(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate endDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam LocalTime startTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam LocalTime endTime, Model model) {
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
    }

    @GetMapping("/update/{id}")
    public String updateMeal(@PathVariable int id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("meal", get(id));
        return "mealForm";
    }

    @PostMapping("/update/{id}")
    public String updateMeal(@ModelAttribute("meal") Meal meal, @PathVariable int id) {
        update(meal, id);
        return "redirect:/meals";
    }


    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable int id) {
        delete(id);
        return "redirect:/meals";
    }

    @PostMapping("/create")
    public String createNew(@ModelAttribute("meal") Meal meal) {
        create(meal);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createNew(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "description", SecurityUtil.authUserCaloriesPerDay()));
        return "mealForm";
    }
}
