package edu.hitsz.aircraft;

import edu.hitsz.Strategy.Strategy;
import edu.hitsz.Strategy.scatterShoot;
import edu.hitsz.Strategy.straightLineShoot;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private static HeroAircraft OnlyHeroAircraft = new HeroAircraft( Main.WINDOW_WIDTH / 2,
            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
            0, 0, 100);
    public static HeroAircraft getOnlyHeroAircraft(){
        return OnlyHeroAircraft;
    }
    /**攻击方式 */
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = -1;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    public HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot(){
        if(shootNum == 1){
            Context context = new Context(new straightLineShoot());
            return context.executeStrategy(this);
        }
        else{
            Context context = new Context(new scatterShoot());
            return context.executeStrategy(this);
        }
    }

    public int changeShootNum(int num){
        return shootNum = num;
    }

    public int getDirection() {return direction;}
    public int getShootNum(){return shootNum;}

}
