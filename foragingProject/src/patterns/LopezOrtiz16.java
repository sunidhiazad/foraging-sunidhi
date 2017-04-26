package patterns;

import java.util.ArrayList;
import java.util.List;

import operations.GeneralOperations;
import operations.VerifyPattern;
import robotsearch2.Coordinate;
import robotsearch2.Robot;
import robotsearch2.Runner;
import entities.Cell;
import entities.Path;

public class LopezOrtiz16 {

	List<Path> paths = new ArrayList<Path>();

	public List<Path> getPaths() {
		return paths;
	}

	public LopezOrtiz16() {
		List<Robot> robots = Runner.getRobots();
		List<Coordinate> tempcoor = new ArrayList<Coordinate>();
		List<Cell> temp = new ArrayList<Cell>();
		int i=0;
		for (Robot r : robots) {
			tempcoor = r.getHistory();
			for (Coordinate coor : tempcoor) {
				temp.add(new Cell(coor.getX() + 64, coor.getY() + 64));
			}
			paths.add(new Path(i++, new ArrayList<Cell>(temp)));
			temp.clear();
		}
		setStarts();
		//GeneralOperations.displayCollection(paths);
	}

	private void setStarts() {
		int i = 0,j=0;
		for (Path p : paths) {
			p.setStart(p.getPath().get(0));
			p.setStartTime(i++);
			p.setDepPath(new ArrayList<Cell>());
			for(Cell c:p.getPath()){
				c.setTimeStep(j++);
			}
			j=0;
		}
	}

	public static void main(String[] args) {
		new LopezOrtiz16();
	}
}
