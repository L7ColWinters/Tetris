package labs.brian.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import labs.brian.beans.HighScoreInfo;
import labs.brian.brianstetris.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HighScoreAdapter extends BaseAdapter {

	private Context context;
	private List<HighScoreInfo> scores;
	
	public HighScoreAdapter(Context context){
		super();
		this.context = context;
		scores = new ArrayList<HighScoreInfo>();
	}
	
	public void setData(List<HighScoreInfo> data){
		scores = data;
		Collections.sort(scores, new Comparator<HighScoreInfo>(){
			@Override
			public int compare(HighScoreInfo l, HighScoreInfo r) {
				return l.score - r.score;
			}
		});
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return scores.size();
	}

	@Override
	public Object getItem(int pos) {
		return scores.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.highscoreslistrow, null,false);
		
		HighScoreInfo hsi = scores.get(pos);
		if(hsi != null){
			((TextView)convertView.findViewById(R.id.highscorename)).setText(hsi.name);
			((TextView)convertView.findViewById(R.id.highscorescore)).setText(String.valueOf(hsi.score));
		}
		
		return convertView;
	}

}
