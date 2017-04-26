package oneagent;

import java.util.List;

import operations.FoodLocations;
import entities.Cell;
import enums.Pattern;

public class RunThis {
	static List<Cell> foodsoources1 = new FoodLocations(128, 0, 20, 40).get();
	static List<Cell> foodsoources2 = new FoodLocations(128, 20, 40, 40).get();
	static List<Cell> foodsoources3 = new FoodLocations(128, 40, 60, 40).get();
	static List<Cell> foodsoources4 = new FoodLocations(128, 60, 80, 40).get();
	static List<Cell> foodsoources5 = new FoodLocations(128, 80, 100, 40).get();
	static int i = 1;

	public static void main(String[] args) {
		for (Cell food : foodsoources1) {
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.STRIPES, food, 1));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.ZIG_ZAG, food, 1));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.HILBERT, food, 1));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.LOPEZ_ORTIZ1, food, 1));
		}

		for (Cell food : foodsoources2) {
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.STRIPES, food, 2));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.ZIG_ZAG, food, 2));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.HILBERT, food, 2));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.LOPEZ_ORTIZ1, food, 2));
		}

		for (Cell food : foodsoources3) {
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.STRIPES, food, 3));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.ZIG_ZAG, food, 3));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.HILBERT, food, 3));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.LOPEZ_ORTIZ1, food, 3));
		}

		for (Cell food : foodsoources4) {
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.STRIPES, food, 4));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.ZIG_ZAG, food, 4));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.HILBERT, food, 4));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.LOPEZ_ORTIZ1, food, 4));
		}

		for (Cell food : foodsoources5) {
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.STRIPES, food, 5));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.ZIG_ZAG, food, 5));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.HILBERT, food, 5));
			System.out.println(new OneAgentBean().getResult(i++, 128, Pattern.LOPEZ_ORTIZ1, food, 5));
		}
	}
}
