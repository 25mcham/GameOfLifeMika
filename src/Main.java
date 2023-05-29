import processing.core.PApplet;
import java.util.Random;

// I chose the extension that makes cells spawn as either alive or dead randomly

public class Main extends PApplet {
    private static final int cell_size = 10; //final = constant, og = 10
    private static final int num_rows = 50;//assignment: 50
    private static final int num_columns = 100;// ssign: 100
    public static Main app;
    private Cell[][] griddy;// in instructions its "cells"
    private boolean doEvolve = false;
    private Random rand = new Random();
    public static void main(String[] args){
        PApplet.main("Main");
    }

    public Main(){
        super();
        app = this;
    }

    public void settings(){
        size(num_columns * cell_size, num_rows * cell_size);
    }

    public void setup(){
        griddy = new Cell[num_rows][num_columns];
        int exy = 0; // x position in the griddy
        int why = 0; // y position in the griddy
        Rules rules = new MooreRules(new int[]{3}, new int[]{2, 3});


        for(int r = 0; r < griddy.length; r++){
            for(int c = 0; c < griddy[0].length; c++){
                Cell dab = new Cell(exy, why, cell_size, r, c, CellState.DEAD, rules);
                if(rand.nextInt(2) %2 == 1 && r > 0 && r < griddy.length-1 && c > 0 && c < griddy[0].length-1 ){
                    dab = new Cell(exy, why, cell_size, r, c, CellState.ALIVE, rules);
                }
                exy += cell_size;
                griddy[r][c] = dab;
            }
            exy = 0;
            why += cell_size;
        }
    }

    public void draw(){
        if(doEvolve){
            applyRules();
            evolve();
        }
        for(int r = 0; r < griddy.length; r++){
            for(int c = 0; c < griddy[0].length; c++){
                griddy[r][c].display();
            }
        }
    }
    public void mouseClicked(){
        for(int r = 0; r < griddy.length; r++){
            for(int c = 0; c < griddy[0].length; c++){
                griddy[r][c].handleClick(mouseX, mouseY);
            }
        }
    }

    public void applyRules(){
        for(int r = 1; r < griddy.length-1; r++){
            for(int c = 1; c < griddy[0].length-1; c++){
                griddy[r][c].applyRules(griddy);
            }
        }
    }

    public void evolve(){
        for(int r = 0; r < griddy.length; r++){
            for(int c = 0; c < griddy[0].length; c++){
                griddy[r][c].evolve();
            }
        }
    }
    public void keyPressed(){
        doEvolve = !doEvolve;
    }
}