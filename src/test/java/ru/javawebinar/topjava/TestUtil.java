package ru.javawebinar.topjava;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {
    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action.andReturn()), clazz);
    }

    public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(result), clazz);
    }

    public static <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValues(getContent(result), clazz);
    }

    public static List<Meal> toMeal(List<MealTo> tos) {
        return tos.stream().map(TestUtil::toMeal).collect(Collectors.toList());
    }

    public static Meal toMeal(MealTo to) {
        return new Meal(to.getId(), to.getDateTime(), to.getDescription(), to.getCalories());
    }
}
