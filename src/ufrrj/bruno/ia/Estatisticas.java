package ufrrj.bruno.ia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class Estatisticas extends JPanel implements Runnable{
    private Thread t;
    private long inicio;
    private int tamX,tamY;
    private Mundo mundo;
    private int total;
    Image bg = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/bg.jpg"));
    
    public Estatisticas(Mundo mundo){
        inicio = System.currentTimeMillis();
        this.mundo = mundo;
        t = new Thread(this);
        t.start();
    }
    
    private void desenhaGrupos(Graphics g){
        int posAtual = 10;
        total = 0;
        try {   
            for(Especie especie : mundo.getEspecies()){ 
                g.setColor(especie.getCor());            
                g.fillRect(10, posAtual, 25, 20);             
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(especie.seres.size()), 40, posAtual+15);  
                posAtual+=30;
                total += especie.seres.size();
            } 
        } catch (Exception ex) {
            try {
                t.sleep(2);
            } catch (InterruptedException exx) { 
            }
            desenhaGrupos(g);
        } 
    }
    
    public void paint(Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(bg,0, 0, null);
        
        desenhaGrupos(g);
        
        int posRodape = 510;
        g.setColor(new Color(220,220,255)); 
        g.fillRect(0, posRodape, getWidth(), 40);
        g.setColor(Color.black);
        g.drawLine(0, posRodape, getWidth(), posRodape);
        
        int largura = (int) g.getFontMetrics().getStringBounds("Total: " + Integer.toString(total), g).getWidth();

        g.drawString("Total: " + Integer.toString(total), (getWidth()-largura)/2, posRodape+15);
        
        largura =  (int) g.getFontMetrics().getStringBounds(Long.toString((System.currentTimeMillis() - inicio)/1000) + " Anos",g).getWidth();
        g.drawString(Long.toString((System.currentTimeMillis() - inicio)/1000) + " Anos",(getWidth()-largura)/2,posRodape+35);
    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                t.sleep(150);
            } catch (InterruptedException ex) {
                Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }

    private String getResource(String imgbgjpg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}