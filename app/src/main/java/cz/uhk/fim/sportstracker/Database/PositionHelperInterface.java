package cz.uhk.fim.sportstracker.Database;

import cz.uhk.fim.sportstracker.Models.Position;

public interface PositionHelperInterface {
    Position getPosition (int positionId);
    boolean insertPosition (Position position);
    boolean deletePosition (int positionId);
    boolean deletePosition (Position position);
}
