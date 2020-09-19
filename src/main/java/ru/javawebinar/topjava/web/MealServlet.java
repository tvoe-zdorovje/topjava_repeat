package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealTo> mealTos = MealsUtil.createTos(MealsUtil.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        req.setAttribute("meals", mealTos);

        LOGGER.debug("forwarding to meals.jsp");

        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
