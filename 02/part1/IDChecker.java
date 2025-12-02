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
		Long	i = range.getFirst(),
			end = range.getSecond();
		List< Long >	invalidIDs = new ArrayList<>();
		
		while (i <= end) {
			int power = findPower(i, 10);
			if (power % 2 == 1) {
				int	halfPower = power / 2 + 1;
				int tenPower = (int) Math.pow(10, halfPower);
				if (i / tenPower == i % tenPower) {
					invalidIDs.add(i);
				}
			}
			i++;
		}

		return invalidIDs;
	}

	private static int	findPower(Long number, int base) {
		int power = 0;

		while (number >= base) {
			number /= base;
			power++;
		}
		return power;
	}

	private static long	addInvalidIDs(List< Long > invalidIDs) {
		Long	sum = new Long(0);

		for (Long invalidID : invalidIDs) {
			sum += invalidID;
		}

		return sum;
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