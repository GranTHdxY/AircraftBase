package edu.hitsz.aircraft;

import edu.hitsz.Strategy.Strategy;
import edu.hitsz.Strategy.scatterShoot;
import edu.hitsz.Strategy.straightLineShoot;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class EliteAircraft extends AbstractAircraft{
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
    private int direction = 1;

    public EliteAircraft(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }


    @Override
    public List<BaseBullet> shoot() {
        Context context = new Context(new straightLineShoot());
        return context.executeStrategy(this);
    }
    public int getDirection() {return direction;}
    public int getShootNum(){return shootNum;}

    @Override
    public void update(){this.vanish();}

}
