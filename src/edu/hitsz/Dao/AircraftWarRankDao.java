package edu.hitsz.Dao;

import java.util.List;

public interface AircraftWarRankDao {
    List<PlayerScore> getAllScores();
    void doAdd(PlayerScore PlayerScores);

}
