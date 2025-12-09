//package mypackage;

import java.lang.Object;
import java.awt.geom.Point2D;
import java.awt.Point;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import mypackage.FileReader;

// 2147479840 too low

public class LargestAreaFinder {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java LargestAreaFinder <filename>");
				return ;
		}

		FileReader reader = new FileReader(args[0]);
		List< String >	lines = reader.getFileLines();
		List< Point >	points = parseLines(lines);
		List< BasicRectangle >	basicRectangles = initBasicRectangles(points);
		basicRectangles = sortRectanglesByArea(basicRectangles);
		long	largestArea = findLargestArea(basicRectangles);

		System.out.println("largestArea = " + largestArea);
	}

	private static List< Point >	parseLines(List< String > lines) {
		List< Point >	points = new ArrayList<>();

		for (String line : lines) {
			String	coordinates[] = line.split(",");
			points.add(new Point(
				Integer.parseInt(coordinates[0]),
				Integer.parseInt(coordinates[1])
			));
		}

		return points;
	}

	private static List< BasicRectangle >	initBasicRectangles(List< Point > points) {
		List< BasicRectangle >	basicRectangles = new ArrayList<>();
		List< Point >	usedPoints = new ArrayList<>();

		for (Point iPoint : points) {
			usedPoints.add(iPoint);
			for (Point jPoint : points) {
				if (!usedPoints.contains(jPoint)) {
					basicRectangles.add(new BasicRectangle(iPoint, jPoint));
				}
			}
		}

		return basicRectangles;
	}

	private static List< BasicRectangle >	sortRectanglesByArea(List< BasicRectangle > list) {
		List< BasicRectangle >	sortedList = new ArrayList<>();

		for (BasicRectangle elem : list) {
			if (sortedList.size() == 0)
				sortedList.add(elem);
			else {
				long	area = elem.getArea();
				for (BasicRectangle sortedElem : sortedList) {
					if (sortedElem.getArea() < area) {
						sortedList.add(sortedList.indexOf(sortedElem), elem);
						break ;
					}
				}
			}
		}

		return sortedList;
	}

	private static long	findLargestArea(List< BasicRectangle > basicRectangles) {
		long	largestArea = 0;

		for (BasicRectangle rectangle : basicRectangles) {
			long	area = rectangle.getArea();

			if (area > largestArea)
				largestArea = area;
		}

		return largestArea;
	}

	static class BasicRectangle {
		private final Point	oppositeCorners[];
		private final long	area;

		public BasicRectangle(Point a, Point b) {
			this.oppositeCorners = new Point[2];
			this.oppositeCorners[0] = a;
			this.oppositeCorners[1] = b;

			int	xa = a.x,
				ya = a.y,
				xb = b.x,
				yb = b.y,
				minX, maxX, minY, maxY;
			if (xa < xb) {
				minX = xa;
				maxX = xb;
			} else {
				minX = xb;
				maxX = xa;
			}
			if (ya < yb) {
				minY = ya;
				maxY = yb;
			} else {
				minY = yb;
				maxY = ya;
			}
			
			this.area = (long)(maxX - minX + 1) * (long)(maxY - minY + 1);
		}

		public Point	getA() { return this.oppositeCorners[0]; }
		public Point	getB() { return this.oppositeCorners[1]; }
		public long	getArea() { return this.area; }
	}
}