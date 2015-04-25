package merliniserentant.bartender;

/**
 * Created by merliniserentant on 23/04/15.
 */
public class Utilisateur {

    private String login;
    private String mdp;
    private String type;

    public Utilisateur(){}

    public Utilisateur(String login, String mdp, String type){

        this.login = login;
        this.mdp = mdp;
        this.type = type;
    }

    public String getlogin() {
        return login;
    }

    public void setlogin(String login) {
        this.login = login;
    }

    public String getmdp() {
        return mdp;
    }

    public void setmdp(String mdp) {
        this.mdp = mdp;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }




    public String toString(){
        return "Login : "+login+"\n Mot de passe : "+mdp+" Type : " + type;
    }

}
