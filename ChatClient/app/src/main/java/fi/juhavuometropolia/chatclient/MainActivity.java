package fi.juhavuometropolia.chatclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Main activity starts the chat client. One can write ip address to text field and then one press
 * connect button one gets to ChatActivity. If one presses instructions button one gets to
 * InstuctionsActivity
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button connectButton,instructionsButton;
    private EditText ipET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipET = (EditText)findViewById(R.id.ipedittext);

        connectButton = (Button)findViewById(R.id.connectButton);
        connectButton.setOnClickListener(this);
        instructionsButton = (Button)findViewById(R.id.instructionsButton);
        instructionsButton.setOnClickListener(this);

    }
    /*
        Checks which one of buttons is pressed. If connect button is pressed, the validy
        of string in ipEt is checked. If string is not valid, toast will inform about it.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.connectButton){
            if(this.isAdressValid(ipET.getText().toString())) {
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("CHAT_IP",ipET.getText().toString());
                startActivity(intent);
            }else if(ipET.getText().toString().length()==0){
                Toast.makeText(this.getApplicationContext(),"Write ip address first",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this.getApplicationContext(),"Ip address is not in valid format",Toast.LENGTH_SHORT).show();
                ipET.setText("");
            }
        }else if(v.getId() == R.id.instructionsButton){
            Intent intent = new Intent(this, InstructionsActivity.class);
            startActivity(intent);
        }
    }

    /*
     * This method checks, that given string has three dots and that the number parts can be
     * really parse to int values and that each value is in range from 0 to 255.
     */
    private boolean isAdressValid(String address){
        if(address == null){
            return false;
        }

        String[] addressParts = address.split("\\.");
        if( addressParts.length !=4){
            return false;
        }
        try{
            for(int i = 0; i<addressParts.length;++i){
                int part = Integer.parseInt(addressParts[i]);
                if(part<0 || part >255){
                    return false;
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
