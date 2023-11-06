package edu.hitsz.aircraft;

import edu.hitsz.Strategy.Strategy;
import edu.hitsz.Strategy.scatterShoot;
import edu.hitsz.Strategy.straightLineShoot;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractAircraft {
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 3;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        Context context = new Context(new scatterShoot());
        return context.executeStrategy(this);
    }
    public int getDirection() {return direction;}
    public int getShootNum(){return shootNum;}
    public void update(){
        decreaseHp(50);
    }

}
