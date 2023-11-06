package edu.hitsz.UI;

import javax.swing.*;
import java.awt.*;


public class Play {

    public static final CardLayout cardLayout = new CardLayout(0,0);
    public static final JPanel cardPanel = new JPanel(cardLayout);
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static void main(String[] args) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cardPanel);
        StartMenu start = new StartMenu();
        cardPanel.add(start.getWholeMenu());
        frame.setVisible(true);


    }


}
