package directSimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import operations.ReadInput;
import operations.WriteOutput;
import relaySimulation.AdvancedSectorSimulation4;
import entities.Agent;
import entities.Cell;
import entities.Parameters;

public class GetDirReadings {
	List<Parameters> params;

	public GetDirReadings() {
		params = new ReadInput().readInput();
		List<Agent> lstAgents = new ArrayList<Agent>();
		for (Parameters p : params) {
			switch (p.getPattrn()) {
			case STRIPES:
				lstAgents = new StripeDirSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case ZIG_ZAG:
				lstAgents = new ZigZagDirSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case HILBERT:
				lstAgents = new HilbertDirSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case SECTORS:
				if (p.getNumAgents() == 4)
					lstAgents = new ZigZagDirSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				else
					lstAgents = new SectorDirSimulation(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case LOPEZ_ORTIZ4:
				lstAgents = new LopezOrtizDirSimulation4(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case LOPEZ_ORTIZ8:
				lstAgents = new LopezOrtizDirSimulation8(p.getGridSize(), p.getNumAgents(), new Cell(p.getFoodX(), p.getFoodY())).getAgents();
				break;
			case ADVANCED_SECTORS:
				lstAgents = new AdvancedSectorSimulation4(new Cell(p.getFoodX(), p.getFoodY())).getAgents();
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

	private Parameters setPhaseTimes(Parameters p, List<Agent> lstAgents) {
		int foodDiscoveryTime = 0;
		List<Integer> idtimes = new ArrayList<Integer>();
		List<Integer> commtimes = new ArrayList<Integer>();
		
		for (Agent a : lstAgents) {
			if (a.getFoodLocator())
				foodDiscoveryTime = a.getExPhaseFlag().getTimeStep();
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
		new GetDirReadings();
	}
}
