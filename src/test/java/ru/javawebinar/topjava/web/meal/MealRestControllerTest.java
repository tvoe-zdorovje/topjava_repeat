package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.TestMatcher;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

class MealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = MealRestController.REST_URL + "/";
    @Autowired
    MealService service;

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER_ID));
    }

    @Test
    void update() throws Exception {
        perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andDo(print())
                .andExpect(status().isNoContent());

        Meal updated = service.get(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(updated, getUpdated());
    }

    @Test
    void createWithLocation() throws Exception {
        MvcResult result = perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Meal created = TestUtil.readFromJsonMvcResult(result, Meal.class);
        Meal expected = getNew();
        expected.setId(created.id());

        MEAL_MATCHER.assertMatch(created, expected);
        MEAL_MATCHER.assertMatch(service.get(expected.id(), USER_ID), expected);
    }

    private static final TestMatcher<MealTo> TO_TEST_MATCHER = TestMatcher.usingFieldsComparator(MealTo.class);

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_TEST_MATCHER.contentJson(MEAL_TOS));
    }

    @ParameterizedTest
    @CsvSource({
            "2020-01-30,10:00,2020-01-30,14:00",
            "2020-01-31,,2020-01-31,10:00",
            ",10:00,2020-01-31,",
            ",,,"
    })
    void getBetween(String startDate, String startTime, String endDate, String endTime) throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL+"filter?startDate={startDate}&startTime={startTime}" +
                "&endDate={endDate}&endTime={endTime}", startDate, startTime, endDate, endTime))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_TEST_MATCHER.contentJson(getFilteredTos(startDate, startTime, endDate, endTime)));
    }

    private List<MealTo> getFilteredTos(String startDate, String startTime, String endDate, String  endTime) {
        return MealsUtil.getFilteredTos(service.getBetweenInclusive(parseLocalDate(startDate), parseLocalDate(endDate),
                USER.id()), USER.getCaloriesPerDay(), parseLocalTime(startTime), parseLocalTime(endTime));
    }
    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEAL1));
    }
}