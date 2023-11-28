package sk.uniba.fmph.dcs;

import java.util.List;

public class FakeFinalPointsCalculation {
    public static Points getPoints(List<FakeWallLine> wall){

        int points = 0;

        for(int i = 0; i < 5; i++){
            boolean rowIncomplete = false;
            for(int j = 0; j < 5; j++){
                if(wall.get(i).getTiles().get(j).isEmpty()){
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
                if(wall.get(j).getTiles().get(i).isEmpty()){
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
                if(wall.get(i).getTiles().get((i+j)%5).isEmpty()){
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
