package cz.uhk.fim.sportstracker.Database;

import java.util.List;

import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.User;

public interface UserHelperInterface {
    User getUser(int userId);
    User getUser(String login);
    boolean insertUser(User user);
    List<Activity> getUserActivities (int userId);
}
