import java.lang.Object;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class RollsCounter {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java RollsCounter <filename>");
			return ;
		}
		List< String >	grid = readFile(args[0]);
		for (String line : grid) {
			System.out.println(line);
		}
		System.out.println("\n");
		int	count = countRolls(grid);

		System.out.println("count: " + count);
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

	private static int	countRolls(List< String > grid) {
		int	size = grid.size(),
			count = 0;
		List< String > gridCopy = new ArrayList<>();

		for (String line : grid) {
			int	lineIndex = grid.indexOf(line);			
			int	length = line.length();
			char	charArray[] = line.toCharArray();

			String lineCopy = "";

			for (int charIndex = 0; charIndex < length; charIndex++) {
				if (charArray[charIndex] == '@'
					&& countAround(grid, lineIndex, charIndex) < 4) {
					lineCopy += '.';
					count++;
				} else {
					lineCopy += charArray[charIndex];
				}
			}
			gridCopy.add(lineCopy);
		}

		for (String l : gridCopy) {
			System.out.println(l);
		}
		if (count == 0)
			return count;
		return count + countRolls(gridCopy);
	}

	private static int	countAround(List< String > grid, int lineIndex, int charIndex) {
		int	count = 0,
			start = (charIndex > 0 ? charIndex - 1 : charIndex),
			end;
		String	currentLine = grid.get(lineIndex),
			previousLine = lineIndex > 0 ? grid.get(lineIndex - 1) : "",
			nextLine = lineIndex < grid.size() - 1 ? grid.get(lineIndex + 1) : "";

		if (!previousLine.isEmpty()) {
			end = (charIndex < (previousLine.length() - 1) ? (charIndex + 1) : charIndex);
			for (int i = start; i <= end; i++) {
				if (previousLine.charAt(i) == '@')
					count++;
			}
		}

		end = (charIndex < (currentLine.length() - 1) ? (charIndex + 1) : charIndex);
		for (int i = start; i <= end; i++) {
			if (i != charIndex && currentLine.charAt(i) == '@')
				count++;
		}

		if (!nextLine.isEmpty()) {
			end = (charIndex < (nextLine.length() - 1) ? (charIndex + 1) : charIndex);
			for (int i = start; i <= end; i++) {
				if (nextLine.charAt(i) == '@')
					count++;
			}
		}

		return count;
	}
}