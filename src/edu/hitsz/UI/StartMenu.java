package edu.hitsz.UI;

import edu.hitsz.TemplatePattern.HardMode;
import edu.hitsz.TemplatePattern.NormalMode;
import edu.hitsz.TemplatePattern.SimpleMode;
import edu.hitsz.application.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    private JButton 简单模式Button;
    private JButton 普通模式Button;
    private JButton 困难模式Button;
    private JButton 排行榜Button;
    private JComboBox MusicSelect;
    private JPanel WholeMenu;
    private JPanel Music;
    private JPanel SelectMode;
    private JPanel empty1;
    private JPanel Start;
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartMenu");
        frame.setContentPane(new StartMenu().WholeMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

public StartMenu() {
    简单模式Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            SimpleMode game = new SimpleMode();
            game.action();
            Play.cardPanel.add(game);
            Play.cardLayout.next(Play.cardPanel);
        }
    });
    普通模式Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            NormalMode game = new NormalMode();
            game.action();
            Play.cardPanel.add(game);
            Play.cardLayout.next(Play.cardPanel);
        }
    });
    困难模式Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            HardMode game = new HardMode();
            game.action();
            Play.cardPanel.add(game);
            Play.cardLayout.next(Play.cardPanel);
        }
    });
    排行榜Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ScoreRank scoreRank = new ScoreRank();
            Play.cardPanel.add(scoreRank.gettableScrollPanel());
            Play.cardLayout.next(Play.cardPanel);
        }
    });
    MusicSelect.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(MusicSelect.getSelectedItem().equals("播放我的音乐")) {
                Game.Music_Set(true);
            }
            else if (MusicSelect.getSelectedItem().equals("关掉我的音乐")){
                Game.Music_Set(false);
            }
        }
    });

}

    public JPanel getWholeMenu() {
        return WholeMenu;
    }
    public JButton get简单模式Button(){return 简单模式Button;}

}
