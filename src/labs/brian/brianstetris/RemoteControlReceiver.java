package labs.brian.brianstetris;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

public class RemoteControlReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
			KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
				// Handle key press.
			}
			switch(event.getKeyCode()){
			case KeyEvent.KEYCODE_MEDIA_PLAY:
				
				break;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				
				break;
			case KeyEvent.KEYCODE_VOLUME_UP:
				
				break;
			}
		}
	}

}
