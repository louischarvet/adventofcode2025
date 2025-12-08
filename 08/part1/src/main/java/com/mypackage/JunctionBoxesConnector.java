package com.mypackage;

import java.lang.Object;

import javafx.geometry.Point3D;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.mypackage.FileReader;

public class JunctionBoxesConnector {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java JunctionBoxesConnector <filename>");
			return ;
		}

		FileReader	reader = new FileReader(args[0]);
		List< String >	lines = reader.getFileLines();
		List< Point3D >	pointList = parseLines(lines);
		List< ConnectedPoint >	connectedPointList = connectPoints(pointList);

		for (Point3D point : pointList) {
			System.out.println(point);
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

	private static List< ConnectedPoint >	connectPoints(List< Point3D > pointList) {
		List< ConnectedPoint >	connectedPointList;
		int	i = 0;

		for (Point3D point : pointList) {
			Integer	id = i;
			
			i++;
		}
	}

	static class ConnectedPoint {
		private final Integer	_id;
		private	final Point3D	_point;
		private final Map< Integer, Double >	_distances;

		public connectedPoint(Integer id, Point3D point, Map< Integer, Double > distances) {
			this._id = id;
			this._point = point;
			this._distances = distances;
		}

		public Integer	getId() { return _id; }
		public Point3D	getPoint() { return _point; }
		public Map< Integer, Double >	getDistances() { return _distances; }
		public Double	getDistanceFrom(ConnectedPoint point) {
			return _distances.get(point.getId());
		}
	}
}