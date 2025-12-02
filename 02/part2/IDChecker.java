import java.lang.Object;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class IDChecker {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java IDChecker.java <filename>");
			return ;
		}

		List< Pair< Long, Long > >	ranges = parseInput(args[0]);
		for (Pair< Long, Long > range : ranges) {
			System.out.println(range.getFirst() + " " + range.getSecond());
		}

		List< Long >	invalidIDs = findInvalidIDs(ranges);
		Long	sumOfInvalidIDs = addInvalidIDs(invalidIDs);

		System.out.println("result = " + sumOfInvalidIDs);
	}

	private static List< Pair< Long, Long > >	parseInput(String fileName) {
		List< Pair< Long, Long > >	ranges = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File(fileName))) {
			while (scanner.hasNextLine()) {
				String	rangeStrings[] = scanner.nextLine().split(",");

				for (String rangeString : rangeStrings) {
					String	range[] = rangeString.split("-");
					Long	start = Long.parseLong(range[0]),
						end = Long.parseLong(range[1]);

					ranges.add(new Pair< Long, Long >(start, end));
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return ranges;
	}

	private static List< Long >	findInvalidIDs(List< Pair< Long, Long > > ranges) {
		List< Long >	invalidIDs = new ArrayList<>();

		for (Pair< Long, Long > range : ranges) {
			invalidIDs.addAll(findInvalidIDsInRange(range));
		}

		return invalidIDs;
	}

	private static List< Long >	findInvalidIDsInRange(Pair< Long, Long > range) {
		Long	nbr = range.getFirst(),
			last = range.getSecond();
		List< Long >	invalidIDs = new ArrayList<>();
		
		while (nbr <= last) {
			String str = nbr.toString();
			int	length = str.length(), div = 1, subLength = length / div;
			do {
				div++;
				subLength = length / div;
			} while (length % div != 0 && subLength > 0);

			while (div <= length) {
				List< String >	cutString = new ArrayList<>();
				int	start = 0, end = subLength;
				while (start < length && end <= length) {
					cutString.add(str.substring(start, end));
					start += subLength;
					end += subLength; 
				}
				if (sameElementsInList(cutString)) {
					invalidIDs.add(nbr);
					break ;
				}
				do {
					div++;
					subLength = length / div;
				} while (length % div != 0 && subLength > 0);
			}
			nbr++;
		}

		return invalidIDs;
	}

	private static long	addInvalidIDs(List< Long > invalidIDs) {
		Long	sum = new Long(0);

		for (Long invalidID : invalidIDs) {
			sum += invalidID;
		}

		return sum;
	}

	private static boolean	sameElementsInList(List< String > list) {
		int	i = 0, n = list.size();
		if (n <= 1)
			return false;
		while (i < n - 1 && (long) Long.parseLong(list.get(i)) == (long) Long.parseLong(list.get(i + 1))) {
			i++;
		}
		return (i == n - 1);
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