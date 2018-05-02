package cz.uhk.fim.sportstracker.Database;

import java.util.Collection;

import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.User;

public interface UserHelperInterface {
    User getUser(int userId);
    boolean addUser (User user);
    Collection<Activity> getUserActivities (int userId);
}
