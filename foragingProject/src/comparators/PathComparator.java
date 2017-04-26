package comparators;

import java.util.Comparator;

import entities.Path;

public class PathComparator implements Comparator<Path> {

	@Override
	public int compare(Path path1, Path path2) {
		if (path1.getId() < path2.getId())
			return -1;
		else
			return 1;
	}

}
