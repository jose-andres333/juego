/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author jose
 */
public class MapGenerator {
    public int map[][];//CREAMOS UN ARREGLO
    public int brickWidth;//ANCHO DEL LADRILO
    public int brickHeight;//ALTO DEL LADRILLO
    //CONMSTRUCTOR
    public MapGenerator(int row, int col){
        map = new int[row][col];//ROW = FILA, COL = COLUMNA
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = 1;
            }
        }
        
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    
    public void draw(Graphics2D g){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    g.setColor(Color.WHITE);//COLOR DEL LADRILLO
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    
                    // esto es solo para mostrar ladrillos separados, el juego aún se puede ejecutar sin él
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);//COLOR DEL BORDE DEL LADRILLO
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    
                }
                
            }    
        }
    }
    public void setBrickValue(int value, int row, int col){//VALOR DEL LADRILLO
        map[row][col] = value;
    }
}
