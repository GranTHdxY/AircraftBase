package edu.hitsz.TemplatePattern;

import edu.hitsz.AircraftFactory.BossEnemyFactory;
import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;

public class NormalMode extends Game {
    public NormalMode() {
        //设置游戏难度
        setdegree("普通");
        //设置背景
        setBackGround(ImageManager.BACKGROUND_IMAGE_NORMAL);
        //设置Boss机产生阈值
        setThreshold(500);
        //设置敌机最大数量
        setenemyMaxNumber(6);
        //设置初始周期
        setcycleDuration(600,35);
        //设置精英敌机产生频率
        setfrequency(0.3, 0.01);
        //设置boss机血量
        BossEnemyFactory.BossHp = 100;
        //设置敌机属性增加
        setEnemyBoostInterval(1.03);
    }

}


