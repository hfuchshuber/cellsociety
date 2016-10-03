package animation.simulation.shape;


import java.util.Iterator;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import structures.Grid;
import structures.cell.Cell;

public class SquareGrid extends GridShape {

	
	public SquareGrid() {
		super();
	}

	public Pane drawGrid(Grid grid, int w, int h) {
		super.drawGrid(grid, w, h);
		Pane screen = new TilePane();
		screen.setMaxWidth(w);
		screen.setMaxHeight(h);
		screen.setPrefSize(w, h);
		((TilePane) screen).setHgap(1);
		((TilePane) screen).setVgap(1);
		double width = ((w + ((TilePane) screen).getHgap()) / (grid.getColumns()) - ((TilePane) screen).getHgap());
		double height = ((h + ((TilePane) screen).getVgap()) / (grid.getRows())) - ((TilePane) screen).getVgap();
		((TilePane) screen).setPrefColumns(grid.getColumns());
		((TilePane) screen).setTileAlignment(Pos.CENTER);
		Iterator<Cell> itr = grid.iterator();
		while(itr.hasNext()) {
			Cell current = itr.next();
			insertStatesList(current.getCurrentState());
			screen.getChildren().add(fillGrid(current, width, height));
		}
		return screen;
	}
	
	private Shape fillGrid(Cell current, double width, double height) {
		double dim = width;
		if (width < height) {
			dim = height;
		}
		Rectangle rectangle = new Rectangle();
		rectangle.setFill(getColor().getColor(current.getCurrentState()));
		rectangle.setWidth(dim);
		rectangle.setHeight(dim);
		return rectangle;
	}
	
	
}



