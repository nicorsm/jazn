package model.interfaces;

public interface IBall {
	
	public IPosition getPosition();
	public void setPosition(int x, int y);
	public void setOwner(IPlayer owner);
	public IPlayer getOwner();

}
