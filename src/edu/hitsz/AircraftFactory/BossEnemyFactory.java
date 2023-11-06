package edu.hitsz.AircraftFactory;

import edu.hitsz.ThreadControl.MusicThread;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements AircraftFactory{

    public static int BossHp;
    public AbstractAircraft createAircraft(){
        if(Game.TurnOnMusic){
            MusicThread.bgm = false;
            MusicThread.bossbgm = true;
            new MusicThread("src/videos/bgm_boss.wav").start();
        }
        return new BossEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                1,
                0,
                BossHp);
    }
    public static void increaseBossHp(int increase){
        BossHp+=increase;
    }
}

