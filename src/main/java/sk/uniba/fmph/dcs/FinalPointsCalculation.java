package sk.uniba.fmph.dcs;

import java.util.Optional;

public class FinalPointsCalculation {

    public static Points getPoints(Optional<Tile>[][] wall){

        int points = 0;

        for(int i = 0; i < 5; i++){
            boolean rowIncomplete = false;
            for(int j = 0; j < 5; j++){
                if(!wall[i][j].isPresent()){
                    rowIncomplete = true;
                    break;
                }
            }
            if(!rowIncomplete){
                points += 2;
            }
        }

        for(int i = 0; i < 5; i++){
            boolean columnIncomplete = false;
            for(int j = 0; j < 5; j++){
                if(!wall[j][i].isPresent()){
                    columnIncomplete = true;
                    break;
                }
            }
            if(!columnIncomplete){
                points += 7;
            }
        }

        for(int i = 0; i < 5; i++){
            boolean colorIncomplete = false;
            for(int j = 0; j < 5; j++){
                if(!wall[i][(i+j) % 5].isPresent()){
                    colorIncomplete = true;
                    break;
                }
            }
            if(!colorIncomplete){
                points += 10;
            }
        }

        return new Points(points);

    }

}
