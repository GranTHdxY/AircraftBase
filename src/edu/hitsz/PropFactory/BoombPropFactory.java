package edu.hitsz.PropFactory;

import edu.hitsz.Prop.BaseProp;
import edu.hitsz.Prop.BoombProp;
import edu.hitsz.aircraft.AbstractAircraft;

public class BoombPropFactory implements PropFactory{
    public BaseProp createProp(AbstractAircraft enemyAircraft){
        return new BoombProp(
                (int) (enemyAircraft.getLocationX()),
                (int) (enemyAircraft.getLocationY()),
                0,
                4
        );
    }
}
