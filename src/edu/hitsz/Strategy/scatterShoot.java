package edu.hitsz.Strategy;

import edu.hitsz.ThreadControl.MusicThread;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

import static edu.hitsz.application.Game.TurnOnMusic;

public class scatterShoot implements Strategy{
    /**攻击方式
     * 直射
     * /


     /**
     * 子弹伤害
     */
    private int power = 30;


    public List<BaseBullet> shootWay(AbstractAircraft abstractAircraft){
        List<BaseBullet> res = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
        int speedX = 0;
        int speedY = abstractAircraft.getSpeedY() + abstractAircraft.getDirection()*10;
        BaseBullet bullet;
        if(abstractAircraft instanceof HeroAircraft){
            for(int i=0; i<abstractAircraft.getShootNum(); i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                bullet = new HeroBullet(x + (i * 2 - abstractAircraft.getShootNum() + 1) * 10, y, speedX+(i * 2 * abstractAircraft.getShootNum() / (abstractAircraft.getShootNum() - 1) -abstractAircraft.getShootNum()) * 1, speedY, power);
                if(TurnOnMusic){new MusicThread("src/videos/bullet.wav").start();}
                res.add(bullet);
            }
            return res;
        }
        for(int i=0; i<abstractAircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i * 2 - abstractAircraft.getShootNum() + 1) * 10, y, speedX+(i * 2 * abstractAircraft.getShootNum() / (abstractAircraft.getShootNum() - 1) -abstractAircraft.getShootNum()) * 1, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
