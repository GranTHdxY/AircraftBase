package edu.hitsz.aircraft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class EliteAircraftTest {

    private EliteAircraft eliteAircraft;
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        eliteAircraft = new EliteAircraft(1,1,1,1,10);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test getHp method")
    void getDirection() {
        System.out.println("**--- Test getHp method executed---**");
        assertEquals(1,eliteAircraft.getDirection());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test getSpeedY method")
    void getSpeedY() {
        System.out.println("**--- Test getSpeedY method executed---**");
        assertEquals(1,eliteAircraft.getSpeedY());
    }
}