package edu.hitsz.Prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HpPropTest {

    private HpProp hpProp1;
    private HeroAircraft heroAircraft1;

    private HpProp hpProp2;
    private HeroAircraft heroAircraft2;

    @org.junit.jupiter.api.BeforeEach
    void setUP(){
        System.out.println("**--- Executed before each test method in this class ---**");
        hpProp1 = new HpProp(1,1 ,0, 2);
        heroAircraft1 = new HeroAircraft(1,1,0,2,100);//构建一个与道具位置相同的英雄机
        hpProp2 = new HpProp(0,0,0, 2);
        heroAircraft2 = new HeroAircraft(100,10,0,2,100);//构建一个与道具位置不同的英雄机
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test crash method")
    void crash() {
        System.out.println("**--- Test crash method executed ---**");
        assertTrue(hpProp1.crash(heroAircraft1));
        assertFalse(hpProp2.crash(heroAircraft2));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test getLocationX method")
    void getLocationX() {
        System.out.println("**--- Test getLocationX method executed ---**");
        assertEquals(1,hpProp1.getLocationX());

    }
}