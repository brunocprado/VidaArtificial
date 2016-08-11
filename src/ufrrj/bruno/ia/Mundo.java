package ufrrj.bruno.ia;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import ufrrj.bruno.ia.graficos.GraficoPizza;

public class Mundo {
    int tamX,tamY;
    private ArrayList<Especie> especies = new ArrayList<Especie>();
    private int nEspecies = 10;
    List<List<Integer>> comida = new ArrayList<List<Integer>>();
    private JFrame tela,tela2;
    
    Mundo(int x,int y){ 
        tamX = x; tamY = y;
        int i;
        for(i=0;i<20;i++){
            comida.add(new ArrayList<Integer>());
            comida.get(i).add((int) Math.ceil(Math.random() * (1260)));
            comida.get(i).add((int) Math.ceil(Math.random() * (700)));
        }
        geraPrimeiraGeracao();        
        abreJanela();        
        realimenta();
    }
    
    private void abreJanela(){
        Visual v = new Visual(tamX, tamY);
        v.setMundo(this);
        Dimension tamTela = Toolkit.getDefaultToolkit().getScreenSize();
        
        //MUNDO
        tela = new JFrame("Mundo");
        tela.setSize(tamX, tamY);
        tela.setResizable(false);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLocation((tamTela.width-1380)/2,(tamTela.height-720)/2);
        tela.add(v);
        tela.setVisible(true);
        
        //ESTATISTICAS
        tela2 = new JFrame("Estatistica");
        tela2.setSize(120, 720);
        tela2.setResizable(true);
        tela2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela2.setLocation(tela.getLocation().x + 1276,tela.getLocation().y);
        Estatisticas e = new Estatisticas(this);
        e.setSize(120,720);
        e.setLocation(0,120);
        tela2.add(e);
        GraficoPizza grafico = new GraficoPizza(this,new Rectangle(10,10,100,100));
        tela2.add(grafico);
        tela2.setVisible(true);
    }
    
    private void geraPrimeiraGeracao(){
        int i;
        for(i=0;i<nEspecies;i++){
            Especie tmp = new Especie(this);
            especies.add(tmp);
        }
        //Especie a = new Especie(this,0,0,0,20);
        //especies.add(a);
    }

    private void realimenta(){
        while(true){
            List<Integer> tmp = new ArrayList<Integer>();
            tmp.add((int) Math.ceil(Math.random() * (1260)));
            tmp.add((int) Math.ceil(Math.random() * (700)));
            comida.add(tmp);
            try {
                Thread.sleep(60);
            } catch (InterruptedException ex) {
                Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<Especie> getEspecies() {
        return especies;
    }
    
    public void eliminaEspecie(Especie especie){
        this.especies.remove(especie);
    }
    
    public void imprime(String texto){
        System.out.print(texto);
    }

    @Override
    public String toString() {
        return "Mundo{" + "tamX=" + tamX + ", tamY=" + tamY + ", especies=" + especies + ", tamGerInicial=" + nEspecies + '}';
    }
    
}
