package edu.hitsz.TemplatePattern;

import edu.hitsz.AircraftFactory.BossEnemyFactory;
import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;

public class HardMode extends Game {
    public HardMode() {
        //设置游戏难度
        setdegree("困难");
        //设置背景
        setBackGround(ImageManager.BACKGROUND_IMAGE_HARD);
        //设置Boss机产生阈值
        setThreshold(400);
        //设置敌机最大数量
        setenemyMaxNumber(7);
        //设置初始周期
        setcycleDuration(500,30);
        //设置精英敌机产生频率
        setfrequency(0.5, 0.01);
        //设置boss机血量
        BossEnemyFactory.BossHp = 200;
        //设置敌机属性增加
        setEnemyBoostInterval(1.1);
    }

}
