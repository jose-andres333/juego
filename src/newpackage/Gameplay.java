/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jose
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    
    private boolean play = false; //NUESTRO JUEGO ESTA EN FALSE
    private int score = 0;        //CREAMOS NUESTRA VARIABLE PUNTACION
    
    private int totalBricks = 21; //CREAMOS EL TOTAL DE LADRILLOS
    
    private Timer timer; //CREAMOS NUESTRO TEMPORISADOR
    private int delay = 8; //CREAMOS NUESTRO RETRASAR
    
    private int playerX = 310;
    
    private int ballposX = 120; //POSICION DE LA PELOTA EN X
    private int ballposY = 350; //POSICION DE LA PELOTA EN Y
    private int ballXdir = -1; //DIRECCION DE LA PELOTA EN X
    private int ballYdir = -2; //DIRECCION DE LA PELOTA EN Y
    
    private MapGenerator map; //CREAMOS NUESTRO MAPA
    
    //CREAMOS NUESTRO "CONSTRUCTOR"
    public Gameplay(){
        map = new MapGenerator(3, 7);//insertamos el constructor de la variable MapGenerator 3,7
        addKeyListener(this);  //Este método se ejecuta cada vez que se presiona y se suelta una tecla.
        setFocusable(true);//Para que un objeto JPanel reciba las notificaciones del teclado es necesario incluir la instrucción setFocusable(true)que permite que KeyboardExample reciba el foco. 
        setFocusTraversalKeysEnabled(false);//Con este método, debe manejar el cruce de foco explícitamente. 
        timer = new Timer(delay, this);//AL TEMPORISADOR LE DAMOS EL DELAY(RETRASO)
        timer.start();//INICIAMOS NUESTRO TEMPORISADOR
    }
    
    public void paint(Graphics g){//DIBUJAMOS
        //FONDO
        g.setColor(Color.BLUE);
        g.fillRect(1,1, 692, 592);
        
        // MAPA DE DIBUJO
        map.draw((Graphics2D)g);
        
        // BORDES
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 3, 592); //BORDE IZQUIERDO
        g.fillRect(0, 0, 692, 3); //BORDE DE ARRIBA
        g.fillRect(681, 0, 3, 592); //BORDE DERECHO
        
        // PUNTUACIÓN
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(" "+score, 590, 30);
        
        // LA PALETA
        g.setColor(Color.WHITE);
        g.fillRect(playerX, 550, 100, 8);
        
        // LA PELOTA
        g.setColor(Color.BLACK);
        g.fillOval(ballposX, ballposY, 20, 20);
        //CUANDO GANE EL JUEGO
        if(totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.YELLOW);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GANASTE EL JUEGO", 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Presione Enter para reiniciar", 230, 350);
        }
        //CUANDO PIERDA EL JUEGO
        if(ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.YELLOW);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("PERDISTE EL JUEGO", 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Presione Enter para reiniciar", 230, 350);
        }
        
        g.dispose();//hace que la ventana JFrame sea destruida y limpiada por el sistema operativo. 
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();//INICIAMOS NUESTRO TEMPORISADOR
        if(play){
            //DETECTA COLISION ENTRE LA PELOTA Y LA PALETA
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){//
                ballYdir = -ballYdir;
            }
            //comprobar la colision del mapa con la pelota
            A: for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;
                        
                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            //CUANDO LA BOLA GOLPEE A LA IZQUIERDA O DERECHA DEL LADRILLO
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else{//CUANDO LA BOLA GOLPEE LA PARTE SUPERIOR O INFERIOR DEL LADRILLO
                                ballYdir = -ballYdir;  
                            }
                            
                            break A;
                        }
                    }
                }
            }
            //LA PELOTA DETECTA LA COLISION CON LAS PAREDES DE IZQUEIRDA DERECHA Y ARRIBA
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir = -ballXdir;
            }
            if(ballposY < 0){
                ballYdir = -ballYdir;
            }
            if(ballposX > 670){
                ballXdir = -ballXdir;
            }
        }
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){//IMPIDE QUE SE SOBREPASE DE LA VENTANA LA PALETA
            if(playerX >= 600){
                playerX = 600;
            }else{
                moveRight();//MOVER DERECHA
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){//IMPIDE QUE SE SOBREPASE DE LA VENTANA LA PALETA
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();//MOVER IZQUIERDA
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){//REINICIAR JUEGO AL PRESIONAR ENTER
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new  MapGenerator(3, 7);
                
                repaint();
            }
        }
    }
    public void moveRight(){//MOVER A LA DERECHA
        play = true;   //SI ES VERDAD
        playerX += 20; //QUE AVANSE 20
    }
    public void moveLeft(){//MOVER A LA IZQUIERDA
        play = true;   //SI ES VERDAD
        playerX -= 20; //QUE DISMINUYA 20
    }
}
