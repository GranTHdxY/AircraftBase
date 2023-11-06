package edu.hitsz.PropFactory;

import edu.hitsz.Prop.BaseProp;
import edu.hitsz.aircraft.AbstractAircraft;

public interface PropFactory {
    public abstract BaseProp createProp(AbstractAircraft enemyAircraft);
}
