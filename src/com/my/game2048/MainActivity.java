package com.my.game2048;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	public MainActivity() {
		mainactivity = this;
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.tvScore);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void clearScore(){
    	score = 0;
     	showScore();
    }
    public void showScore(){
    	tvScore.setText(score+"");
    }
    public void addScore(int s){
    	score+=s;
    	showScore();
    }
    private int score = 0;
    private TextView tvScore;
    private static MainActivity mainactivity = null;
    
    public static MainActivity getMainactivity() {
		return mainactivity;
	}
}
