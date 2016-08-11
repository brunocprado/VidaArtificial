package ufrrj.bruno.ia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class Visual extends JPanel implements Runnable {
    private Thread t;
    private int tamX,tamY;
    private Mundo mundo;
    Image bg = Toolkit.getDefaultToolkit().createImage("/home/bruno/grama2.png");
    Image alimento = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/Plant2.png"));
    Image arvore = Toolkit.getDefaultToolkit().createImage("/home/bruno/tree.png");
    List<List<Integer>> arvores = new ArrayList<List<Integer>>();
    public Visual(int x,int y){
        tamX = x; tamY = y;
        for(int a=0;a<6;a++){
            List<Integer> tmp = new ArrayList<Integer>();
            tmp.add((int) Math.ceil(Math.random() * 31) * 40);
            tmp.add((int) Math.ceil(Math.random() * 15) * 40);
            arvores.add(tmp);
        }
        t = new Thread(this);
        t.start();
    }

    public void setMundo(Mundo mundo) {
        this.mundo = mundo;
    }  
    
    private void desenhaIndividuos( Graphics g){
        try {
            for(Especie especie : mundo.getEspecies()){             
                g.setColor(especie.getCor());
                for(Individuo individuo : especie.seres){
                    //g.fillOval(individuo.getPosX(), individuo.getPosY(), especie.tamanhoX, especie.tamanhoY);
                    g.fillOval(individuo.getPosX(), individuo.getPosY(), 25, 25);
                }
            }
        } catch (Exception ex) {
            try {
                t.sleep(2);
            } catch (InterruptedException exx) {}     
            desenhaIndividuos(g);
        } 
    }
    
    public void paint(Graphics g){
        // LIMPA TELA
        g.clearRect(0, 0, getWidth(), getHeight());
       // g.setColor(Color.green);
        //g.fillRect(0, 0, getWidth(), getHeight());
        for(int x=0;x<1400;x+=200){
            for(int y=0;y<800;y+=200){
                 g.drawImage(bg,x, y, null);
            }
        }
       
        //DESENHA INDIVIDUOS
        desenhaIndividuos(g);

        //DESENHA COMIDA
        try{
            for(List<Integer> pos : mundo.comida){
                g.setColor(Color.red);
                g.drawImage(alimento, pos.get(0), pos.get(1), null);
            } 
        } catch (Exception ex) {
            //WHATEVER
        }
        
        for(List<Integer> a : arvores){
            g.drawImage(arvore,a.get(0),a.get(1),null);
        }
        
    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                t.sleep(25);
            } catch (InterruptedException ex) {
                Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
}
