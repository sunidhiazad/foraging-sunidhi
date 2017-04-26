package patterns;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import operations.GeneralOperations;
import operations.Operations;
import operations.PathOperations;
import operations.SectorOperations;
import operations.VerifyPattern;
import comparators.SectorComparator;
import entities.AngularRange;
import entities.Cell;
import entities.Path;
import entities.SecLevel;
import entities.Sector;
import enums.MovementType;

public class SectorWiseDivision {
	int gridSize;
	int numAgents;
	Cell origin;
	List<Cell> grid = new ArrayList<Cell>();
	List<List<Cell>> diagonals = new ArrayList<List<Cell>>();
	List<AngularRange> ranges;

	public SectorWiseDivision(int gridSize, int numAgents) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.origin = new Cell(gridSize / 2, gridSize / 2, new BigDecimal(0));
		ranges = SectorOperations.getRanges(numAgents);
	}

	public List<Path> getPaths() {
		List<Sector> lstSectors = initializeSectors();
		lstSectors = divideCellsAmongSectors(lstSectors);
		lstSectors = managePaths(lstSectors);
		lstSectors = setStartTimes(lstSectors);
		lstSectors = setExpTimeSteps(lstSectors);
		return fromSectorsToPaths(lstSectors);
	}

	public List<Sector> getSectors() {
		List<Sector> lstSectors = initializeSectors();
		lstSectors = divideCellsAmongSectors(lstSectors);
		lstSectors = managePaths(lstSectors);
		lstSectors = setStartTimes(lstSectors);
		lstSectors = setExpTimeSteps(lstSectors);
		return lstSectors;
	}

	private List<Path> fromSectorsToPaths(List<Sector> lstSectors) {
		List<Path> paths = new ArrayList<Path>();
		Path p;
		for (Sector s : lstSectors) {
			p = new Path();
			p.setId(s.getId());
			p.setStart(s.getFirstPoint());
			p.setPath(assignTimeSteps(s.getExplorationPath(), s.getDepTimeStep()));
			p.setStartTime(s.getStartTime());
			p.setDepPath(s.getDepPath());
			p.setDepTime(s.getDepTimeStep());
			p.setExpTime(s.getExpTimeStep());
			p.setCompletePath(s.getFinalPath());
			paths.add(p);
		}
		return paths;
	}

	private List<Cell> assignTimeSteps(List<Cell> explorationPath, int depTimeStep) {
		int k = depTimeStep;
		for (Cell c : explorationPath) {
			c.setTimeStep(k++);
		}
		return explorationPath;
	}

	private List<Sector> setExpTimeSteps(List<Sector> lstSectors) {
		for (Sector s : lstSectors) {
			s.setExpTimeStep(s.getFinalPath().size() - 1);
		}
		return lstSectors;
	}

	private List<Sector> setStartTimes(List<Sector> lstSectors) {
		List<Sector> quad11 = new ArrayList<Sector>();
		List<Sector> quad21 = new ArrayList<Sector>();
		List<Sector> quad31 = new ArrayList<Sector>();
		List<Sector> quad41 = new ArrayList<Sector>();
		List<Sector> quad12 = new ArrayList<Sector>();
		List<Sector> quad22 = new ArrayList<Sector>();
		List<Sector> quad32 = new ArrayList<Sector>();
		List<Sector> quad42 = new ArrayList<Sector>();
		for (Sector s : lstSectors) {
			switch (s.getQuadCode()) {
			case 11:
				quad11.add(s);
				break;
			case 12:
				quad12.add(s);
				break;
			case 21:
				quad21.add(s);
				break;
			case 22:
				quad22.add(s);
				break;
			case 31:
				quad31.add(s);
				break;
			case 32:
				quad32.add(s);
				break;
			case 41:
				quad41.add(s);
				break;
			case 42:
				quad42.add(s);
				break;
			}
		}
		Collections.sort(quad11, new SectorComparator());
		Collections.sort(quad12, new SectorComparator());
		Collections.sort(quad21, new SectorComparator());
		Collections.sort(quad22, new SectorComparator());
		Collections.sort(quad31, new SectorComparator());
		Collections.sort(quad32, new SectorComparator());
		Collections.sort(quad41, new SectorComparator());
		Collections.sort(quad42, new SectorComparator());

		List<Sector> quad1 = new ArrayList<Sector>();
		List<Sector> quad2 = new ArrayList<Sector>();
		List<Sector> quad3 = new ArrayList<Sector>();
		List<Sector> quad4 = new ArrayList<Sector>();

		for (int i = 0; i < numAgents / 8; i++) {
			quad1.add(quad11.get(i));
			quad1.add(quad12.get(i));

			quad2.add(quad21.get(i));
			quad2.add(quad22.get(i));

			quad3.add(quad31.get(i));
			quad3.add(quad32.get(i));

			quad4.add(quad41.get(i));
			quad4.add(quad42.get(i));
		}

		int time = numAgents / 4;

		for (int i = 0; i < numAgents / 4; i++) {
			quad1.get(i).setStartTime(time);
			quad2.get(i).setStartTime(time);
			quad3.get(i).setStartTime(time);
			quad4.get(i).setStartTime(time);
			time--;
		}

		List<Sector> allSectors = new ArrayList<Sector>();
		allSectors.addAll(quad1);
		allSectors.addAll(quad2);
		allSectors.addAll(quad3);
		allSectors.addAll(quad4);

		allSectors = setFinalPathAndDepTimeStep(allSectors);

		return allSectors;
	}

	private List<Sector> setFinalPathAndDepTimeStep(List<Sector> sectors) {
		int start;
		List<Cell> finalPath;
		List<Cell> temp;
		for (Sector s : sectors) {
			start = s.getStartTime();
			finalPath = new ArrayList<Cell>();
			temp = new ArrayList<Cell>();

			for (int i = 0; i < start - 1; i++) {
				temp.add(origin);
			}
			s.setDepPath(appendOrigin(s.getDepPath(), temp));
			finalPath.addAll(temp);
			finalPath.addAll(s.getDepPath());
			finalPath.addAll(s.getExplorationPath());
			s.setFinalPath(finalPath);
			s.setDepTimeStep(s.getStartTime() + s.getDepDistance() - 1);
		}
		return sectors;
	}

	private List<Cell> appendOrigin(List<Cell> depPath, List<Cell> temp) {
		List<Cell> list = new ArrayList<Cell>();
		list.addAll(temp);
		list.addAll(depPath);
		int time = 0;
		for (Cell c : list) {
			c.setTimeStep(time++);
		}
		return list;
	}

	private List<Sector> managePaths(List<Sector> lstSectors) {
		SecLevel slevel;
		for (Sector s : lstSectors) {
			slevel = new SecLevel(s.getLevelMCode(), s.getCells());
			s.setCells(SectorOperations.getSortedCells(slevel));
			if (s.getId() == 0) {
				s.getCells().remove(origin);
			}
			s.setExplorationPath(validatePaths(s.getCells()));
			// if (s.getId() == 0)
			// s.setFirstPoint(s.getExplorationPath().get(1));
			// else
			s.setFirstPoint(s.getExplorationPath().get(0));

			s.setDepPath(getDeploymentPath(s.getFirstPoint(), s.getQuadCode()));
			s.setDepDistance(Operations.getDistance(s.getFirstPoint(), origin));
		}
		return lstSectors;
	}

	private List<Cell> getDeploymentPath(Cell firstPoint, int quadCode) {
		if (quadCode / 10 == 1 || quadCode / 10 == 3)
			return PathOperations.getDeltaXPath(origin, firstPoint);
		else
			return PathOperations.getDeltaYPath(origin, firstPoint);
	}

	private List<Cell> validatePaths(List<Cell> cells) {
		List<Cell> vPath = new ArrayList<Cell>();
		MovementType mtype = MovementType.DELTA_X;
		Cell first;
		Cell second;
		for (int i = 0; i < cells.size() - 1; i++) {
			first = cells.get(i);
			second = cells.get(i + 1);
			if (Operations.getDistance(first, second) == 1) {
				vPath.add(first);
			} else if (Operations.getDistance(first, second) > 1) {
				List<Cell> sub = getSubPath(first, second, cells);
				vPath.addAll(sub);
			}
			mtype = (mtype.equals(MovementType.DELTA_X)) ? MovementType.DELTA_Y : MovementType.DELTA_X;
		}
		vPath.add(cells.get(cells.size() - 1));
		return vPath;
	}

	private List<Cell> getSubPath(Cell first, Cell second, List<Cell> cells) {
		List<Cell> path = PathOperations.getPath(MovementType.DELTA_X, first, second);
		Boolean insidePath = cells.contains(path.get(1));
		if (!insidePath) {
			path = PathOperations.getPath(MovementType.DELTA_Y, first, second);
		}
		return path;
	}

	private List<Sector> divideCellsAmongSectors(List<Sector> lstSectors) {
		List<Sector> lstdivideCellsAmongSectors = lstSectors;
		// Assign slope to every cell
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				grid.add(new Cell(j, i, Operations.findAngle(new Cell(j, i), origin)));
			}
		}

		// Divide the cells among sectors depending upon the slope
		for (Cell c : grid) {
			int sec = -1;
			sec = getSectorId(c.getAngle());
			lstdivideCellsAmongSectors.get(sec).getCells().add(c);
		}

		// Sort the cells in each sector according to its respective mCode
		for (Sector s : lstdivideCellsAmongSectors) {
			s.setCells(SectorOperations.sortCells(s.getCells(), s.getLevelMCode()));
		}
		return lstdivideCellsAmongSectors;
	}

	private int getSectorId(BigDecimal angle) {

		AngularRange ar;
		for (int i = 0; i < ranges.size(); i++) {
			ar = ranges.get(i);
			if (angle.doubleValue() >= ar.getFrom().doubleValue() && angle.doubleValue() < ar.getTo().doubleValue())
				return i;
		}
		return 0;
	}

	private List<Sector> initializeSectors() {
		Sector sec;
		List<Sector> lstCreateSectors = new ArrayList<Sector>();
		for (int i = 0; i < numAgents; i++) {
			sec = new Sector(i, origin, ranges.get(i));
			sec.setQuadCode(getQuadCodeByI(i));
			lstCreateSectors.add(sec);
		}
		return lstCreateSectors;
	}

	private int getQuadCodeByI(int i) {
		int qdiv = numAgents / 4;
		int quad = 0;
		int subQDiv = 0;
		int subQuad = 0;
		if (i >= 0 && i < qdiv) {
			quad = 1;
			subQDiv = qdiv / 2;
		} else if (i >= qdiv && i < 2 * qdiv) {
			quad = 2;
			subQDiv = 3 * qdiv / 2;
		} else if (i >= 2 * qdiv && i < 3 * qdiv) {
			quad = 3;
			subQDiv = 5 * qdiv / 2;
		} else if (i >= 3 * qdiv && i < 4 * qdiv) {
			quad = 4;
			subQDiv = 7 * qdiv / 2;
		}
		if (i < subQDiv)
			subQuad = 1;
		else
			subQuad = 2;
		return quad * 10 + subQuad;
	}

	public static void main(String[] args) {
		System.out.println(Operations.getOrigin(65));
		GeneralOperations.displayCollection(new SectorWiseDivision(65, 48).getPaths());
	//	VerifyPattern.drawIt(new SectorWiseDivision(64, 32).getPaths(), 1000);
	}

}
