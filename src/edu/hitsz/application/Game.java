package edu.hitsz.application;

import edu.hitsz.AircraftFactory.AircraftFactory;
import edu.hitsz.AircraftFactory.BossEnemyFactory;
import edu.hitsz.AircraftFactory.EliteAircraftFactory;
import edu.hitsz.AircraftFactory.MobEnemyFactory;
import edu.hitsz.Dao.AircraftWarRanklmpl;
import edu.hitsz.Dao.PlayerScore;
import edu.hitsz.Prop.BaseProp;
import edu.hitsz.Prop.BoombProp;
import edu.hitsz.PropFactory.BoombPropFactory;
import edu.hitsz.PropFactory.FirePropFactory;
import edu.hitsz.PropFactory.HpPropFactory;
import edu.hitsz.PropFactory.PropFactory;
import edu.hitsz.ThreadControl.MusicThread;
import edu.hitsz.UI.Play;
import edu.hitsz.UI.ScoreRank;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 30;
    private double frequencyInterval = 0.001;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private  final List<BaseProp> props;

    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5 ;
    /**
     * 当前得分
     */
    private int score = 0;

    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleDurationInterval = 40;
    private int EnemyCycleDuration;
    private int HeroCycleDuration;

    private int cycleTime = 0;

    /**
     * 背景图片
     */
    private BufferedImage BackGround;

    //敌机产生回合数
    public int Round = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;
    //敌机精英机产生的频率
    private double frequency = 0.3;
    private int BossScore = 0;
    //阈值
    private int threshold;
    //游戏难度
    public static String degree;
    // 用户ID
    private String userName;
    private double multiplier_increase;
    public static boolean TurnOnMusic = false;//所有音效的开启与关闭
    public static double EnemyBoost = 1;//敌机属性提升
    public double enemyBoostInterval = 1.01;//敌机属性提升





    public Game() {
        heroAircraft = HeroAircraft.getOnlyHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        if(TurnOnMusic){
            MusicThread.bgm = true;
            MusicThread.bossbgm = false;
            new MusicThread("src/videos/bgm.wav").start();
        }
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    ProduceEnemy(frequency);
                }


                //敌机射出子弹

                EnemyshootAction();
                HeroshootAction();
                if(getDegree()!="简单"){
                    if(time/20000 == Round){
                        frequency+=frequencyInterval;
                        cycleDuration-=cycleDurationInterval;
                        EnemyBoost*=enemyBoostInterval;
                        System.out.println("提高难度！精英机产生频率："+frequency+",敌机周期:"+ cycleDuration/20 +",敌机属性提升概率:"+EnemyBoost);
                        if(getDegree() == "困难"){
                            BossEnemyFactory.increaseBossHp(50);
                            System.out.println("Boss机血量增加！");
                        }
                        Round++;
                    }
                }


            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();



            //道具移动
            propMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();


            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;

                System.out.println("Game Over!");
                if(TurnOnMusic){
                    MusicThread.bgm = false;
                    MusicThread.bossbgm = false;
                    new MusicThread("src/videos/game_over.wav").start();
                }
                setuserName(JOptionPane.showInputDialog(null,"游戏结束，您的分数是:"+ String.valueOf(score) + "\n" + "请输入用户名"));
                printTable(score, userName);
                ScoreRank scoreRank = new ScoreRank();
                Play.cardPanel.add(scoreRank.gettableScrollPanel());
                Play.cardLayout.next(Play.cardPanel);
            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************
    public void printTable(int score, String userName){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String time = dateFormat.format(date);

        AircraftWarRanklmpl ranklmpl = new AircraftWarRanklmpl();
        //先读取文件中的信息 将文件中的数据赋于PlayScore 并加到列表中
        ranklmpl.readFile();
        //再清空文件中的信息进行重写
        ranklmpl.clearFile();
        //将当前用户的信息加入链表
        ranklmpl.doAdd(new PlayerScore(userName, score, time, degree));
        //排序
        List<PlayerScore> playerScores = ranklmpl.getAllScores();
        ranklmpl.writeFile(playerScores);
        System.out.println("********************************************************");
        System.out.println("                       得分排行榜                         ");
        System.out.println("********************************************************");
        int rank = 1;
        for(PlayerScore playerScore: playerScores){
            System.out.printf("第%d名：%12s %8d %16s %8s\n",rank, playerScore.getPlayerName(),
                    playerScore.getScore(),playerScore.getTime(),playerScore.gerDegree());
            rank +=1;
        }


    }

    //控制概率、周期、属性等
    public void setEnemyBoostInterval(double enemyBoostInterval) {
        this.enemyBoostInterval = enemyBoostInterval;
    }

    //设置难度
    public String setdegree(String degree){
        this.degree = degree;
        return degree;
    }
    //用户名
    public String setuserName(String name){
        this.userName = name;
        return userName;
    }
    //设置背景
    public BufferedImage setBackGround(BufferedImage BackGround){
        this.BackGround = BackGround;
        return BackGround;
    }
    //设置阈值
    public int setThreshold(int threshold){
        this.threshold = threshold;
        return threshold;
    }
    //设置敌机最大数量
    public int setenemyMaxNumber(int enemyMaxNumber){
        this.enemyMaxNumber = enemyMaxNumber;
        return enemyMaxNumber;
    };
    //设置产生频率
    public int setcycleDuration(int cycleDuration,int cycleDurationInterval){
        this.cycleDuration =cycleDuration;
        this.cycleDurationInterval = cycleDurationInterval;
        return cycleDuration;
    }

    public double setfrequency(double frequency, double frequencyInterval){
        this.frequency = frequency;
        this.frequencyInterval = frequencyInterval;
        return frequency;
    }
    public static boolean Music_Set(boolean flag){
        return TurnOnMusic = flag;
    }

    //返回难度
    public static String getDegree(){return degree;}

    public void ProduceEnemy(double frequency){
        AircraftFactory aircraftFactory;
        if(Math.random()>frequency) {
            aircraftFactory = new MobEnemyFactory();
            enemyAircrafts.add(aircraftFactory.createAircraft());
        }
        else if(Math.random()<=frequency) {
            aircraftFactory = new EliteAircraftFactory();
            enemyAircrafts.add(aircraftFactory.createAircraft());
        }
        if (BossScore>=threshold){
            aircraftFactory = new BossEnemyFactory();
            enemyAircrafts.add(aircraftFactory.createAircraft());
            BossScore = 0;
        }
    }


    public void increaseEnemyFrequency(float increaseAmount){
        frequency += increaseAmount;
    }


    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void EnemyshootAction() {
        // TODO 敌机射击
        for(AbstractAircraft enemyAircrafts:enemyAircrafts){
            enemyBullets.addAll(enemyAircrafts.shoot());
        }
    }

    private void HeroshootAction(){
        //英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }





    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propMoveAction(){
        for(BaseProp prop:props){
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            //子弹消失
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                //英雄机撞击到敌机子弹
                //英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());

                bullet.vanish();//子弹消失
            }
        }



        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    if(TurnOnMusic){new MusicThread("src/videos/bullet_hit.wav").start();}
                    bullet.vanish();
                }
                if (enemyAircraft.notValid()) {
                    // TODO 获得分数，产生道具补给
                    //道具产生的概率为0.3 每个道具产生的概率为0.1
                    if (enemyAircraft instanceof EliteAircraft && Math.random() >= 0.7) {
                        PropFactory propFactory;
                        //炸弹道具
                        if (Math.random() >= 0.7 && Math.random() < 0.8) {
                            propFactory = new BoombPropFactory();
                            props.add(propFactory.createProp(enemyAircraft));
                        }
                        //火力道具
                        else if (Math.random() >= 0.8 && Math.random() < 0.9) {
                            propFactory = new FirePropFactory();
                            props.add(propFactory.createProp(enemyAircraft));
                        }
                        //血量道具
                        else {
                            propFactory = new HpPropFactory();
                            props.add(propFactory.createProp(enemyAircraft));
                        }
                    }
                    else if (enemyAircraft instanceof BossEnemy) {
                        PropFactory propFactory;
                        //同时产生三种道具
                        propFactory = new BoombPropFactory();
                        props.add(propFactory.createProp(enemyAircraft));
                        propFactory = new FirePropFactory();
                        props.add(propFactory.createProp(enemyAircraft));
                        propFactory = new HpPropFactory();
                        props.add(propFactory.createProp(enemyAircraft));
                        if(TurnOnMusic){
                            MusicThread.bgm = true;
                            MusicThread.bossbgm = false;
                            new MusicThread("src/videos/bgm.wav").start();
                        }
                    }
                    score += 10;
                    BossScore += 10;
                }

                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (BaseProp prop : props) {
            if (heroAircraft.crash(prop)) {
                    prop.function(heroAircraft, enemyAircrafts, enemyBullets);
                    prop.vanish();
                    if(prop instanceof BoombProp){
                        score += 10;
                        BossScore += 10;
                    }
                }
            }
        }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(BackGround, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(BackGround, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);


        //绘制道具
        paintImageWithPositionRevised(g, props);


        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
