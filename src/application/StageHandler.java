package application;

import java.util.ArrayList;

import javafx.stage.Stage;

public class StageHandler {

	private static ArrayList<Stage> stageList = new ArrayList<Stage>();
	private static int index = 0;
	
	
	public static void addStage(Stage stage) {
		stageList.add(index++, stage);
	}
	
	public static Stage getCurrentStage() {
		return stageList.get(index - 1);
	}
	
	public  static Stage getPreviousStage() {
		return stageList.get(index - 2);
	}
	
	public static Stage removeCurrentStage() {
		return stageList.remove(--index);
	}
	
	public static void hidePreviousStage() {
		stageList.get(index - 2).close();
	}
	
	public static void showPreviousStage() {
		stageList.get(index - 2).show();
	}
	
	public static Stage closeCurrentStage() {
		Stage s = stageList.remove(--index);
		s.close();
		return s;
	}
	
	public static void showCurrentStage() {
		stageList.get(index - 1).show();
	}
}

