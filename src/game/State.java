package game;
import java.io.Serializable;
import java.util.ArrayList;

import game.buildings.Barracks;
import game.buildings.BasicTower;

public class State implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3411080766160081665L;
	static int SHARD_WIDTH = 640, SHARD_HEIGHT = 480;
	public int allegiance;
	
	State() {
		players = 2;
		characters = new Character[players];
		for(int i = 0; i < characters.length; i++) {
			characters[i] = new Character();
			characters[i].box = new Circle(20, 20, 30);
		}
		shards = new Shard[players];
		for(int i = 0; i < shards.length; i++) {
			shards[i] = new Shard();
			Shard shard = shards[i];
			shard.units = new ArrayList<>(players);
			for(int j = 0; j < players; j++) {
				shard.units.add(new ArrayList<>());
			}
			shard.characters = new Character[players];
			shard.characters[i] = characters[i];
			shard.buildings = new Tilemap<>();
			shard.buildings.set(new Reasoursepatch(), 0, 236);
			Rectangle h = new Rectangle(0, 0, 32, 32);
			Barracks b = new Barracks(h, 100, 0, 0, 120);
			shard.buildings.set(b, (int)b.box.x(), (int)b.box.y());
		}
	}
	int players;
	
	public static class Shard implements Serializable {
		private static final long serialVersionUID = 1L;
		public ArrayList<ArrayList<Unit>> units;
		public Character[] characters;
		public Tilemap<Building> buildings;
	}
	
	public Shard[] shards;
	public Character[] characters;
	void update() 
	{
		//checks to see if a unit is within a portal Note: portal is represented by a constant value not an entity may change later.
		for(int i = 0; i < players;i++)
		{
			Shard current = shards[i];
			for(int j = 0;j < current.units.get(i).size();j++)
			{
				//prints units current pos
				
				int tempx = (int)current.units.get(i).get(j).box.x();
				//int tempy = (int)current.units.get(i).get(j).box.y();
				if(tempx >= 608)
				{
					if(players == 2)
					{
						//checks to see what array the unit is in
						//spawns units 1 pixel in front of the portal can be changed by modifing the setx int
						if (i < 1)  
						{
							current.units.get(i+1).add(current.units.get(i).get(j));
							current.units.get(i+1).get(j).box.setX(607);
						}
						else
						{
							current.units.get(i-1).add(current.units.get(i).get(j));
							current.units.get(i+1).get(j).box.setX(607);
						}
						current.units.get(i).remove(current.units.get(i).get(j));
					}
				}
			}
			//teleports player
			for(int c = 0; c <current.characters.length;c++)
			{
				if(current.characters[c] != null)
					if(current.characters[c].box.x() >= 608)
					{
						if(i < 1)
						{
							shards[i + 1].characters[c] = current.characters[c];
							current.characters[c].box.setX(600);
							current.characters[c] = null;
						}
						else
						{
							shards[i - 1].characters[c] = current.characters[c];
							current.characters[c].box.setX(600);
							current.characters[c] = null;
						}
					}
			}
		}
		//Moves all characters by speed
		for(int i = 0; i < characters.length; i++)
		{
			double x = characters[i].box.x();
			double y = characters[i].box.y();
			x = x + characters[i].speed.getX();
			y = y + characters[i].speed.getY();
			characters[i].box.setX((float) x);
			characters[i].box.setY((float) y);
		}
		//Updates all units in all shards
		for(int i = 0; i < shards.length; i++)
		{
			for(int j = 0; j < shards[i].units.size(); j++)
			{
				for(int q = 0; q < shards[i].units.get(j).size(); q++)
				{
					shards[i].units.get(j).get(q).update(this);
				}
			}
			for(int x = 0; x < shards[i].buildings.data.size(); x++)
			{
				for(int y = 0; y < shards[i].buildings.data.get(x).size(); y++)
				{
					if(shards[i].buildings.data.get(x).get(y) != null)
					{
						shards[i].buildings.data.get(x).get(y).update(this);
					}
				}
			}
		}
	}
	
	void merge(State state) {
		//TODO: Merge states
		System.out.println(state.characters[0].box);
	}
}
