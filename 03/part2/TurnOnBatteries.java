import java.lang.Object;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class TurnOnBatteries {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java TurnOnBatteries <filename>");
			return ;
		}
		List< String >	banks = getLinesFromFile(args[0]);
		List< Long >	batteries = findHighestJoltage(banks);
		long	totalJoltage = listSum(batteries);

		System.out.println("batteries: " + batteries);
		System.out.println("total joltage = " + totalJoltage);
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

	private static List< Long >	findHighestJoltage(List< String > banks) {
		List< Long >	batteries = new ArrayList<>();

		for (String bank : banks) {
			List< Long >	tmpBatteryList = new ArrayList<>();

			batteries.add(findHighestInBank(bank));
		}

		return batteries;
	}

	private static Long	findHighestInBank(String bank) {
		List< Long >	battery = new ArrayList<>();
		char	highestChar = bank.charAt(0),
			currentChar = highestChar;
		int	i = 0,
			n = bank.length(),
			size = 12,
			highestIndex = 0;
		List< Integer >	highestIndexes = new ArrayList<>();

		// get highestChar and highestIndex
		while (i <= n - size) {
			currentChar = bank.charAt(i);
			if (currentChar > highestChar) {
				highestChar = currentChar;
				highestIndex = i;
			}
			i++;
		}
		highestIndexes.add(highestIndex);

		// check if there is other highestIndexes
		i = highestIndex + 1;
		while (i <= n - size) {
			currentChar = bank.charAt(i);
			if (currentChar == highestChar) {
				highestIndexes.add(i);
			}
			i++;
		}

		// get highest Longs for each highestIndex
		for (Integer hi : highestIndexes) {
			i = hi.intValue();
			size = 12;
			String	batteryString = "" + bank.charAt(i);

			while (i <= n - size) {
				currentChar = bank.charAt(i);
				i++;
			}
		}

		// use rcursivity

		return highest;
	}

	private static long listSum(List< Long > list) {
		long	total = 0;

		for (Long element : list)
			total += element.longValue();
		
		return total;
	}
}