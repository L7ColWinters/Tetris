package labs.brian.brianstetris;

import java.io.IOException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;

public class TetrisMusic implements OnAudioFocusChangeListener {

	private static TetrisMusic tm;
	
	private AudioManager am;
	private MediaPlayer player;
	private boolean paused = false;
	
	private TetrisMusic(Activity c) {
		am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
		c.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		player = new MediaPlayer();
		setDataSource(c,"tetris_theme.mp3");
	}
	
	public static TetrisMusic getInstance(Activity c){
		if(tm == null)
			tm = new TetrisMusic(c);
		return tm;
	}

	public void setDataSource(Activity c, String datasource){
		AssetFileDescriptor afd = null;
		try {
			afd = c.getAssets().openFd(datasource);
			player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			player.prepare();
			player.setLooping(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void playMusic() {
		if(player.isPlaying())
			return;
		
		int result = am.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
				AudioManager.AUDIOFOCUS_GAIN);
		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			playback();
		}
	}
	
	public void playback(){
		am.registerMediaButtonEventReceiver(new ComponentName("labs.brian.brianstetris","RemoteControlReceiver"));
		player.start();
	}
	
	public void reduceVolume(){
		player.setVolume(1, 1);
	}
	
	public void pauseMusic(){
		if(player.isPlaying()){
			player.pause();
			paused = true;
		}
	}

	public void stopMusic() {
		if(player.isPlaying())
			player.stop();
		am.unregisterMediaButtonEventReceiver(new ComponentName("labs.brian.brianstetris","RemoteControlReceiver"));
		am.abandonAudioFocus(this);
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		switch(focusChange){
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			reduceVolume();
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			pauseMusic();
			break;
		case AudioManager.AUDIOFOCUS_GAIN:
			playback();
			break;
		case AudioManager.AUDIOFOCUS_LOSS:
			stopMusic();
			break;
		}
	}
	
	public boolean isPlaying(){
		return player.isPlaying();
	}
	
	public boolean isPaused(){
		return paused;
	}
}
