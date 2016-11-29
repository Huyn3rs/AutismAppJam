package teampaygrade.busyboard;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;

public class MainMenu extends AppCompatActivity {
    RelativeLayout l1;

    private static final int PERMISSION_FOLDER = 1;
    private static final int PERMISSION_CAMERA = 2;
    private static final int PERMISSION_AUDIO = 3;
    private boolean requestToast = true;
    File storage = Environment.getExternalStorageDirectory ();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);

        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_FOLDER);
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PermissionChecker.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_AUDIO);

        l1 = (RelativeLayout) findViewById(R.id.mainmenu);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainMenu();

            }
        });
    }

    private void MainMenu() {
        Intent menuIntent = new Intent(this, SecondMenu.class);
        startActivity(menuIntent);
        overridePendingTransition(0, 0);
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (!(grantResults.length > 0 && grantResults[0] == PermissionChecker.PERMISSION_GRANTED)) {
            if(requestToast) {
                Toast.makeText(this, "Some features may not work properly.", Toast.LENGTH_SHORT).show();
                requestToast = false;
            }
        }
        return;
    }
}

