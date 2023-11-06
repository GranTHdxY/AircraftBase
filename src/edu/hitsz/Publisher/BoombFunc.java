package edu.hitsz.Publisher;

import edu.hitsz.Prop.BaseProp;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.ArrayList;
import java.util.List;

public class BoombFunc {

    private List<AbstractFlyingObject> VictimList = new ArrayList<>();

    public void addViewer(AbstractFlyingObject abstractFlyingObject){
        VictimList.add(abstractFlyingObject);
    }

    public void removeViewer(AbstractFlyingObject abstractFlyingObject){
        VictimList.remove(abstractFlyingObject);
    }

    public void Notify(){
        for (AbstractFlyingObject abstractFlyingObject:VictimList){
            abstractFlyingObject.update();
        }
    }

    public void Boom(){
        Notify();
    }
}
