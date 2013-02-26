package labs.brian.beans;

import com.j256.ormlite.field.DatabaseField;

public class HighScoreInfo {
	
	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField
	public String name;
	@DatabaseField
	public int score;
	
	HighScoreInfo(){
		
	}
	
	public HighScoreInfo(String name,int score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", ").append("name=").append(name);
		sb.append(", ").append("score=").append(score);
		return sb.toString();
	}
}
