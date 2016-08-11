package ufrrj.bruno.ia.graficos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import ufrrj.bruno.ia.Especie;
import ufrrj.bruno.ia.Mundo;

public class GraficoPizza extends JComponent implements Runnable{
    private ArrayList fatias = new ArrayList<Fatia>();
    private Rectangle area;
    private Thread t;
    private Mundo mundo;
    public void paint(Graphics g){
        desenhaGrafico((Graphics2D) g, area, fatias);
    }
    
    public void adicionaFatia(Fatia fatia){
        fatias.add(fatia);
    }
    Image bg = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/bg.jpg"));
    public void desenhaGrafico(Graphics2D g, Rectangle area,ArrayList<Fatia> fatias){
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(bg,0, 0, null);
        double soma = 0;
        int angulo = 0;
        double valAtual = 0;
        for(Fatia fatia : fatias){
            soma += fatia.valor;
        }
        for(Fatia fatia : fatias){
            angulo = (int) ((valAtual * 360)/soma);
            int anguloF = (int) ((fatia.valor * 360)/soma);
            g.setColor(fatia.cor);
            g.fillArc(area.x, area.y, area.width, area.height, angulo, anguloF);
            //g.drawString("teste", anguloF, 6);
            valAtual += fatia.valor;
        }
    }

    
    
    public GraficoPizza(){
        area = getBounds();
    }
    public GraficoPizza(Fatia[] fatias,Rectangle area){
        //this.fatias = fatias;
        this.area = area;
    }
    public GraficoPizza(Mundo mundo,Rectangle area){
        this.area = area;
        this.mundo = mundo;
        t = new Thread(this);
        t.start();    
    }

    @Override
    public void run() {
        while(true){
            fatias.clear();
            for(Especie especie : mundo.getEspecies()){ 
                fatias.add(new Fatia(especie.seres.size(),especie.getCor()));
            }
            repaint();
            try {
                t.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(GraficoPizza.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
