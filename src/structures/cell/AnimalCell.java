package structures.cell;

/*
 * Animal Cell which holds data for wa-tor
 */
public class AnimalCell extends Cell {
	private int time;
	private int energy;
	private int fishTime;
	private int sharkTime;
	
	public AnimalCell() {
		super();
		time = 0;
	}
	
	public AnimalCell(int cell_num, int cell_state, int initEnergy, int fish, int shark) {
		super(cell_num, cell_state);
		time = 0;
		energy = initEnergy;
		this.fishTime = fish;
		this.sharkTime = shark;
	}
	
	public int getTime() {
		return time;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public int getFishTime() {
		return fishTime;
	}
	
	public int getSharkTime() {
		return sharkTime;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
}
