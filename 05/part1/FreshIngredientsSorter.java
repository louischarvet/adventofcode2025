import java.lang.Object;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class FreshIngredientsSorter {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java FreshIngredientsSorter <filename>");
			return ;
		}

		List< String >	rawLines = getLinesFromFile(args[0]);
		List< Pair< Long, Long > >	ranges = parseRanges(rawLines);
		// for (Pair< Long, Long > e : ranges) {
		// 	System.out.println(e.getFirst() + " " + e.getSecond());
		// }
		List< Long >	ids = parseIDs(rawLines);
		long	freshCount = countInRanges(ranges, ids);

		System.out.println("freshCount = " + freshCount);
	}

	private static List< String >	getLinesFromFile(String fileName) {
		List< String >	banks = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File(fileName))) {
			while (scanner.hasNextLine())
				banks.add(scanner.nextLine());
		} catch (Exception e) {
			System.err.println(e);
		}

		return banks;
	}

	private static List< Pair< Long, Long > >	parseRanges(List< String > rawLines) {
		List< Pair< Long, Long > >	ranges = new ArrayList<>();

		for (String line : rawLines) {
			String	split[] = line.split("-");
			if (split[0].isEmpty() || split[1].isEmpty())
				break ;
			Pair< Long, Long >	range = new Pair<>(Long.parseLong(split[0]), Long.parseLong(split[1]));
			ranges.add(range);
		}

		return ranges;
	}

	private static List< Long >	parseIDs(List< String > rawLines) {
		List< Long >	ids = new ArrayList<>();
		int	i = 0, size = rawLines.size();

		while (!rawLines.get(i).isEmpty())
			i++;
		while (++i < size) {
			ids.add(Long.parseLong(rawLines.get(i)));
		}

		return ids;
	}

	private static long	countInRanges(List< Pair< Long, Long > > ranges, List< Long > ids) {
		long	count = 0;

		for (Long id : ids) {
			for (Pair< Long, Long > range : ranges) {
				if (isInRange(range, id)) {
					count++;
					break ;
				}
			}
		}

		return count;
	}

	private static boolean	isInRange(Pair< Long, Long > range, Long id) {
		return id >= range.getFirst() && id <= range.getSecond();
	}

	static class Pair< F, S > {
		private final F	first;
		private final S	second;

		public Pair(F first, S second) {
			this.first = first;
			this.second = second;
		}

		public F	getFirst() { return first; }
		public S	getSecond() { return second; }
	}
}