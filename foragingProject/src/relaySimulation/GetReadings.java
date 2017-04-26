package relaySimulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import operations.FoodLocations;
import operations.ReadInput;
import operations.WriteOutput;
import entities.Agent;
import entities.Cell;
import entities.Parameters;

public class GetReadings {
	List<Parameters> params;

	public GetReadings() {
		GetReadingsfood(); 
		//GetReadingsSep();
	}

	public void GetReadingsfood() {
		
		params = new ReadInput().readInput();
		List<Agent> lstAgents = new ArrayList<Agent>();
		for (Parameters p : params) {
			switch (p.getPattrn()) {
			case STRIPES:
				System.out.println("I came here");
				lstAgents = new StripeSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case ZIG_ZAG:
				lstAgents = new ZigZagSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case HILBERT:
				lstAgents = new HilbertSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case SECTORS:
				if (p.getNumAgents() == 4)
					lstAgents = new ZigZagSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				else
					lstAgents = new SectorSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case LOPEZ_ORTIZ4:
				lstAgents = new LopezOrtizSimulation4(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case LOPEZ_ORTIZ8:
				lstAgents = new LopezOrtizSimulation8(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case ADVANCED_SECTORS:
				lstAgents = new AdvancedSectorSimulation4(new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case LOPEZ_ORTIZ16:
				lstAgents = new LopezOrtiz16_simulation(new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case NULL:
				break;
			default:
				break;
			}
			p = setPhaseTimes(p, lstAgents);
			System.out.println(p);
			System.gc();
		}
		new WriteOutput(params);
	}

	public void GetReadingsSep() {
		params = new ReadInput().readInput();
		List<Agent> lstAgents = new ArrayList<Agent>();
		Cell food = Cell.NULL_CELL;
		for (Parameters p : params) {
			if (p.getSeparation() == 1)
				food = new FoodLocations(p.getGridSize(), 0, 20, 1).get().get(0);
			else if (p.getSeparation() == 2)
				food = new FoodLocations(p.getGridSize(), 20, 40, 1).get().get(0);
			else if (p.getSeparation() == 3)
				food = new FoodLocations(p.getGridSize(), 40, 60, 1).get().get(0);
			else if (p.getSeparation() == 4)
				food = new FoodLocations(p.getGridSize(), 60, 80, 1).get().get(0);
			else if (p.getSeparation() == 5)
				food = new FoodLocations(p.getGridSize(), 80, 100, 1).get().get(0);
			else
				food = new FoodLocations(p.getGridSize(), 0, 100, 1).get().get(0);
			switch (p.getPattrn()) {
			case STRIPES:
				lstAgents = new StripeSimulation(p.getGridSize(), p.getNumAgents(), food).getAgents();
				break;
			case ZIG_ZAG:
				lstAgents = new ZigZagSimulation(p.getGridSize(), p.getNumAgents(), food).getAgents();
				break;
			case HILBERT:
				lstAgents = new HilbertSimulation(p.getGridSize(), p.getNumAgents(), food).getAgents();
				break;
			case SECTORS:
				if (p.getNumAgents() == 4)
					lstAgents = new ZigZagSimulation(p.getGridSize(), p.getNumAgents(), food).getAgents();
				else
					lstAgents = new SectorSimulation(p.getGridSize(), p.getNumAgents(), food).getAgents();
				break;
			case LOPEZ_ORTIZ4:
				lstAgents = new LopezOrtizSimulation4(p.getGridSize(), p.getNumAgents(), food).getAgents();
				break;
			case LOPEZ_ORTIZ8:
				lstAgents = new LopezOrtizSimulation8(p.getGridSize(), p.getNumAgents(), food).getAgents();
				break;
			case ADVANCED_SECTORS:
				lstAgents = new AdvancedSectorSimulation4(food).getAgents();
				break;
			case NULL:
				break;
			default:
				break;
			}
			p = setPhaseTimes(p, lstAgents);
			p.setFoodX(food.getX());
			p.setFoodY(food.getY());
			System.out.println(p);
			System.gc();
		}
		new WriteOutput(params);
	}

	private Parameters setPhaseTimes(Parameters p, List<Agent> lstAgents) {
		int foodDiscoveryTime = 0;
		List<Integer> idtimes = new ArrayList<Integer>();
		List<Integer> commtimes = new ArrayList<Integer>();

		for (Agent a : lstAgents) {
			if (a.getFoodLocator())
				foodDiscoveryTime = a.getExPhaseFlag().getTimeStep();
			if (a.getCommPhaseFlag().getTimeStep() > 0)
				commtimes.add(a.getCommPhaseFlag().getTimeStep());
			idtimes.add(a.getInDePhaseFlag().getTimeStep());
		}
		p.setInitDepTS(Collections.max(idtimes));
		p.setFoodDiscoveryTS(foodDiscoveryTime);
		p.setFirstCommTS(Collections.min(commtimes));
		p.setFirstTransTS(Collections.max(commtimes));
		p.setFoodTransportTS(lstAgents.get(0).getTransTime());
		return p;
	}

	public static void main(String[] args) {
		new GetReadings();
	}
}
