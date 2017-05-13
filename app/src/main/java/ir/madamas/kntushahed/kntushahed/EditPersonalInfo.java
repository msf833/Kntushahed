package ir.madamas.kntushahed.kntushahed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EditPersonalInfo extends AppCompatActivity {

    Button cancelBtn;
    Button confirmEdit;
    int backPressedCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);

        cancelBtn = (Button) findViewById(R.id.eCancel_button);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmEdit = (Button) findViewById(R.id.edit_button);

        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedCounter == 1){
            finish();
        }
        Toast.makeText(getApplicationContext(), "با خروج از این صفحه تغییرات شما دخیره نخواهد شد!", Toast.LENGTH_SHORT).show();
        backPressedCounter++;
    }
}
