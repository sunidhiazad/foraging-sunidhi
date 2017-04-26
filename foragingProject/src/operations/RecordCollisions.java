package operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import entities.Cell;
import entities.Sector;

public class RecordCollisions {
	private List<Sector> sectors;
	private List<Cell> cells;

	public RecordCollisions(List<Sector> sec) {
		this.sectors = sec;
		cells = new ArrayList<Cell>();
		for (Sector s : sectors) {
			s = setTimeAndSectorForEveryCell(s);
			cells.addAll(s.getFinalPath());
		}

		Multimap<Integer, Cell> mmap = ArrayListMultimap.create();
		for (Cell c : cells) {
			mmap.put(c.getTimeStep(), c);
		}
		
		Map<Integer, Collection<Cell>> map=mmap.asMap();
		GeneralOperations.printMap(map);
	}

	private Sector setTimeAndSectorForEveryCell(Sector s) {
		List<Cell> path = s.getFinalPath();
		for (int i = 1; i < path.size(); i++) {
			path.get(i).setTimeStep(s.getStartTime() + i - 1);
			path.get(i).setPathid(s.getId());
		}
		s.setFinalPath(path);
		return s;
	}
}
