package animation.simulation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import animation.controls.button.ButtonGo;
import animation.controls.button.ButtonPause;
import animation.controls.button.ButtonStop;
import animation.controls.button.ButtonString;
import animation.navigation.Navigator;
import animation.simulation.shape.GridShape;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import readxml.XmlMapper;



public class GUISimulation {
	
	private StackPane stack;
	private GridShape myGridIllustrator;
	private Pane animation;
	private Scene myScene;
	private Timeline engine;
	private XmlMapper myInfo;
	private ScrollPane scroll;
	private int myTime;
	private LineChart<Number, Number> myChart;
	private Navigator myNav;
	private Map<Integer, ArrayList<Point>> pointsList = new HashMap<Integer, ArrayList<Point>>();
	
	public GUISimulation(Scene scene, XmlMapper info, Timeline animation) {
		this.myScene = scene;
		myGridIllustrator = info.getMeta().getGridShape();
		myGridIllustrator.setColor(info.getMeta().getColor());
		stack = new StackPane();
		this.engine = animation;
		myInfo = info;
		scroll = new ScrollPane();
		myTime = info.getGrid().getNumCells();
		for (int i = 0; i <= info.getMeta().getNumStates(); i++) {
			System.out.println(i);
			pointsList.put(i, new ArrayList<Point>());
		}
	}
	
	public Pane getStackPane() {
		return stack;
	}

	public Pane generateSimulationScreen() {
		stack.getChildren().remove(scroll);
		double left = myScene.getWidth() * .1;
	    double top = myScene.getHeight() * .53;
	    double other = myScene.getHeight() * .1;
	    int width = (int) Math.round((myScene.getWidth() * .6 - other));
	    int height = (int) Math.round((myScene.getWidth() * .6 - other));
	    scroll = makeScrollPane(width, height);
	    animation = myGridIllustrator.drawGrid(myInfo.getGrid(), width, height);
	    scroll.setContent(animation);
	    StackPane.setMargin(scroll, new Insets(left, top, other, other));
        stack.getChildren().add(scroll);
        stack.setMouseTransparent(true);
		return stack;
	}
	
	public Pane generateSimulationScreenButton(ResourceBundle resources, Group root) {
		Button play = (new ButtonGo()).getButton();
		play.setOnAction(e -> play());
		Button pause = (new ButtonPause()).getButton();
		pause.setOnAction(e -> stopAnimation());
		Button stop = (new ButtonStop()).getButton();
		stop.setOnAction(e -> restart(root));
		HBox hbox = new HBox((myScene.getWidth() * .40) / 3);
		hbox.getChildren().addAll(play, pause, stop);
		hbox.setLayoutX(myScene.getWidth() * .125);
		hbox.setLayoutY(myScene.getHeight() * .92);
		return hbox;
	}
	
	public Pane generateSimulationScreenControls(ResourceBundle resources) {
		VBox vbox = new VBox(10);
		Button speed = (new ButtonString(resources.getString("ButtonSpeed"))).getButton();
		speed.setPrefWidth(myScene.getWidth() * .20);
		speed.setOnAction(e -> engine.setRate(engine.getCurrentRate() + 2));
		Button slow = (new ButtonString(resources.getString("ButtonSlow"))).getButton();
		slow.setPrefWidth(myScene.getWidth() * .20);
		slow.setOnAction(e -> engine.setRate(engine.getCurrentRate() - 2));
		Button step = (new ButtonString(resources.getString("ButtonStep"))).getButton();
		step.setPrefWidth(myScene.getWidth() * .20);
		step.setOnAction(e -> step());
		vbox.getChildren().addAll(speed, slow, step);
		vbox.setLayoutX(myScene.getWidth() * .73);
		vbox.setLayoutY(myScene.getHeight() * .15);
		return (Pane) vbox;
	}
	
	public LineChart<Number, Number> generatSimulationChart(ResourceBundle resources) {
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(resources.getString("LineChartXTitle"));
		yAxis.setLabel(resources.getString("LineChartYTitle"));
		myChart = new LineChart<Number, Number>(xAxis, yAxis); 
		myChart.setTitle(resources.getString("LineChartTitle"));
		Iterator<Integer> stateItr = myGridIllustrator.iterator();
		int count = 0;
		while(stateItr.hasNext()) {
			int cellnum = stateItr.next();
			myTime = myTime + myInfo.getGrid().getNumCells();
			pointsList.get(count).add(new Point(myTime, cellnum));
			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
			for (int i = 0; i < pointsList.get(count).size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(pointsList.get(count).get(i).getX(), pointsList.get(count).get(i).getY()));
			}
			myChart.getData().add(series);
			count++;
		}
		myChart.setLayoutX(myScene.getWidth() * .60);
		myChart.setLayoutY(myScene.getHeight() * .52);
		myChart.setMaxHeight(myScene.getHeight() * .15);
		myChart.setMaxWidth(myScene.getWidth() * .38);
		return myChart;
	}
	
	public void play() {
		engine.setCycleCount(Timeline.INDEFINITE);
		engine.play();
	}
	
	public void stopAnimation() {
		engine.stop();
	}
	
	private void step() {
		stopAnimation();
		engine.setCycleCount(1);
		engine.play();
	}
	
	private void restart(Group root) {
		stopAnimation();
		root.getChildren().clear();
		myInfo.mapXml(myInfo.getMeta().getFileName());
		myNav = new Navigator(myScene, root, myInfo);
		myNav.createSimluationMenu();
	}
	
	private ScrollPane makeScrollPane(double width, double height) {
		scroll.setMaxWidth(width);
	    scroll.setMaxHeight(height);
	    scroll.setFitToWidth(true);
	    scroll.setFitToHeight(true);
	    scroll.setPrefSize(width, height);
	    scroll.setPrefViewportWidth(width);
	    scroll.setPrefViewportHeight(height); 
	    scroll.setMinViewportHeight(height);
	    scroll.setMinViewportWidth(width);
	    return scroll;
	}

	public LineChart<Number, Number> getLineChart() {
		return myChart;
	}
	
}
