package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MealServlet.class);
    private static final MealService SERVICE = new MealService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String dateTime_str = req.getParameter("dateTime");
        String calories_str = req.getParameter("calories");
        String description_str = req.getParameter("description");

        Integer id = getId(req);
        LocalDateTime localDateTime =  LocalDateTime.parse(dateTime_str);
        Integer calories = Integer.parseInt(calories_str);

        Meal meal = new Meal(id, localDateTime, description_str, calories);

        SERVICE.update(meal);

        LOGGER.debug("Redirect to meals.jsp.");
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) {
            List<MealTo> mealTos = MealsUtil.createTos(SERVICE.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
            req.setAttribute("meals", mealTos);

            LOGGER.debug("forwarding to meals.jsp");

            req.getRequestDispatcher("meals.jsp").forward(req, resp);
            return;
        }

        switch (action.toLowerCase()) {
            case "delete":
                SERVICE.delete(getId(req));

                LOGGER.debug("Redirect to /meals");
                resp.sendRedirect("meals");
                break;
            case "update":
            case "create":
                Meal meal = action.equals("update") ? SERVICE.get(getId(req)):
                        new Meal(null, LocalDateTime.now(), "", MealsUtil.DEFAULT_CALORIES_PER_DAY);

                req.setAttribute("action", action);
                req.setAttribute("meal", MealsUtil.createTo(meal, false));

                LOGGER.debug("forwarding to mealForm.jsp");

                req.getRequestDispatcher("mealForm.jsp").forward(req, resp);
                break;
        }
    }

    private Integer getId(HttpServletRequest request) {
        try{
            return Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
