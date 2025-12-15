package com.mypackage;

import java.lang.Object;

import javafx.geometry.Point3D;

import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;

import com.mypackage.FileReader;
import com.mypackage.Pair;

public class JunctionBoxesConnector {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java JunctionBoxesConnector <filename>");
			return ;
		}

		FileReader	reader = new FileReader(args[0]);
		List< String >	lines = reader.getFileLines();
		List< Point3D >	pointList = parseLines(lines);
		Map< Point3D, Pair< Point3D, Double > >	closestPointMap = takeClosestPoints(pointList);
		// sort distances
		List< Pair< Point3D, Pair< Point3D, Double > > >	sortedPointDistances = sortPointsByDistance(closestPointMap);

		for (Pair< Point3D, Pair< Point3D, Double > > entry : sortedPointDistances) {
			System.out.println(entry.getFirst() + " " + entry.getSecond().getFirst() + " " + entry.getSecond().getSecond());
		}

		// for (Map.Entry< Point3D, Pair< Point3D, Double > > entry : closestPointMap.entrySet()) {
		// 	Point3D	point = entry.getValue().getFirst();
		// 	Double	distance = entry.getValue().getSecond();

		// 	System.out.println(entry.getKey());
		// 	System.out.println("\t" + point + "\n\t" + distance);
		// }

		List< Set< Point3D > > pointSetList = connectPoints(sortedPointDistances, 10);

		for (Set< Point3D > set : pointSetList) {
			System.out.println(set.size() + " " + set);
		}

	}

	private static List< Point3D >	parseLines(List< String > lines) {
		List< Point3D >	pointList = new ArrayList<>();

		for (String line : lines) {
			String	coordinatesStringArray[] = line.split(",");
			double	x = Double.parseDouble(coordinatesStringArray[0]),
				y = Double.parseDouble(coordinatesStringArray[1]),
				z = Double.parseDouble(coordinatesStringArray[2]);

			pointList.add(new Point3D(x, y, z));
		}

		return pointList;
	}

	private static Map< Point3D, Pair< Point3D, Double > >	takeClosestPoints(List< Point3D > pointList) {
		Map< Point3D, Pair< Point3D, Double > >	closestPointMap = new HashMap<>();

		for (Point3D point : pointList) {
			Map< Point3D, Double >	distances = calculateDistances(pointList, point);
			Pair< Point3D, Double >	closestPoint = findClosestPoint(distances);
			// System.out.println("\t" + point);
			// for (Map.Entry< Point3D, Double > entry : distances.entrySet()) {
			// 	System.out.println(entry.getKey() + "\t" + entry.getValue());
			// }
			// System.out.println();

			closestPointMap.put(point, closestPoint);
		}

		return closestPointMap;
	}

	private static List< Pair< Point3D, Pair< Point3D, Double > > >	sortPointsByDistance(Map< Point3D, Pair< Point3D, Double > > closestPointMap) {
		List< Pair< Point3D, Pair< Point3D, Double > > >	sortedPointDistances = new ArrayList<>();

		for (Map.Entry< Point3D, Pair< Point3D, Double > > entry : closestPointMap.entrySet()) {
			Double	distance = entry.getValue().getSecond();

			if (!inList(sortedPointDistances, entry.getKey(), entry.getValue().getFirst())) {
				sortedPointDistances.add(findIndex(sortedPointDistances, distance),
					new Pair< Point3D, Pair< Point3D, Double > >(entry.getKey(), entry.getValue()));
			}
		}

		return sortedPointDistances;
	}

	private static int	findIndex(List< Pair< Point3D, Pair< Point3D, Double > > >	sortedPointDistances, Double distance) {
		int	index = 0;

		for (Pair< Point3D, Pair< Point3D, Double > > pair : sortedPointDistances) {
			if (distance < pair.getSecond().getSecond())
				break ;
			index++;
		}

		return index;
	}

	private static boolean	inList(List< Pair< Point3D, Pair< Point3D, Double > > > list, Point3D a, Point3D b) {
		for (Pair< Point3D, Pair< Point3D, Double > > pair : list) {
			Point3D	first = pair.getFirst(),
				second = pair.getSecond().getFirst();
//			if ((a.getX() == first.getX() && a.getY() == first.getY() && a.getZ() == first.getZ()
//				&& b.getX() == second.getX() && b.getY() == second.getY() && b.getZ() == second.getZ())
//				|| (b.getX() == first.getX() && b.getY() == first.getY() && b.getZ() == first.getZ()
//				&& a.getX() == second.getX() && a.getY() == second.getY() && a.getZ() == second.getZ()))
			if ((a == first && b == second)
				|| (b == first && a == second))
				return true;
		}
		return false;
	}

	private static List< Set< Point3D > >	connectPoints(List< Pair< Point3D, Pair< Point3D, Double > > > sortedPointDistances, int limit) {
		List< Set< Point3D > >	pointSetList = new ArrayList<>();
		int	i = 0;

		for (Pair< Point3D, Pair< Point3D, Double > > entry : sortedPointDistances) {
			Point3D	a = entry.getFirst(),
				b = entry.getSecond().getFirst();
			// Set< Point3D >	pointSetA = findPointSet(pointSetList, a),
			// 	pointSetB = findPointSet(pointSetList, b);
//			System.out.println("found set: " + pointSet);
//			if (pointSetList.indexOf(pointSet) == -1)
//				findPointSet(pointSetList, b);

			addPointsToSet(pointSetList, a, b);

// 			pointSetA.add(b);
// 			pointSetB.add(a);

// //			if (pointSetList.indexOf(pointSet) == -1) {
// 			pointSetList.add(pointSetA);
// 			pointSetList.add(pointSetB);
//			}
			System.out.println("i = " + i);
			if (limit > 0 && ++i >= limit)
				break;
		}
		// for (Set< Point3D > setA : pointSetList) {
		// 	int	indexA = pointSetList.indexOf(setA);
		// 	for (Set< Point3D > setB : pointSetList) {
		// 		int	indexB = pointSetList.indexOf(setB);
		// 		if (indexA != indexB && setA.equals(setB)) {
		// 			System.out.println("EQUAL");
		// 			pointSetList.remove(setB);
		// 		}
		// 	}
		// }
		// List<Set<Point3D>> uniqueList = pointSetList.stream()
    	// 	.distinct()
    	// 	.collect(Collectors.toList());

	//	return uniqueList;
		return pointSetList;
	}

	private static void	addPointsToSet(List< Set< Point3D > >	pointSetList, Point3D a, Point3D b) {
		for (Set< Point3D > pointSet : pointSetList) {
			if (pointSet.contains(a)) {
				if (!pointSet.contains(b))
					pointSet.add(b);
				return ;
			}
			if (pointSet.contains(b)) {
				if (!pointSet.contains(a))
					pointSet.add(a);
				return ;
			}
		}
		Set< Point3D >	newSet = new HashSet<>();
		newSet.add(a);
		newSet.add(b);
		pointSetList.add(newSet);
	}

	private static Set< Point3D >	findPointSet(List< Set< Point3D > > pointSetList, Point3D point) {
		for (Set< Point3D > set : pointSetList) {
			if (set.contains(point))
				return set;
		}
		Set< Point3D >	newSet = new HashSet< Point3D >();
		newSet.add(point);
		
		return newSet;
	}

	private static Map< Point3D, Double >	calculateDistances(List< Point3D > pointList, Point3D currentPoint) {
		Map< Point3D, Double >	distances = new HashMap<>();
	//	Integer currentPointID = pointList.indexOf(currentPoint);
		System.out.println("currentPoint: " + currentPoint);
		for (Point3D point : pointList) {
			if (point != currentPoint) {
				Double	distance = currentPoint.distance(point);
				System.out.println("\t" + distance + "\tfrom " + point);

				distances.put(point, distance);
			}
		}
		System.out.println();

		return distances;
	}

	private static Pair< Point3D, Double >	findClosestPoint(Map< Point3D, Double > distances) {
		List< Map.Entry< Point3D, Double > > entryList = new ArrayList<>(distances.entrySet());
		Map.Entry< Point3D, Double >	closestPoint = entryList.get(0);

		for (Map.Entry< Point3D, Double > entry : distances.entrySet()) {
			if (closestPoint.getValue() > entry.getValue())
				closestPoint = entry;
		}

		return new Pair< Point3D, Double >(closestPoint.getKey(), closestPoint.getValue());
	}

	static class ConnectedPoint {
		private final Integer	_id;
		private	final Point3D	_point;
//		private final Map< Integer, Double >	_distances;
//		private final Double	_shortestDistance;

		public ConnectedPoint(Integer id, Point3D point, /* Map< Integer, Double > distances, */ Double shortestDistance, Point3D closestPoint) {
			this._id = id;
			this._point = point;
//			this._distances = distances;
//			this._shortestDistance = shortestDistance;
//			this._closestPoint = closestPoint;
		}

		public Integer	getId() { return _id; }
		public Point3D	getPoint() { return _point; }
//		public Map< Integer, Double >	getDistances() { return _distances; }
//		public Double	getDistanceFrom(ConnectedPoint point) {
//			return _distances.get(point.getId());
//		}
	}
}