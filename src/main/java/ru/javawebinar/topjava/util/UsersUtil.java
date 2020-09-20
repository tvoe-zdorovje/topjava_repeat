package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.Role.ADMIN;
import static ru.javawebinar.topjava.model.Role.USER;

public class UsersUtil {

    public static final int ADMIN_ID = 10_000;
    public static final int USER_ID = 10_001;

    public static final List<User> USERS = Arrays.asList(
            new User(USER_ID, "User", "user@yandex.by", "userpass", USER),
            new User(ADMIN_ID, "Admin", "admin@gmail.com", "adminpass", USER, ADMIN)
    );
}
