package com.branddocs.nfc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String package_name = getApplication().getPackageName();

        setContentView(
                getApplication()
                        .getResources()
                        .getIdentifier(
                                "activity_new"
                                , "layout"
                                , package_name));

        Button closeButton
                = (Button) findViewById(
                R.id.closeButton);

        closeButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                    }
                });
    }
}