package merliniserentant.bar_tender;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;





public class consult_details extends Activity {

    private Boisson currentBoisson;

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

        Bitmap bitmap = currentBoisson.getPicture();

        if(bitmap!=null) {
            ImageView picture = (ImageView)findViewById(R.id.consult_details_picture);
            picture.setImageBitmap(bitmap);
        }
        else {
            View pictureLL = findViewById(R.id.consult_details_picture_ll);
            pictureLL.setVisibility(View.GONE);
        }
        boissondao.close();
    }
}
