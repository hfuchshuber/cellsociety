
package animation.simulation.shape;


import java.util.Iterator;

import animation.simulation.color.CellColor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import structures.Cell;
import structures.Grid;
import structures.MetaData;

public class TriangleGrid extends GridShape {
	
	private CellColor myColor;
	
	
	public TriangleGrid() {
		super();
	}
	
	public TriangleGrid(MetaData meta) {
		super();
		myColor = meta.getColor();
	}
	
	public Pane drawGrid(Grid grid, int w, int h) {
		Pane screen = setUpScreen(grid, w, h);
		Iterator<Cell> itr = grid.iterator();
		int dimension = (int) w / (grid.getColumns() / 2);
		int wcount = (grid.getColumns() / 2);
		System.out.println(wcount);
		int vcount = (grid.getRows());
		int row = 1;
		while(itr.hasNext()) {
			Cell current = itr.next();
			screen.getChildren().add(fillGrid(current, w, h, wcount, vcount, dimension, row));
			wcount--;
			if (wcount == 0) {
				wcount = (grid.getColumns() / 2);
				row++;
				if (row % 2 != 0) {
					vcount--;
				}
			}
		}
		return screen;
	}
	

	private Shape fillGrid(Cell current, double screenwid, double screenheight, int wcount, int vcount, double dim, int row) {
		Polygon triangle = new Polygon();
		if (row % 4 == 1) {
			triangle.getPoints().setAll(new Double[] {
					screenwid - (wcount + .5) * dim, screenwid - (vcount - 1) * dim,
					screenwid - (wcount - .5) * dim, screenwid - (vcount - 1) * dim,
					screenwid - (wcount) * dim, screenwid - (vcount) * dim });
		} else if (row % 4 == 2) {
			triangle.getPoints().setAll(new Double[] {
					screenwid - wcount * dim, screenwid - vcount * dim,
					screenwid - (wcount - 1) * dim, screenwid - vcount * dim,
					screenwid - (wcount - .5) * dim, screenwid - (vcount - 1) * dim });
		} else if (row % 4 == 3){
			triangle.getPoints().setAll(new Double[] {
					screenwid - (wcount - 1) * dim, screenwid - (vcount - 1) * dim,
					screenwid - (wcount) * dim, screenwid -  (vcount - 1) * dim,
					screenwid - (wcount - .5) * dim, screenwid - (vcount) * dim });
		} else {
			triangle.getPoints().setAll(new Double[] {
					screenwid - (wcount) * dim, screenwid - (vcount - 1) * dim,
					screenwid - (wcount - .5) * dim, screenwid -  (vcount) * dim,
					screenwid - (wcount + .5) * dim, screenwid -  (vcount) * dim});
		}
		triangle.setFill(myColor.getColor((current.getCurrentState())));
		return triangle;
	}

}
