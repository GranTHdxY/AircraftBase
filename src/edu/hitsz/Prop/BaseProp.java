package edu.hitsz.Prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;


public abstract class BaseProp extends AbstractFlyingObject {
    public BaseProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    //道具功能
    public abstract List<BaseProp> function(HeroAircraft heroAircraft, List<AbstractAircraft> enemies, List<BaseBullet> enemybullets);
}
