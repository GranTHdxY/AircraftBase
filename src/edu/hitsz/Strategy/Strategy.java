package edu.hitsz.Strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface Strategy {
    List<BaseBullet> shootWay(AbstractAircraft abstractAircraft);
}
