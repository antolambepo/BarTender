package merliniserentant.bar_tender;

/**
 * Created by merliniserentant on 29/04/15.
 */
public class LigneDeCommande {
    private int num = 1;
    private String login;
    private int bsn;
    private int qté;
    private int table;
    public LigneDeCommande(){};
    public LigneDeCommande(int num, String login, int bsn, int qté, int table)
    {
        this.bsn = bsn;
        this.qté = qté;
        this.table = table;
        this.num = num;
        this.login = login;
    }

    public int getBoisson() {
        return bsn;
    }

    public int getQuantité() {
        return qté;
    }

    public  int getTable(){
        return table;
    }

    public int getNum(){
        return num;
    }

    public String getLogin(){
        return login;
    }

    public void setBoisson(int bsn){
        this.bsn = bsn;
    }

    public void setQuantité(int qté){
        this.qté = qté;
    }

    public void setTable(int table){
        this.table = table;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
