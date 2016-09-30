package engine;

import java.util.ArrayList;
import structures.Cell;
import structures.Grid;

public class UpdateGameOfLife extends Update {
	private Grid grid;
	private Neighbors neighborsObject;
	
	public UpdateGameOfLife(Grid newGrid, Neighbors newNeighbors) {
		super(newGrid, newNeighbors);
		grid = newGrid;
		neighborsObject = newNeighbors;
	}
	
	public ArrayList<Cell> getNeighbors(Cell cell) {
		ArrayList<Cell> neighbors = super.getNeighbors(cell);
		neighbors.addAll(neighborsObject.getDiagonalNeighbors(cell));
		return neighbors;
	}
	
	/**
	 * Determines the next state of each cell based on the rules of the Game of Life. Each cell is one of two states
	 * (0 = dead, 1 = live). If a cell is live and has two or three live neighbors, it remains live. If a cell is live 
	 * and has less than two or more than three live neighbors, then the cell becomes dead. If a dead cell has exactly
	 * three live neighbors, then it becomes live.
	 */
	@Override
	public void determineUpdates() {
		for(Cell cell : grid.getCellList()) {
			int numLive = 0;
			for(Cell neighbor : getNeighbors(cell)) {
				if(neighbor.getCurrentState() == 1) {
					numLive++; //count number of live neighbors
				}
			}
			if(cell.getCurrentState() == 1) { //cell is live
				if(numLive < 2 || numLive > 3) {
					cell.setNextState(0);
				}
			} else { //cell is dead
				if(numLive == 3)
				cell.setNextState(1);
			}
		}
	}
}