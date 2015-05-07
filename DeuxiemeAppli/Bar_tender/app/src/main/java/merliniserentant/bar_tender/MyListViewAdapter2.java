
package merliniserentant.bar_tender;


/**
 * Created by Alice on 30-04-15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//Va nous permettre d'afficher de manière personnalisée notre inventaire

public class MyListViewAdapter2 extends BaseAdapter {

    /**
     * Permet d'instancier un fichier xml de layout dans une vue.
     */
    private final LayoutInflater mInflater;

    /**
     * Liste des éléments de collection à mettre dans la liste.
     */
    private ArrayList<Boisson> boissons;

    /**
     * Constructeur.
     *
     * @param context        Contexte de l'application.
     * @param boissons Liste des éléments à placer dans l'inventaire.
     */
    public MyListViewAdapter2(Context context, ArrayList<Boisson> boissons) {
        mInflater = LayoutInflater.from(context);
        this.boissons = boissons;
    }

    @Override
    public int getCount() { return boissons.size(); }

    @Override
    public Object getItem(int position) { return boissons.get(position);} //Pas sure d'en avoir besoin

    @Override
    public long getItemId(int position) { return boissons.get(position).getNumboisson(); } //Juste la pour implementer BaseAdapter

    /**
     * Remplit chaque ligne de la liste avec un layout particulier.
     *
     * Cette méthode est appelée par Android pour construire la vue de la liste (lors de la
     * construction de listview).
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Si la vue n'a pas encore été créé (typiquement lors du première affichage de la liste).
        // Android recycle en effet les layout déjà chargés des éléments de la liste (par exemple
        // lors du changement de l'ordre dans la liste.)

        if (convertView == null) {
            // Création d'un nouvelle vue avec le layout correspondant au fichier xml
            convertView = mInflater.inflate(R.layout.collecto_row_item2, parent, false);
        }

        // Récupération des 4 éléments de notre vue dans le but d'y placer les données.
        TextView nameTextView = (TextView) convertView.findViewById(R.id.nomboissonrow2);
        TextView stockTextView = (TextView) convertView.findViewById(R.id.prixrow2);

        // Récupération et placement des données.
        Boisson boisson = boissons.get(position);

        nameTextView.setText(boisson.getNom());
        stockTextView.setText(Double.toString(boisson.getPrix()));


        return convertView;
    }


    public void setBoissons(ArrayList<Boisson> newBoissons) {
        this.boissons = newBoissons;
        notifyDataSetChanged();
    }


}



