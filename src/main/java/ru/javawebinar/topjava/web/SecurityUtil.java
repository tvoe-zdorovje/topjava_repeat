package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.UsersUtil.USER_ID;

public class SecurityUtil {

    private static int loggedUserId = USER_ID;

    public static void loginAs(int id) {
        loggedUserId = id;
    }

    public static int authUserId() {
        return loggedUserId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}