package edu.hitsz.AircraftFactory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteAircraftFactory implements AircraftFactory{
    public AbstractAircraft createAircraft(){
        return new EliteAircraft((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_AIRCRAFT_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                10,
                30 * (int)Game.EnemyBoost
        );
    }
}
