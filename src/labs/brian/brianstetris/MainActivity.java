package labs.brian.brianstetris;

import labs.brian.brianstetris.TetrisGrid.onEndGameListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements onEndGameListener{

	private TetrisGrid board;
	private TextView score;
	
	private Handler handler;
	private GestureDetector gesture;
	
	private boolean gameRunning = true;
	private int screenWidth;
	private int screenHeight;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        
        board = (TetrisGrid)findViewById(R.id.tetrisboard);
        score = (TextView)findViewById(R.id.textView1);
        
        board.setOnEndGameListener(this);
        handler = new Handler(){
        	@Override
        	public void handleMessage(Message m){
        		board.performMove();
        		score.setText(String.valueOf(board.getGameScore()));
        	}
        };
        
        //will control the board
        gesture = new GestureDetector(this,new OnGestureListener(){

			@Override
			public boolean onDown(MotionEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onFling(MotionEvent arg0, MotionEvent arg1,
					float arg2, float velY) {
				if(velY > 50)
					board.sendCurrentPieceToBottom();
				return true;
			}

			@Override
			public void onLongPress(MotionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				if(distanceX > 15){
					board.translatePiece(-1);
				}else if(distanceX < -15){
					board.translatePiece(1);
				}
				return true;
			}

			@Override
			public void onShowPress(MotionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			//if tap on left side of screen rotate ccw, right side cw
			@Override
			public boolean onSingleTapUp(MotionEvent event) {
				board.rotatePiece(TetrisGrid.ROTATE_CLOCKWISE);
				return true;
			}});
        startGame();
    }

    private void startGame(){
    	Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				Looper.prepare();
				while(gameRunning){
					handler.sendEmptyMessage(0);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
    	});
    	t.start();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onEndGame() {
		gameRunning = false;
		Toast.makeText(this, "game has ended", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		return gesture.onTouchEvent(event);
	}

}
