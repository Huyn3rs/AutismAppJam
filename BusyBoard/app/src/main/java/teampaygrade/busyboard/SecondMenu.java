package teampaygrade.busyboard;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondMenu extends AppCompatActivity {
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_START_RECORDING = 69;
    private static final String NAME_OF_SOUND = "Hello";
    String timeStamp = "";
    File storage = Environment.getExternalStorageDirectory ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_second);

//        text = (TextView) findViewById(R.id.text1);
        File images = new File(storage.getAbsolutePath() + File.separator + "SensorySounds" + File.separator + "Images");
        File folder = new File(storage.getAbsolutePath() + File.separator + "SensorySounds" + File.separator + "Sounds");
        if(!(images.exists() && folder.exists())) {
            if(images.mkdirs() && folder.mkdir())
                Toast.makeText(this,"Folders created.", Toast.LENGTH_SHORT).show();
        }
//            text.setText(storage.getAbsolutePath() + File.separator + "SensorySounds");
    }

    private File createImage(String name)
    {
        File folder = new File(storage.getAbsolutePath() + File.separator + "SensorySounds" + File.separator + "Images");
//        File MyPhoto = new File(folder, timeStamp);
        File image = null;
        try {
            image = new File(folder.getAbsolutePath() + File.separator + name + ".jpg");
        } catch (Exception e) {}
        return image;
    }

    public void TakePicture(View view) {
        Intent CameraIntent = new Intent();
        CameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        timeStamp = new SimpleDateFormat("MdyHms").format(new Date());
        File photoFile = createImage(timeStamp);
        if (photoFile != null){
            CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(CameraIntent, ACTIVITY_START_CAMERA_APP );
//            Toast.makeText(this,"Photo saved as" + timeStamp, Toast.LENGTH_LONG).show();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            Toast.makeText(this,"Photo saved in SensorySounds folder.", Toast.LENGTH_SHORT).show();
            Intent AudioIntent = new Intent(this, RecordAudio.class).putExtra(NAME_OF_SOUND, timeStamp);
            startActivityForResult(AudioIntent, ACTIVITY_START_RECORDING);

        }
//        if (requestCode == ACTIVITY_START_RECORDING && resultCode == RESULT_OK)
//        {
////            Toast.makeText(this,"Sound saved in SensorySounds folder.", Toast.LENGTH_SHORT).show();
//        }


    }

    public void learnClick(View view) {
        Intent intent = new Intent(this, Learn.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void quizClick(View view) {
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }

}
