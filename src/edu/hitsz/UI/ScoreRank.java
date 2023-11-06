package edu.hitsz.UI;

import edu.hitsz.Dao.AircraftWarRanklmpl;
import edu.hitsz.Dao.PlayerScore;
import edu.hitsz.application.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ScoreRank {
    private JPanel tableScrollPanel;
    private JTable ScoreTable;
    private JButton 删除Button;
    private JPanel JpanelLeft;
    private JPanel JpanelRight;
    private JScrollPane Title;
    private JButton 重新开始Button;

    public ScoreRank() {
        AircraftWarRanklmpl aircraftWarRanklmpl = new AircraftWarRanklmpl();
        aircraftWarRanklmpl.setRankTable(Game.getDegree());
        aircraftWarRanklmpl.readFile();
        List<PlayerScore> playerScores = aircraftWarRanklmpl.getAllScores();
        String[][] tableData = new String[35][35];

        int i = 1;
        for (PlayerScore playerScore : playerScores) {
            if(playerScore.getPlayerName()!= "null"){
            String[] temp = {String.valueOf(i), playerScore.getPlayerName(), String.valueOf(playerScore.getScore()), playerScore.getTime(), playerScore.gerDegree()};
            tableData[i - 1] = temp;
            i++;}
        }

        String[] columnName = {"名次", "用户ID", "分数", "时间", "难度"};

        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        ScoreTable.setModel(model);

        删除Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = ScoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(删除Button,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                    //删除历史记录
                    aircraftWarRanklmpl.doDelete(row);
                    aircraftWarRanklmpl.clearFile();
                    aircraftWarRanklmpl.writeFile(playerScores);
                    ScoreTable.setModel(model);
                }
            }
        });
        重新开始Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("ScoreRank");
        frame.setContentPane(new ScoreRank().tableScrollPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel gettableScrollPanel() {
        return tableScrollPanel;
    }

}
