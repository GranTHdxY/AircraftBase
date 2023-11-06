package edu.hitsz.aircraft;

import edu.hitsz.Strategy.Strategy;
import edu.hitsz.Strategy.straightLineShoot;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected int direction;
    protected int shootNum;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void increaseHp(int increase){
        hp += increase;
        if(hp>=maxHp){
            hp = maxHp;
        }
    }


    public int getHp() {
        return hp;
    }
    public int getDirection() {return direction;}
    public int getShootNum(){return shootNum;}

    public class Context{
        private Strategy strategy;
        public  Context(Strategy strategy){
            this.strategy = strategy;
        }

        public void setStrategy(Strategy strategy) {
            this.strategy = strategy;
        }

        public List<BaseBullet> executeStrategy(AbstractAircraft abstractAircraft){
            return strategy.shootWay(abstractAircraft);
        }
    }
    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();


}


