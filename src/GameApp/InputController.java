package GameApp;

import GameLoop.GameLoop;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputController implements EventHandler<KeyEvent> {
	
	private boolean isMenuOpen = false;

	@Override
	public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE && event.getEventType() == KeyEvent.KEY_PRESSED) {
			if(!isMenuOpen) {
				GameLoop.getInstance().stop();
				GameLoop.getInstance().drawMenu();
				isMenuOpen = true;				
			} else {
				GameLoop.getInstance().start();
				isMenuOpen = false;
			}
		} else if( isMenuOpen && event.getCode() == KeyCode.R && event.getEventType() == KeyEvent.KEY_PRESSED) {
			GameLoop.getInstance().initGame();
			GameLoop.getInstance().start();
			isMenuOpen = false;
		} else if(!isMenuOpen) {
			GameLoop.getInstance().getPlayer().handle(event);
		}

	}

}
