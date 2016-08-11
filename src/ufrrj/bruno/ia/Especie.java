package ufrrj.bruno.ia;

import java.awt.Color;
import java.util.ArrayList;

public class Especie {
    //=======| Indentificadores |======//
    static int qtE;
    private int idE;
    //=======|  Caracteristicas |======//
    private int r,g,b;                  //COR
    private int tempoVida = 8;         //EM SEGUNDOS 
    private double velMovimento = (Math.random() * 6) + 1 ; 
    public int tamanhoX = (int) (Math.random() * 15) + 20;
    public int tamanhoY = (int) (Math.random() * 15) + 20;
    //=================================//
    public ArrayList<Individuo> seres = new ArrayList<Individuo>();
    private Mundo mundo;
    
    Especie(Mundo mundo){
        this.mundo = mundo;
        idE=++qtE;
        r = (int) Math.ceil(Math.random() * 220); //LIMITA faixa rgb 
        g = (int) Math.ceil(Math.random() * 220); 
        b = (int) Math.ceil(Math.random() * 220);
       
        int i,tamPopulacao = ((int) Math.ceil(Math.random() * (3)) + 1);
        for(i=0;i<tamPopulacao;i++){ //for(i=0;i<tamPopulacao;i++){
            criaIndividuo();
        }  
    }
    
    Especie(Mundo mundo,int r,int g,int b,double velMovimento){
        this.r = r;
        this.g = g;
        this.b = b;
        this.velMovimento = velMovimento;
        this.mundo = mundo;
        qtE++; idE=qtE;
       
        int i,tamPopulacao = ((int) Math.ceil(Math.random() * (3)) + 1);
        for(i=0;i<tamPopulacao;i++){ //for(i=0;i<tamPopulacao;i++){
            criaIndividuo();
        }  
    }
       
    public Color getCor(){
        return new Color(r,g,b);
    }
    
    public void criaIndividuo(){
        Individuo tmp = new Individuo(this);
        addIndividuo(tmp);
    }
    
    public void addIndividuo(Individuo novo){
        seres.add(novo);
    }

    public int getTempoVida() {
        return tempoVida;
    }
    
    public Mundo getMundo() {
        return mundo;
    }

    public double getVelMovimento() {
        return velMovimento;
    }

    @Override
    public String toString() {
        return "Especie{" + "idE=" + idE + ", r=" + r + ", g=" + g + ", b=" + b + ", tempoVida=" + tempoVida + ", mundo=" + mundo + '}';
    }
     
}