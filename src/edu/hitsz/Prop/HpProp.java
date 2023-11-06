package edu.hitsz.Prop;

import edu.hitsz.ThreadControl.MusicThread;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

import static edu.hitsz.application.Game.TurnOnMusic;
public class HpProp extends BaseProp{
    public HpProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }
        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    public List<BaseProp> function(HeroAircraft heroAircraft, List<AbstractAircraft> enemies, List<BaseBullet> enemybullets){
        heroAircraft.increaseHp(30);
        if(TurnOnMusic){new MusicThread("src/videos/get_supply.wav").start();}
        System.out.println("HpSupply Active!");
        return null;
    }
}