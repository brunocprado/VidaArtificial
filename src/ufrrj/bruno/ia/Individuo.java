package ufrrj.bruno.ia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Individuo implements Runnable{
    static int id;
    private Especie especie;
    private Thread t;
    private int posX,posY;
    private long nascimento;
    private Mundo mundo;
    
    public Individuo(Especie esp) {
        id++;
        especie = esp;
        mundo = especie.getMundo();
        nascimento = System.currentTimeMillis();
        posX = (int) Math.ceil(Math.random() * (1260));
        posY = (int) Math.ceil(Math.random() * (700));
        t = new Thread(this);
        t.start();
    }
    
    public boolean vivo(){
        long variacao = System.currentTimeMillis() - nascimento;
        if((variacao/1000) < especie.getTempoVida()){
            return true;
        } else {
            return false;
        }
    } 
    
    private void reproduz(Individuo par){
        
    }
    
    private List<Integer> verificaMaisProximo(){
        double distMaisProximo = Double.MAX_VALUE;
        List<Integer> posProximo = null;
        try {
            for(List<Integer> pos : mundo.comida){
                //double dist = Math.sqrt(( (posX - pos.get(0)) * (posX - pos.get(0)) ) + ( (posY - pos.get(1)) * (posY - pos.get(1)) ));
                double dist = ( (posX - pos.get(0)) * (posX - pos.get(0)) ) + ( (posY - pos.get(1)) * (posY - pos.get(1)) );
                if(dist < distMaisProximo){
                    distMaisProximo = dist;
                    posProximo = pos;
                }
            }
        } catch (Exception ex) {
            pausa(4);
            return verificaMaisProximo();
        }    
        return posProximo;
    }
    public void pausa(int tempo){
        try {
            t.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while(vivo()){ 
            if(mundo.comida.size() != 0){
                List<Integer> pos = verificaMaisProximo();
                
                //EXECUTA MOVIMENTO
                double deltaX = pos.get(0) - posX;
                double deltaY = pos.get(1) - posY;
                
                //double dist = Math.sqrt(((posX - pos.get(0))*(posX - pos.get(0))) + ((posY - pos.get(1))*(posY - pos.get(1))));
                if(Math.abs(deltaX) + Math.abs(deltaY) < especie.getVelMovimento() *2) { 
                   mundo.comida.remove(pos);
                    //nascimento = nascimento + 3000;
                   //especie.criaIndividuo();
                    especie.criaIndividuo();
                    pausa(2);
                    continue;
                }
                
                double angulo = Math.atan2(deltaY,deltaX);       
                posX += especie.getVelMovimento() * Math.cos(angulo);
                posY += especie.getVelMovimento() * Math.sin(angulo);
            }
            pausa(30);
        }  
        especie.seres.remove(this);
        if(especie.seres.size() == 0){
            mundo.eliminaEspecie(especie);
        }
    }

    @Override
    public String toString() {
        return "Individuo{" + "posX=" + posX + ", posY=" + posY + ", tempoVida=" + especie.getTempoVida() + ", nascimento=" + nascimento + '}';
    }
    
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
   
}