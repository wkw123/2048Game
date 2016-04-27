package com.my.game2048;

import android.R.bool;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
//卡片类
public class Card extends FrameLayout {

	public Card(Context context) {
		super(context);
		label = new TextView(getContext());
		label.setTextSize(32);
		//设置背景为半透明白色
		label.setBackgroundColor(0x33ffffff);
		//设置数字的位置为卡片正中间
		label.setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(-1, -1);
		//设置卡片的间隙
		lp.setMargins(10, 10, 0, 0);
		addView(label, lp);
		setNum(0);
	}
	private int num = 0;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		if(num<=0){
			//如果num不是正整数，则为空卡片
			label.setText("");
		}else{
			//和字符串相加使之变成字符串
			label.setText(num+"");
		}
		
	}
	//判断两张卡片数字是否相同
	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return (getNum()==o.getNum());
	}
	private TextView label;
}
