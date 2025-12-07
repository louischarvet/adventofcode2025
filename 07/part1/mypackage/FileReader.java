package mypackage;

import java.lang.Object;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class FileReader {
	private List< String >	fileLines;
	public FileReader(String fileName) {
		fileLines = readFile(fileName);
	}

	private static List< String >	readFile(String fileName) {
		List< String >	lines = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File(fileName))) {
			while (scanner.hasNextLine())
				lines.add(scanner.nextLine());
		} catch (Exception e) {
			System.err.println(e);
		}

		return lines;
	}

	public List< String >	getFileLines() {
		return this.fileLines;
	}
}