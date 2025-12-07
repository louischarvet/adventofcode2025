import java.lang.Object;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Stack;

import mypackage.FileReader;
import mypackage.Pair;

public class TachyonManifold {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java TachyonManifold <filename>");
			return ;
		}

		FileReader	reader = new FileReader(args[0]);
		List< String >	lines = reader.getFileLines();
		long	deviationCount = countBeamDeviations(lines);

		System.out.println(deviationCount);
	}

	private static long	countBeamDeviations(List< String > lines) {
		long	count = 0;
		int	i = 0,
			n = lines.size(),
			width = lines.get(0).length();

		while (i < n - 1) {
			char	nextLine[] = lines.get(i + 1).toCharArray(),
				currentLine[] = lines.get(i).toCharArray();
			int	j = 0;

			while (j < width) {
				if (currentLine[j] == '|' || currentLine[j] == 'S') {
					if (nextLine[j] == '^') {
						nextLine[j] = 'o';
						count++;
					} else
						nextLine[j] = '|';
				} else if (currentLine[j] == 'o') {
					if (j > 0) {
						currentLine[j - 1] = '|';
						nextLine[j - 1] = '|';
					}
					if (j < width - 1) {
						currentLine[j + 1] = '|';
						nextLine[j + 1] = '|';
					}
				}

				String	newCurrent = new String(currentLine),
					newNext = new String(nextLine);
				lines.remove(i);
				lines.add(i, newCurrent);
				lines.remove(i + 1);
				lines.add(i + 1, newNext);

				j++;
			}
			for (String line : lines)
				System.out.println(line);
			System.out.println();
			i++;
		}
		return count;
	}
}