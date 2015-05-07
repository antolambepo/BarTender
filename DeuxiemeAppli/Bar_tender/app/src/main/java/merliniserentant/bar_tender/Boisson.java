package merliniserentant.bar_tender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by merliniserentant on 30/04/15.
 */
public class Boisson {
    private int Stockmax;
    private int Stock;
    private int Seuil;
    private double Prix;
    private int Numboisson;
    private String Nom;
    private String LOGOTYPE;
    private String Description;

    public Boisson(){}

    public Boisson(int Stockmax,int Numboisson, int Stock,double Prix, int Seuil, String Nom, String LOGOTYPE, String Desciption){
        this.Numboisson=Numboisson;
        this.Stockmax = Stockmax;
        this.Stock = Stock;
        this.Prix = Prix;
        this.Seuil = Seuil;
        this.Nom = Nom;
        this.LOGOTYPE = LOGOTYPE;
        this.Description = Desciption;
    }

    public int getStockmax() {
        return Stockmax;
    }

    public void setStockmax(int Stockmax) {
        this.Stockmax= Stockmax;
    }

    public int getNumboisson() {
        return Numboisson;
    }

    public void setNumboisson( int Numboisson) {
        this.Numboisson = Numboisson;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double Prix) {
        this.Prix = Prix;
    }

    public int getSeuil() {
        return Seuil;
    }

    public void setSeuil(int Seuil) {
        this.Seuil = Seuil;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getLogotype() {
        return LOGOTYPE;
    }

    public void setLogotype(String LOGOTYPE) {
        this.LOGOTYPE = LOGOTYPE;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description= Description;
    }

    public void upStock(int up) { setStock( getStock()+up); }

    public void downStock(int down) { setStock( getStock()- down);} //Attention traiter cas où down>stock.

    public String alertStock(String boisson) {

        return " La boisson "+ boisson +" est en quantité insuffisante. Pensez à réapprovisionner le stock.";

    }

}
