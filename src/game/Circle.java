package game;

//TODO: STUB
public class Circle extends Hitbox {
	private static final long serialVersionUID = 1L;
	Vector pos;
	double radius;
	
	public Circle() {
		pos = new Vector();
		radius = 0;
	}
	
	public Circle(double x, double y, double radius)
	{
		pos = new Vector(x, y);
		this.radius = radius;
	}

	@Override
	public double x()
	{
		return pos.getX();
	}

	@Override
	public Hitbox setX(double x)
	{
		pos.setX(x);
		return this;
	}

	@Override
	public double y()
	{
		return pos.getY();
	}

	@Override
	public Hitbox setY(double y)
	{
		pos.setY(y);
		return this;
	}
	
	float radius(){
		return (float)radius;
	}
	
	public Hitbox setRadius(float radius){
		this.radius = radius;
		return this;
	}
	
	@Override
	boolean collides(Circle circ)
	{
		return new Vector(pos).sub(new Vector(circ.x(), circ.y())).mag() < radius() + circ.radius();
	}
	
	@Override
	boolean collides(Rectangle rect)
	{
		double closestX = x();
		double closestY = y();

		if (x() < rect.x()) {
			closestX = rect.x();
		} else if (x() > rect.x() + rect.width()) {
			closestX = rect.x() + rect.width();
		}

		if (y() < rect.y()) {
			closestY = rect.y();
		} else if (y() > rect.y() + rect.height()) {
			closestY = rect.y() + rect.height();
		}

		closestX = closestX - x();
		closestX *= closestX;
		closestY = closestY - y();
		closestY *= closestY;

		return closestX + closestY < radius * radius;
	}

	@Override
	boolean contains(Vector point)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public void merge(Hitbox box) {
		Circle circ = (Circle)box;
		pos.set(circ.x(), circ.y());
		radius = circ.radius;
	}

	@Override
	public double centerX() {
		return pos.getX();
	}

	@Override
	public double centerY() {
		return pos.getY();
	}
	
	public String toString()
	{
		return x() + " " + y();
	}
}
