package com.example.vendingmachine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Random;

public class bought extends Service {
    //投入された金額
    private int thorwedMoney;
    //出てきた飲み物が表示される
    private ArrayList<String> drinkResult = new ArrayList<String>();

    private final BoughtDrink.Stub mBinder = new BoughtDrink.Stub() {
        @Override
        public int getMoney() throws RemoteException {
            return thorwedMoney;
        }

        @Override
        public void setMoney(int money) throws RemoteException {
            thorwedMoney = money;
        }

        @Override
        public  String[] getDrinkResult() throws RemoteException{
            //ひたすら何か飲み物が出てくる
            //おつりは知らん()
            while(thorwedMoney > 0){
                Random rnd = new Random();
                int num = rnd.nextInt(6);
                switch(num){
                    case 0:
                        //これが買えない時点で何も買えない
                        if(thorwedMoney > 100) {
                            drinkResult.add("おいしい水");
                            thorwedMoney -= 100;
                        }
                        //買えるものがないとき
                        else {
                            return drinkResult.toArray(new String[drinkResult.size()]);
                        }
                        break;
                    case 1:
                        if(thorwedMoney > 130) {
                            drinkResult.add("缶コーヒー");
                            thorwedMoney -= 130;
                        }
                        break;
                    case 2:
                        if(thorwedMoney > 150) {
                            drinkResult.add("コーラ");
                            thorwedMoney -= 150;
                        }
                        break;
                    case 3:
                        if(thorwedMoney > 210) {
                            drinkResult.add("Monster");
                            thorwedMoney -= 210;
                        }
                        break;
                    case 4:
                        if(thorwedMoney > 210) {
                            drinkResult.add("Red Bull");
                            thorwedMoney -= 210;
                        }
                        break;
                    case 5:
                        if(thorwedMoney > 300) {
                            drinkResult.add("眠眠打破");
                            thorwedMoney -= 300;
                        }
                        break;
                }
            }

            return drinkResult.toArray(new String[drinkResult.size()]);
        }

        @Override
        public  void initialize(){
            drinkResult = new ArrayList<String>();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intetnt){
        return mBinder;
    }
}
