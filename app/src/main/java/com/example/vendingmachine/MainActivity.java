package com.example.vendingmachine;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private BoughtDrink myDrink = null;

    private ServiceConnection mConect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myDrink = BoughtDrink.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myDrink = null;
        }
    };

    public void BoughtDrink(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this).setPositiveButton("飲み物を購入する", null);
        EditText edit = findViewById(R.id.throwMoney);

        try{
            myDrink.setMoney(Integer.parseInt(edit.getText().toString()));
            alert.setMessage(myDrink.getMoney()+"円投入して購入する？").setPositiveButton("はい", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try{
                        showDrinkResult(myDrink.getDrinkResult());
                    } catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
            }).show();

        } catch (RemoteException e){
            alert.setMessage("あれれ？　電源が　落ちてしまった…").show();
            e.printStackTrace();
        }
    }

    private void showDrinkResult(String[] drinkResult){
        AlertDialog.Builder result = new AlertDialog.Builder(this).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    myDrink.initialize();
                } catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
        String str = "";
        if(drinkResult.length == 0){
            result.setMessage("ずっと待ってても　なんにも　出てこない…").show();
        }else {
            for (int i = 0; i < drinkResult.length; i++) {
                str += drinkResult[i];
                if (i < drinkResult.length - 1) str += "と　";
            }
            result.setMessage(str + "が　出てきた！").show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent vendingservice = new Intent(this, bought.class);
        bindService(vendingservice, mConect, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(mConect);
    }
}
