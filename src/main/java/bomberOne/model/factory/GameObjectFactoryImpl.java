package bomberOne.model.factory;

import java.awt.image.BufferedImage;

import bomberOne.model.bomber.BomberImpl;
import bomberOne.model.common.P2d;
import bomberOne.model.gameObjects.BombImpl;
import bomberOne.model.gameObjects.BoxImpl;
import bomberOne.model.gameObjects.FireImpl;
import bomberOne.model.gameObjects.GameObject;
import bomberOne.model.gameObjects.HardWall;
import bomberOne.model.gameObjects.PowerUp;
import bomberOne.model.gameObjects.PowerUp.type;
import bomberOne.model.user.Difficulty;
import bomberOne.model.user.Skins;
import bomberOne.model.gameObjects.PowerUpImpl;
import bomberOne.tools.img.ImagesObj;
import bomberOne.tools.img.SpriteMapsObj;

public class GameObjectFactoryImpl implements GameObjectFactory {

	private static final int BOMBER_LIFES = 3;
	
	@Override
	public GameObject createBomber(P2d position, Skins color) {
		BufferedImage [][] images = null;
		if(color.equals(Skins.WHITE)) {
			images = SpriteMapsObj.PLAYER_1.getSprites();
		}
		if(color.equals(Skins.BLACK)) {
			images = SpriteMapsObj.PLAYER_2.getSprites();
		}
		if(color.equals(Skins.RED)) {
			images = SpriteMapsObj.PLAYER_3.getSprites();
		}
		if(color.equals(Skins.BLUE)) {
			images = SpriteMapsObj.PLAYER_4.getSprites();
		}
		return new BomberImpl(position, images, GameObjectFactoryImpl.BOMBER_LIFES, true);
	}

	@Override
	public GameObject createEnemy(P2d position, Difficulty diff) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameObject createBox(P2d position) {
		return new BoxImpl(position, ImagesObj.BOX.getImage(), 1, true);
	}

	@Override
	public GameObject createPowerUp(P2d position, type type) {
		
		BufferedImage powerUpImage = new BufferedImage(0, 0, 0);
		if(type.equals(PowerUp.type.FirePower)) {
			powerUpImage = ImagesObj.POWER_FIREPOWER.getImage();
		}
		if(type.equals(PowerUp.type.Pierce)) {
			powerUpImage = ImagesObj.POWER_PIERCE.getImage();
		}
		if(type.equals(PowerUp.type.Speed)) {
			powerUpImage = ImagesObj.POWER_SPEED.getImage();
		}
		if(type.equals(PowerUp.type.Time)) {
			powerUpImage = ImagesObj.POWER_TIMER.getImage();
		}
		PowerUp pU = new PowerUpImpl(position, powerUpImage, 1, true, type);
		return pU;
	}

	@Override
	public GameObject createHardWall(P2d position) {
		return new HardWall(position, ImagesObj.HARDWALL.getImage(), 1, false);
	}

	@Override
	public GameObject createFire(P2d position) {
		return new FireImpl(position, ImagesObj.FIRE.getImage(), 1, false);
	}

	@Override
	public GameObject createBomb(P2d position, int firePower, boolean pierce) {
		return new BombImpl(position, ImagesObj.BOMB.getImage(), 1, false, firePower, pierce);
	}

}
