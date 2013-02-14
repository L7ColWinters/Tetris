package labs.brian.brianstetris;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DPad extends ImageView {
	
	private OnDpadCallBacks callback;
	
	public DPad(Context context) {
		super(context);
		init(context);
	}
	
	public DPad(Context context,AttributeSet attrs){
		super(context,attrs);
		init(context);
	}
	
	public DPad(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		init(context);
	}
	
	private void init(Context context){
		setImageResource(R.drawable.d_pad);
		setScaleType(ScaleType.FIT_XY);
		setAdjustViewBounds(true);
	}
	
	public void setDpadCallbacks(OnDpadCallBacks callback){
		this.callback = callback;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() != MotionEvent.ACTION_UP)
			return true;
		
		Point p = findGridLocation(getWidth()/3,getHeight()/3,event);
		switch(p.x){
			case 0:
				if(p.y == 1)
					callback.onLeft();
				break;
			case 1:
				if(p.y == 0)
					callback.onUp();
				else if(p.y == 2)
					callback.onDown();
				break;
			case 2:
				if(p.y == 1)
					callback.onRight();
				break;
		}
		
		
		return true;
	}
	
	private Point findGridLocation(float columnWidth,float columnHeight,MotionEvent event){
		int gridPositionX = 0;
		int gridPositionY = 0;
		Point p = new Point();
		
		for(float x = 0;x<getWidth();x+=columnWidth){
			for(float y = 0;y<getHeight();y+=columnHeight){
				if(event.getX() >= x && event.getX() <= (x+columnWidth) && event.getY() >= y && event.getY() <= (y+columnHeight)){
					p.x = gridPositionX;
					p.y = gridPositionY;
					return p;
				}
				
				gridPositionY ++;
			}
			gridPositionY = 0;
			gridPositionX ++;
		}
		return p;
	}
	
	public interface OnDpadCallBacks{
		public void onLeft();
		public void onUp();
		public void onRight();
		public void onDown();
	}

}
