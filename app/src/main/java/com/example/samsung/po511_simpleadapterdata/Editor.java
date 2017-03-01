package com.example.samsung.po511_simpleadapterdata;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samsung on 01.03.2017.
 */

public class Editor extends AppCompatActivity {

    String oldText;
    EditText etText;
    int listPos;
    boolean isChenged;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);

        isChenged = false;
        Intent intent = getIntent();
        listPos = intent.getIntExtra("listPos", -1);
        if (listPos < 0) {
            String massage = "Из главного активити получено ошибочное значение номера позиции в списке";
            Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
            throw new RuntimeException(massage);
        }
        oldText = intent.getStringExtra("etText");
        etText = (EditText) findViewById(R.id.etText);
        etText.setText(oldText);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isChenged) {
            closeLayout((Button) findViewById(R.id.btnChensel));
        }
    }

    public void onBtnUpgClic(View view) {
        closeLayout(view);
    }

    public void onBtnChenClic(View view) {
        closeLayout(view);
    }

    public void closeLayout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (view.getId()) {
            case R.id.btnUpg :
                //получаем данные и передаём их в активити Editor
                String tvText = etText.getText().toString();
                if (!tvText.equals(oldText)) {
                    intent.putExtra("tvText", tvText);
                    intent.putExtra("listPos", listPos);
                    isChenged = true;
                } else {
                    isChenged = false;
                }
                break;
            default :
                isChenged = false;
                break;
        }
        intent.putExtra("isChenged", isChenged);
        startActivity(intent);
    }
}
