package merliniserentant.bar_tender;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;


public class consult_details extends Activity {

    private Boisson currentBoisson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_details);

        int number = getIntent().getIntExtra("", -1);
        if (number==-1) {
            throw new RuntimeException("Aucun numéro de boisson n'est spécifié");
        }
        currentBoisson = BoissonDAO.getBoissonwithNumboisson(number);

        TextView name = (TextView) findViewById(R.id.consult_details_name);
        name.setText(currentBoisson.getNom());

        TextView description = (TextView) findViewById(R.id.consult_details_description);
        description.setText(currentBoisson.getDescription());

        Bitmap bitmap = currentBoisson.getLogotype();

        if(bitmap!=null) {
            ImageView picture = (ImageView)findViewById(R.id.consult_details_picture);
            picture.setImageBitmap(bitmap);
        }
        else {
            View pictureLL = findViewById(R.id.consult_details_picture_ll);
            pictureLL.setVisibility(View GONE);
        }
    }


}
