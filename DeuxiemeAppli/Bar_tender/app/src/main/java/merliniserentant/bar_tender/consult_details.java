package merliniserentant.bar_tender;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class consult_details extends Activity {

    private Boisson currentBoisson;
    private TextView nomBoisson;
    private TextView detailsDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_details);

        int number = getIntent().getIntExtra("numBoisson", -1);
        if (number==-1) {
            throw new RuntimeException("Aucun numéro de boisson n'est spécifié");
        }
        BoissonDAO boissondao = new BoissonDAO(this);
        boissondao.open();
        currentBoisson = boissondao.getBoissonwithNumboisson(number); // J'ai le droit de faire ca??

        TextView name = (TextView) findViewById(R.id.consult_details_name);

        name.setText(currentBoisson.getNom());

        TextView description = (TextView) findViewById(R.id.consult_details_description);
        description.setText(currentBoisson.getDescription());

        nomBoisson = (TextView) findViewById(R.id.nomBoisson);
        detailsDescription = (TextView) findViewById(R.id.detailsDescription);

        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            nomBoisson.setText("Name of the beverage");
            detailsDescription.setText("Description");
        }
        else if(Langue.equals("Néerlandais")){
            nomBoisson.setText("Name van drank");
            detailsDescription.setText("Beschrijving");
        }


        //Bitmap bitmap = currentBoisson.getPicture();

        //if(bitmap!=null) {
         //   ImageView picture = (ImageView)findViewById(R.id.consult_details_picture);
           // picture.setImageBitmap(bitmap);
        //}
        //else {
          //  View pictureLL = findViewById(R.id.consult_details_picture_ll);
           // pictureLL.setVisibility(View.GONE);
        //}
        //boissondao.close();
        ImageView picture = (ImageView)findViewById(R.id.consult_details_picture);
        System.out.println("a"+Integer.toString(currentBoisson.getNumboisson()));
        picture.setImageResource(getResources().getIdentifier("a"+Integer.toString(currentBoisson.getNumboisson()), "drawable", getPackageName()));

    }
}
