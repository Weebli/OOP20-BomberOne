package bomberOne.model.gameObjects;

public interface PowerUp extends AbstractGameObject{

	public enum type{
		FirePower,
		Speed,
		Pierce,
		Time;
	}
	
	/**
	 * 
	 * @return True, if the PowerUp is dropped by the box
	 */
	public boolean isReleased();
	
	/**
	 * When the Box is destroyed, it calls this method
	 */
	public void release();
	
	/**
	 * 
	 * @return The type of the PowerUp
	 */
	public PowerUp.type getType();
}
