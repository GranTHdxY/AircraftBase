package edu.hitsz.PropFactory;

import edu.hitsz.Prop.BaseProp;
import edu.hitsz.Prop.BoombProp;
import edu.hitsz.Prop.HpProp;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;

public class HpPropFactory implements PropFactory{
    public BaseProp createProp(AbstractAircraft enemyAircraft){
        return new HpProp(
                (int) (enemyAircraft.getLocationX()),
                (int) (enemyAircraft.getLocationY()),
                0,
                7
        );
    }
}
