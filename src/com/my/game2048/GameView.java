package com.my.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	private void initGameView() {
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		// 获取手指按下和离开的位置，从而判断方向（左上角为原点）
		setOnTouchListener(new View.OnTouchListener() {
			float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) {
							swipeLeft();
							System.out.println("left");
						} else if (offsetX > 5) {
							swipeRight();
							System.out.println("right");
						}
					} else {
						if (offsetY < -5) {
							swipeUp();
							System.out.println("up");
						} else if (offsetY > 5) {
							swipeDown();
							System.out.println("down");
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}

	// 屏幕不可旋转
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 设置每张正方形卡片的边长
		int cardWidth = (Math.min(w, h) - 10) / 4;
		// 将16张卡片添加到矩阵当中
		addCards(cardWidth, cardWidth);
		startGame();
	}

	private void addCards(int cardWidth, int cardHeight) {
		Card c;
		// 把16张卡片添加到GameView中
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				c = new Card(getContext());
				c.setNum(0);
				addView(c, cardWidth, cardHeight);
				cardsMap[x][y] = c;
			}
		}
	}

	private void startGame() {
		MainActivity.getMainactivity().clearScore();;
		// 初始化全为空卡片
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardsMap[x][y].setNum(0);
			}
		}
		// 游戏开始有两个带数字的卡片
		addRandomNum();
		addRandomNum();
	}

	private void addRandomNum() {
		emptyPoints.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardsMap[x][y].getNum() <= 0) {
					// 保存矩阵中所有的空卡片
					emptyPoints.add(new Point(x, y));
				}
			}
		}
		// 随机一张空卡片填入数字
		Point p = emptyPoints
				.remove((int) (Math.random() * emptyPoints.size()));
		// 填入2/4的概率比为9/1
		cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
	}

	private void swipeLeft() {
		boolean merge = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++)
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cardsMap[x1][y].getNum() > 0) {
						// 如果当前位置是空卡片而它右边有非空卡片
						if (cardsMap[x][y].getNum() <= 0) {
							// 非空卡片(cardsMap[x1][y])替代空卡片(cardsMap[x][y])
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							// 这里在x1写成了x导致浪费了很多时间
							cardsMap[x1][y].setNum(0);
							// 向前退一步，看看这个卡片能不能和右边的合并
							x--;
							merge = true;
							// 如果存在两张数字相等的卡片，则向左合并
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							cardsMap[x1][y].setNum(0);
							MainActivity.getMainactivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
		}
		if(merge){
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeRight() {
		boolean merge = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--)
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cardsMap[x1][y].getNum() > 0) {
						// 如果当前位置是空卡片而它左边有非空卡片
						if (cardsMap[x][y].getNum() <= 0) {
							// 非空卡片(cardsMap[x1][y])替代空卡片(cardsMap[x][y])
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							// 向前退一步，看看这个卡片能不能和左边的合并
							x++;
							merge = true;
							// 如果存在两张数字相等的卡片，则向右合并
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							cardsMap[x1][y].setNum(0);
							MainActivity.getMainactivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
		}
		if(merge){
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeUp() {
		boolean merge = false;
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++) {
				for (int y1 = y + 1; y1 < 4; y1++) {
					if (cardsMap[x][y1].getNum() > 0) {
						// 如果当前位置是空卡片而它下边有非空卡片
						if (cardsMap[x][y].getNum() <= 0) {
							// 非空卡片(cardsMap[x][y1])替代空卡片(cardsMap[x][y])
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);
							// 向前退一步，看看这个点能不能和下面的合并
							y--;
							merge = true;
							// 如果存在两张数字相等的卡片，则向上合并
						} else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							cardsMap[x][y1].setNum(0);
							MainActivity.getMainactivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		if(merge){
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeDown() {
		boolean merge = false;
		for (int x = 0; x < 4; x++)
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (cardsMap[x][y1].getNum() > 0) {
						// 如果当前位置是空卡片而它上边有非空卡片
						if (cardsMap[x][y].getNum() <= 0) {
							// 非空卡片(cardsMap[x1][y])替代空卡片(cardsMap[x][y])
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);
							// 向前退一步，看看这个点能不能和上面的合并
							y++;
							merge = true;
							// 如果存在两张数字相等的卡片，则向下合并
						} else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							cardsMap[x][y1].setNum(0);
							MainActivity.getMainactivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		if(merge){
			addRandomNum();
			checkComplete();
		}
	}
	private void checkComplete(){
		boolean complete = true;
		ALL:
		for(int y=0; y<4; y++)
			for(int x=0; x<4; x++){
				//只要非空卡片没有布满16个格子或者还有相邻两个数字一样的卡片则游戏不能结束
				if(cardsMap[x][y].getNum()==0 || 
						(x>0 && cardsMap[x][y].equals(cardsMap[x-1][y])) ||
						(x<3 && cardsMap[x][y].equals(cardsMap[x+1][y])) ||
						(y>0 && cardsMap[x][y].equals(cardsMap[x][y-1])) ||
						(y<3 && cardsMap[x][y].equals(cardsMap[x][y+1]))){
					complete = false;
					break ALL;
				}
			}
		if(complete){
			new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).show();
		}
	}
	private Card[][] cardsMap = new Card[4][4];
	// 保存没有数字的点
	private List<Point> emptyPoints = new ArrayList<Point>();
}
