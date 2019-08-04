package Utils;

import javafx.scene.image.Image;

public enum Sprite {
	// *** SKELETON
	GRASS("/resources/images/grass.png"),
	STANDINGRIGHT("/resources/images/standingRight.png"),
	RUNRIGHT1("/resources/images/runRight1.png"),
	RUNRIGHT2("/resources/images/runRight2.png"),
	STANDINGLEFT("/resources/images/standingLeft.png"),
	RUNLEFT1("/resources/images/runLeft1.png"),
	RUNLEFT2("/resources/images/runLeft2.png"),
	DEAD1("/resources/images/dead1.png"),
	DEAD2("/resources/images/dead2.png"),
	DEAD3("/resources/images/dead3.png"),
	DEAD4("/resources/images/dead4.png"),
	DEAD5("/resources/images/dead5.png"),
	DEAD6("/resources/images/dead6.png"),

	// **** ASTRONAUT
	ASTROSTANDINGRIGHT("/resources/images/astroStandingRight.png"),
	ASTRORUNRIGHT1("/resources/images/astroRunRight1.png"),
	ASTRORUNRIGHT12("/resources/images/astroRunRight12.png"),
	ASTRORUNRIGHT2("/resources/images/astroRunRight2.png"),
	ASTROJUMPRIGHT("/resources/images/astroJumpRight.png"),
	ASTROSTANDINGLEFT("/resources/images/astroStandingLeft.png"),
	ASTRORUNLEFT1("/resources/images/astroRunLeft1.png"),
	ASTRORUNLEFT12("/resources/images/astroRunLeft12.png"),
	ASTRORUNLEFT2("/resources/images/astroRunLeft2.png"),
	ASTROJUMPLEFT("/resources/images/astroJumpLeft.png"),
	ASTRODEAD1("/resources/images/astroDead1.png"),
	ASTROWIN1("/resources/images/astroWin1.png"),
	
	
	// ***** FIELD
	LUNARTERRAIN1("/resources/images/lunarTerrain1.png"),
	LUNARTERRAIN2("/resources/images/lunarTerrain2.png"),
	
	//ROCKET
	BLACKROCKET("/resources/images/blackRocket.png"),
	ROCKET1("/resources/images/rocket1.png"),
	ROCKET2("/resources/images/rocket2.png"),
	ROCKET3("/resources/images/rocket3.png"),
	MENU("/resources/images/menu.png"),
	BACKGROUND("/resources/images/background.png"),
	BACKGROUND2("/resources/images/background2.png"),
	LIFE("/resources/images/life.png"),
	GOAL("/resources/images/goal.png");
	
	private final Image image;

	private Sprite(String path) {
		image = new Image(getClass().getResource(path).toExternalForm());
	}

	public Image getSpriteImage() {
		return image;
	}
}
