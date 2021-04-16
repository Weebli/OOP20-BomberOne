package bomberOne.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import bomberOne.model.bomber.BomberImpl;
import bomberOne.model.common.P2d;
import bomberOne.model.enemy.Enemy;
import bomberOne.model.event.ExplosionEvent;
import bomberOne.model.event.HitBorderEvent;
import bomberOne.model.event.HitFireEvent;
import bomberOne.model.event.PickPowerUpEvent;
import bomberOne.model.event.WorldEventListener;
import bomberOne.model.event.WorldEventListenerImpl;
import bomberOne.model.factory.GameObjectFactory;
import bomberOne.model.factory.GameObjectFactoryImpl;
import bomberOne.model.gameObjects.Bomb;
import bomberOne.model.gameObjects.Fire;
import bomberOne.model.gameObjects.GameObject;
import bomberOne.model.gameObjects.GameObjectCollection;
import bomberOne.model.gameObjects.GameObjectCollectionImpl;
import bomberOne.model.gameObjects.PowerUp;
import bomberOne.model.user.Difficulty;
import bomberOne.model.user.Skins;
import bomberOne.tools.maps.Maps;


public class WorldImpl implements World {

	private static int ENEMYNUMBER = 3;
	private static int DIMENSION = 18;
	private static int FRAME = 32;
	
	private GameObjectCollection collection = new GameObjectCollectionImpl();
	private GameObjectFactory objectFactory = new GameObjectFactoryImpl();
	private WorldEventListener listener = new WorldEventListenerImpl();
	private BomberImpl bomberMan;
	private boolean respawn;
	private Difficulty difficulty;
	private List<List<String>> mapLayout;
		
	public WorldImpl(Difficulty difficulty, Skins skin) {
		this.difficulty=difficulty;
		if(difficulty.equals(Difficulty.STANDARD)) {
			this.respawn=false;
		} else {
			this.respawn=true;
		}
		this.bomberMan = (BomberImpl) objectFactory.createBomber(new P2d(32,32), skin);
		this.mapLayout = Maps.MAP1.getList();
		this.setHardWall();
		this.setBox();
	}
		
	/**
	 * This method creates all HardWall in the World
	 */
	private void setHardWall() {
		for(int y=0; y<WorldImpl.DIMENSION; y++) {
			for(int x=0; x<WorldImpl.DIMENSION; x++) {
				if(mapLayout.get(y).get(x).equals("H")) {
					collection.spawn(objectFactory.createHardWall(new P2d(x*WorldImpl.FRAME, y*WorldImpl.FRAME)));
				}
			}
		}
	}
	
	/**
	 * This method creates all HardWall in the World
	 */
	private void setBox() {
		
	}
	
	@Override
	public boolean getRespawn() {
		return this.respawn;
	}

	@Override
	public GameObjectCollection getGameObjectCollection() {
		return collection;
	}

	@Override
	public GameObjectFactory getGameObjectFactory() {
		return objectFactory;
	}

	@Override
	public void setEventListener(WorldEventListener event) {
		this.listener=event;
	}

	@Override
	public BomberImpl getBomber() {
		return this.bomberMan;
	}

	@Override
	public void updateState(int time) {
		this.bomberMan.update(time);
		for(GameObject obj : collection.getGameObjectCollection()) {
			obj.update(time);
		}
		List<GameObject> deathObject = collection.getGameObjectCollection().stream()
				.filter(p -> p.isAlive() == false).collect(Collectors.toList());
		for(GameObject obj : deathObject) {
			collection.despawn(obj);
		}
	}

	@Override
	public void checkCollision() {
		List<Fire> fireList = collection.getFireList();
		List<GameObject> list = new LinkedList<>();
		list.add(bomberMan);
		list.addAll(collection.getBoxList());
		list.addAll(collection.getEnemyList());
		for(GameObject obj : list) {
			for(Fire fire : fireList) {
				if(fire.getBoundingBox().isCollidingWith(obj.getBoundingBox())) {
					this.listener.notifyEvent(new HitFireEvent(obj));
				}
			}
		}
		List<PowerUp> powerUpList = collection.getPowerUpList().stream()
				.filter(p -> p.isReleased() == true).collect(Collectors.toList());
		for(PowerUp power : powerUpList) {
			if(power.getBoundingBox().isCollidingWith(bomberMan.getBoundingBox())) {
				this.listener.notifyEvent(new PickPowerUpEvent(power));
			}
		}
	}

	@Override
	public void checkRespawn() {
		if(collection.getBoxList().size() == 0) {
				this.respawn=false;
		}
		if(this.respawn) {
			if(collection.getEnemyList().size() != WorldImpl.ENEMYNUMBER) {
				collection.spawn(objectFactory.createEnemy(new P2d(32,32), this.difficulty)); //da mettere in mezzo alla mappa
			}
		}
	}

	@Override
	public void checkBoundary() {
		List<Enemy> enemyList = collection.getEnemyList();
		List<GameObject> wallBoxList = new LinkedList<>();
		wallBoxList.addAll(collection.getHardWallList());
		wallBoxList.addAll(collection.getBoxList());
		for(GameObject wall : wallBoxList) {
			if(wall.getBoundingBox().isCollidingWith(this.bomberMan.getBoundingBox())) {
				listener.notifyEvent(new HitBorderEvent(this.bomberMan, wall));
			}
		}
		for(Enemy enemy : enemyList) {
			for(GameObject wall : wallBoxList) {
				if(wall.getBoundingBox().isCollidingWith(enemy.getBoundingBox())) {
					listener.notifyEvent(new HitBorderEvent(enemy, wall));
				}
			}
		}
	}

	@Override
	public void checkExplosion() {
		List<Bomb> bombList= collection.getBombList();
		for(Bomb bomb : bombList) {
			if(!bomb.getExplosion().equals(Optional.empty())) {
				listener.notifyEvent(new ExplosionEvent(bomb.getExplosion().get()));
			}
		}
	}

}
