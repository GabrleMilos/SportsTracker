package cz.uhk.fim.sportstracker.Database;

import cz.uhk.fim.sportstracker.Models.Position;

public interface PositionHelperInterface {
    boolean insertPosition (Position position, long activityId);
}
