package com.my.game2048;

import android.R.bool;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
//��Ƭ��
public class Card extends FrameLayout {

	public Card(Context context) {
		super(context);
		label = new TextView(getContext());
		label.setTextSize(32);
		//���ñ���Ϊ��͸����ɫ
		label.setBackgroundColor(0x33ffffff);
		//�������ֵ�λ��Ϊ��Ƭ���м�
		label.setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(-1, -1);
		//���ÿ�Ƭ�ļ�϶
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
			//���num��������������Ϊ�տ�Ƭ
			label.setText("");
		}else{
			//���ַ������ʹ֮����ַ���
			label.setText(num+"");
		}
		
	}
	//�ж����ſ�Ƭ�����Ƿ���ͬ
	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return (getNum()==o.getNum());
	}
	private TextView label;
}
