package fi.juhavuometropolia.chatclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Scanner;

/*
    Purpose of this activity is show instructions. The text field is in raw folder in
    resources and it is read by scanner.
 */
public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.chatinstructions));
        scanner.useDelimiter("\n");
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()){
            stringBuilder.append(scanner.nextLine()+"\n");
        }
        scanner.close();

        EditText tv = (EditText) findViewById(R.id.instructionstv);
        tv.setText(stringBuilder.toString());

    }

}
