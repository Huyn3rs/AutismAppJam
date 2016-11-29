package teampaygrade.busyboard;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class Quiz extends AppCompatActivity {
    int LEFT;
    int RIGHT;
    int size = 12;
    int sounds[] = {R.raw.moo, R.raw.meow, R.raw.sad, R.raw.bark, R.raw.eating, R.raw.angrys, R.raw.happy, R.raw.crying, R.raw.sirens, R.raw.eww, R.raw.car, R.raw.dingdong};
    int pics[] = {R.drawable.cow, R.drawable.cat, R.drawable.baby, R.drawable.dog,
                  R.drawable.eating, R.drawable.angry, R.drawable.happy,
                  R.drawable.cryingman, R.drawable.ambulance, R.drawable.eww,
                  R.drawable.car, R.drawable.house };
    int index = 0;
    int s;
    int score = 0;

    Button playBtn;
    ImageButton btnR, btnL;
    SoundPool sd;
    int sound, play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        playBtn = (Button) this.findViewById(R.id.PlaySoundBtn);
        btnR = (ImageButton) findViewById(R.id.rightButton);
        btnL = (ImageButton) findViewById(R.id.leftButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View b = findViewById(R.id.NextBtn);
        b.setVisibility(View.GONE);
        View f = findViewById(R.id.FinishBtn);
        f.setVisibility(View.GONE);


        int random = (int)(Math.random() * size);
        while(random == index)
            random = (int)(Math.random() * size);

        int correctSide = (int)(Math.random() * 2);
        if(correctSide == 0){
            LEFT = 1;
            RIGHT = 0;
            btnL.setImageResource(pics[index]);
            btnR.setImageResource(pics[random]);
        }
        else{
            LEFT = 0;
            RIGHT = 1;
            btnR.setImageResource(pics[index]);
            btnL.setImageResource(pics[random]);
        }

        sd = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound = sd.load(this, (int)sounds[index], 1);
        index++;
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                play = sd.play(sound, 1, 1, 1, 0, 1);
            }
        });

    }

    public void leftChoice(View view) {
        final TextView messageTxt = (TextView) this.findViewById(R.id.InfoTxt);
        messageTxt.setText("BOTTOM CHOSEN");
        View b = findViewById(R.id.NextBtn);
        b.setVisibility(View.VISIBLE);
        s = LEFT;

    }

    public void rightChoice(View view) {
        final TextView messageTxt = (TextView) this.findViewById(R.id.InfoTxt);
        messageTxt.setText("TOP CHOSEN");
        View b = findViewById(R.id.NextBtn);
        b.setVisibility(View.VISIBLE);
        s = RIGHT;
    }

    public void nextQuestion(View view) {
        sd.autoPause();
        score += s;
        if (index >= size) {
            View one = findViewById(R.id.rightButton);
            View two = findViewById(R.id.leftButton);
            View play = findViewById(R.id.PlaySoundBtn);
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
            play.setVisibility(View.GONE);

            final TextView messageTxt = (TextView) this.findViewById(R.id.InfoTxt);
            messageTxt.setText("YOUR SCORE: " + Integer.toString(score) + " out of " + Integer.toString(size));

            View b = findViewById(R.id.NextBtn);
            b.setVisibility(View.GONE);
            View f = findViewById(R.id.FinishBtn);
            f.setVisibility(View.VISIBLE);
        } else {
            sd = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            sound = sd.load(this, (int)sounds[index], 1);

//            playBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    play = sd.play(sound, 1, 1, 1, 0, 1);
//                }
//            });

            View b = findViewById(R.id.NextBtn);
            b.setVisibility(View.GONE);
            final TextView messageTxt = (TextView) this.findViewById(R.id.InfoTxt);
            messageTxt.setText("");

            final ImageButton btnR = (ImageButton) findViewById(R.id.rightButton);
            final ImageButton btnL = (ImageButton) findViewById(R.id.leftButton);
            int random = (int)(Math.random() * size);
            while(random == index)
                random = (int)(Math.random() * size);

            int correctSide = (int)(Math.random() * 2);
            if(correctSide == 0){
                LEFT = 1;
                RIGHT = 0;
                btnL.setImageResource(pics[index]);
                btnR.setImageResource(pics[random]);
            }
            else{
                LEFT = 0;
                RIGHT = 1;
                btnR.setImageResource(pics[index]);
                btnL.setImageResource(pics[random]);
            }

            index++;
        }
    }

    public void finishClick(View view) {
        Intent intent = new Intent(this, SecondMenu.class);
        startActivity(intent);
    }
}
