package teampaygrade.busyboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.SoundPool;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Learn extends AppCompatActivity {
    File storage = Environment.getExternalStorageDirectory ();
    Context c = this;
    ImageButton b1;
    Button b2;

    SoundPool sd;
    RelativeLayout l1;
    int sound, play;
    List<Object> sounds = new ArrayList<Object>(){{
        add(R.raw.moo);
        add(R.raw.meow);
        add(R.raw.sad);
        add(R.raw.bark);
        add(R.raw.eating);
        add(R.raw.angrys);
        add(R.raw.happy);
        add(R.raw.crying);
        add(R.raw.sirens);
        add(R.raw.eww);
        add(R.raw.car);
        add(R.raw.dingdong);

    }};
    List<Object> images = new ArrayList<Object>(){{
        add(R.drawable.cow);
        add(R.drawable.cat);
        add(R.drawable.baby);
        add(R.drawable.dog);
        add(R.drawable.eating);
        add(R.drawable.angry);
        add(R.drawable.happy);
        add(R.drawable.cryingman);
        add(R.drawable.ambulance);
        add(R.drawable.eww);
        add(R.drawable.car);
        add(R.drawable.house);
    }};
    Random generator = new Random();
    int index = generator.nextInt(images.size());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b1 = (ImageButton) findViewById(R.id.custom);
        b1.setImageResource((int)images.get(index));

        b2 = (Button) findViewById(R.id.stop1);
        l1 = (RelativeLayout) findViewById(R.id.page1);

        sd = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound = sd.load(this, (int)sounds.get(index), 1);

        File[] image_folder = new File(storage.getAbsolutePath() + File.separator + "SensorySounds" + File.separator + "Images").listFiles();
        File[] sound_folder = new File(storage.getAbsolutePath() + File.separator + "SensorySounds" + File.separator + "Sounds").listFiles();

        for (File ifile: image_folder) {
            for (File sfile: sound_folder)
            {
                if (ifile.getName().substring(0, ifile.getName().lastIndexOf('.')).equals(sfile.getName().substring(0, sfile.getName().lastIndexOf('.'))))
                {
                    Bitmap bitmapImage = BitmapFactory.decodeFile(ifile.getAbsolutePath());
                    int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(ifile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                    images.add(rotateBitmap(scaled, orientation));
                    sounds.add(sfile);
                }
            }
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play = sd.play(sound, 1, 1, 1, 0, 1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sd.autoPause();

            }
        });


        l1.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft()  {
                sd.autoPause();
                if (++index >= sounds.size())
                    index = 0;
//                Toast.makeText(c, Integer.toString(index), Toast.LENGTH_SHORT).show();
                if (sounds.get(index).getClass().equals(Integer.class)) {
                    sound = sd.load(c, (int)sounds.get(index), 1);
                    b1.setImageResource((int)images.get(index));
                }

                else{
                    b1.setImageBitmap((Bitmap) images.get(index));
                    File temp = (File) sounds.get(index);

                    sound = sd.load(temp.getAbsolutePath(), 1);
                }

            }
            public void onSwipeRight() {
                sd.autoPause();
                if (--index < 0)
                    index = sounds.size() - 1;

//                Toast.makeText(c, Integer.toString(index), Toast.LENGTH_SHORT).show();
                if (sounds.get(index).getClass().equals(Integer.class)) {
                    sound = sd.load(c, (int) sounds.get(index), 1);
                    b1.setImageResource((int) images.get(index));
                }
                else{
                    b1.setImageBitmap((Bitmap) images.get(index));
                    File temp = (File) sounds.get(index);

                    sound = sd.load(temp.getAbsolutePath(), 1);
                }


            }
        });
    }

    public void next(Class c) {
        Intent intent = new Intent(Learn.this, c);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
}



