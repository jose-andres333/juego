/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;
import javax.swing.JFrame;
/**
 *
 * @author jose
 */
public class Main {
    public static void main(String[] args){
        JFrame obj = new JFrame();   // CREAMOS NUESTRO JFRAME(VENTANA)
        Gameplay gamePlay = new Gameplay(); // CREAMOS NUESTRO JUEGO
        obj.setBounds(10,10,700,600);// DAMOS EL TAMAÑO A NUESTRA VENTANA
        obj.setTitle("JUEGO");       // TITULO DE NUESTRA VENTANA
        obj.setResizable(false);     // IMPIDE QUE MOSDIFIQUEMOS EL TAMAÑO DENUESTRA VENTANA
        obj.setVisible(true);        // PONEMOS VISIBLE NUESTRA VENTANA
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//LA EJECUCION SE TERMINE AL PRESIONAR [X]
        obj.add(gamePlay);           //AÑADIMOS NUESTRO JUEGO
        obj.setVisible(true);        //ASEMOS VISIBLE NUESTRO JUEGO
    }
}
