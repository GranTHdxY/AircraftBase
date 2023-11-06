package edu.hitsz.aircraft;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;



class HeroAircraftTest {

    private HeroAircraft heroAircraft1;
    private HeroAircraft heroAircraft2;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        heroAircraft1 = HeroAircraft.getOnlyHeroAircraft();
        heroAircraft2 = HeroAircraft.getOnlyHeroAircraft();

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test getOnlyHeroAircraft method")
    void getOnlyHeroAircraft() {
        System.out.println("**--- Test getOnlyHeroAircraft method executed---**");
        assertEquals(heroAircraft1, heroAircraft2);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test increaseHp method")
    void decreaseHp() {
        System.out.println("**--- Test increaseHp method executed ---**");
        heroAircraft1.decreaseHp(30);
        assertEquals(70,heroAircraft1.getHp());
        heroAircraft1.decreaseHp(-100);
        assertEquals(170,heroAircraft1.getHp());//decreaspHp没有设置上限
        heroAircraft1.decreaseHp(200);
        assertEquals(0,heroAircraft1.getHp());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test changeShootNum method")
    void changeShootNum(){
        System.out.println("**--- Test changeShootNum method executed ---**");
        assertEquals(1, heroAircraft1.getShootNum());
        heroAircraft1.changeShootNum();
        assertEquals(3, heroAircraft1.getShootNum());
    }
}