package edu.hitsz.Dao;

import edu.hitsz.application.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AircraftWarRanklmpl implements AircraftWarRankDao{
    String rankTable;
    private List<PlayerScore> playerScores;


    public String setRankTable(String degree){
        if(degree == "简单"){
            rankTable =  "EasyRankList.txt";
        }
        else if(degree == "普通"){
            rankTable =  "NormalRankList.txt";
        }
        else if (degree == "困难") {
            rankTable =  "HardRankList.txt";
        }
        return rankTable;
    }



    File file = new File(setRankTable(Game.getDegree()));
    public void doAdd(PlayerScore PlayerScores){playerScores.add(PlayerScores);
    }
    public void doDelete(int index){ playerScores.remove(index);}
    @Override
    public List getAllScores() {
        Collections.sort(playerScores, new Comparator<PlayerScore>() {
            @Override
            public int compare(PlayerScore t1, PlayerScore t2) {
                return t2.getScore() - t1.getScore();
            }

    });
        return this.playerScores;
    }



    /**读取文件**/
    public void readFile(){
        playerScores = new ArrayList<PlayerScore>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine())!=null){
                String[] userIMessage = line.split(" ");
                PlayerScore playerScore = new PlayerScore(userIMessage[0], Integer.parseInt(userIMessage[1]), userIMessage[2]+ ' ' + userIMessage[3], userIMessage[4]);
                playerScores.add(playerScore);
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**写入文件**/
    public void writeFile(List<PlayerScore> playerScores){
        try{
            FileWriter writer = new FileWriter(file, true);
            for(PlayerScore playerScore:playerScores){
                String imessage =  playerScore.getPlayerName() + ' '
                                  + playerScore.getScore() + ' ' + playerScore.getTime() + ' ' + playerScore.gerDegree() + '\n';
            writer.write(imessage);
            }
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    /**清除文件**/
    public void clearFile(){
        try{
            FileWriter writer = new FileWriter(file);
            //rewrite
            writer.write("");
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }
}
