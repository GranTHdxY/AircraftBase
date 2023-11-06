package edu.hitsz.TemplatePattern;

import edu.hitsz.AircraftFactory.AircraftFactory;
import edu.hitsz.AircraftFactory.BossEnemyFactory;
import edu.hitsz.AircraftFactory.EliteAircraftFactory;
import edu.hitsz.AircraftFactory.MobEnemyFactory;
import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;

public class SimpleMode extends Game {
    int INF = 100000000;
    public SimpleMode(){
        //设置游戏难度
        setdegree("简单");
        //设置背景
        setBackGround(ImageManager.BACKGROUND_IMAGE_EASY);
        //设置Boss机产生阈值（INF很大 不可能达到 不可能产生Boss机）
        setThreshold(INF);
        //设置敌机最大数量
        setenemyMaxNumber(5);
        //设置周期
        setcycleDuration(600,40);
    }


}
