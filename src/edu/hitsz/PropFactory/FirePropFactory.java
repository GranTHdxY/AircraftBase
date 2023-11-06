package edu.hitsz.PropFactory;

import edu.hitsz.Prop.BaseProp;
import edu.hitsz.Prop.BoombProp;
import edu.hitsz.Prop.FireProp;
import edu.hitsz.aircraft.AbstractAircraft;

public class FirePropFactory implements PropFactory{
    public BaseProp createProp(AbstractAircraft enemyAircraft){
        return new FireProp(
                (int) (enemyAircraft.getLocationX()),
                (int) (enemyAircraft.getLocationY()),
                0,
                6
        );
    }
}
