package labs.brian.customviews;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import labs.brian.brianstetris.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontTextView extends TextView {

	private static HashMap<String,SoftReference<Typeface>> typeFaceCache = new HashMap<String,SoftReference<Typeface>>();
	
	public CustomFontTextView(Context context) {
		super(context);
	}
	
	public CustomFontTextView(Context context, AttributeSet attrs){
		super(context,attrs);
		
		init(context,attrs);
	}
	
	public CustomFontTextView(Context context, AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		
		init(context,attrs);
	}
	
	private void init(Context c,AttributeSet attrs){
		TypedArray a = c.obtainStyledAttributes(attrs,R.styleable.MyTextStyle);
		String customFont = a.getString(R.styleable.MyTextStyle_font);
		
		Typeface tf = null;
		if(typeFaceCache.containsKey(customFont) && typeFaceCache.get(customFont) != null)
			tf = typeFaceCache.get(customFont).get();
		else{
			tf = Typeface.createFromAsset(c.getAssets(), customFont);
			typeFaceCache.put(customFont, new SoftReference<Typeface>(tf));
		}
		setTypeface(tf);
	}

}
