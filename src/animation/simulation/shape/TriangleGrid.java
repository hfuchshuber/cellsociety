
package animation.simulation.shape;


import java.util.Iterator;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import structures.Grid;
import structures.cell.Cell;

/**
 * This is the Triangle Grid class which draws a simulation with triangle cells.
 * 
 * @author Hannah Fuchshuber
 */

public class TriangleGrid extends GridShape {

	/**
	 * Calls the super constructor
	 */
	public TriangleGrid() {
		super();
	}
	
	/**
	 * Draws the entire grid using triangles
	 */
	public Pane drawGrid(Grid grid, int w, int h) {
		Pane screen = super.drawGrid(grid, w, h);
		Iterator<Cell> itr = grid.iterator();
		int width = (int) w / (grid.getColumns() / 2);
		int height = (int) h / (grid.getRows()); 
		int wcount = (grid.getColumns() / 2);
		int vcount = (grid.getRows());
		int row = 1;
		while(itr.hasNext()) {
			Cell current = itr.next();
			insertStatesList(current.getCurrentState());
			screen.getChildren().add(fillGrid(current, w, h, wcount, vcount, width, height, row));
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
	
	/**
	 * Draws one triangle
	 * @param current
	 * @param screenwid
	 * @param screenheight
	 * @param wcount
	 * @param vcount
	 * @param width
	 * @param height
	 * @param row
	 * @return
	 */
	private Shape fillGrid(Cell current, double screenwid, double screenheight, int wcount, int vcount, double width, double height, int row) {
		Polygon triangle = new Polygon();
		if (row % 4 == 1) {
			triangle.getPoints().setAll(new Double[] {
					screenwid - (wcount + .5) * width, screenwid - (vcount - 1) * height,
					screenwid - (wcount - .5) * width, screenwid - (vcount - 1) * height,
					screenwid - (wcount) * width, screenwid - (vcount) * height });
		} else if (row % 4 == 2) {
			triangle.getPoints().setAll(new Double[] {
					screenwid - wcount * width, screenwid - vcount * height,
					screenwid - (wcount - 1) * width, screenwid - vcount * height,
					screenwid - (wcount - .5) * width, screenwid - (vcount - 1) * height });
		} else if (row % 4 == 3){
			triangle.getPoints().setAll(new Double[] {
					screenwid - (wcount - 1) * width, screenwid - (vcount - 1) * height,
					screenwid - (wcount) * width, screenwid -  (vcount - 1) * height,
					screenwid - (wcount - .5) * width, screenwid - (vcount) * height });
		} else {
			triangle.getPoints().setAll(new Double[] {
					screenwid - (wcount) * width, screenwid - (vcount - 1) * height,
					screenwid - (wcount - .5) * width, screenwid -  (vcount) * height,
					screenwid - (wcount + .5) * width, screenwid -  (vcount) * height });
		}
		triangle.setFill(getColor().getColor((current.getCurrentState())));
		return triangle;
	}


}
