package cz.uhk.fim.sportstracker.Database;

import java.util.List;

import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.Position;

public interface ActivityHelperInterface {
    Activity getActivity (int activityId);
    long insertActivity (Activity activity, int userId);
    boolean deleteActivity (Activity activity);
    boolean deleteActivity (int activityId);
    List<Position> getActivityPositions (int activityId);
}
