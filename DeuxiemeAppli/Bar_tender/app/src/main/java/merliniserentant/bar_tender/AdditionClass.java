package merliniserentant.bar_tender;


/**
 * Created by Roselien on 03/05/15.
 */
public class AdditionClass {

    private int numAddition;
    private int numLignedeCommande;
    private String typePaiement;

    public AdditionClass(int numAddition, int numLignedeCommande, String typePaiement ){
        this.numAddition = numAddition;
        this.numLignedeCommande = numLignedeCommande;
        this.typePaiement = typePaiement;
    }

    public int getNumAddition(){return numAddition;}
    public int getNumLignedeCommande(){return  numLignedeCommande;}
    public String getTypePaiement(){return  typePaiement;}

    public void setNumAddition(int num){this.numAddition = num;}

    public void setNumLignedeCommande(int numLignedeCommande) {
        this.numLignedeCommande = numLignedeCommande;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }
}
