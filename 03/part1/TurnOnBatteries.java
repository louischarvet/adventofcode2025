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
		List< Integer >	batteries = findHighestJoltage(banks);
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

	private static List< Integer >	findHighestJoltage(List< String > banks) {
		List< Integer >	batteries = new ArrayList<>();

		for (String bank : banks) {
			batteries.add(findHighestInBank(bank));
		}

		return batteries;
	}

	private static Integer	findHighestInBank(String bank) {
		Integer	battery;
		char	highestChar = bank.charAt(0),
			currentChar = highestChar;
		int	i = 1,
			n = bank.length(),
			highestIndex = 0;

		while (i < n - 1) {
			currentChar = bank.charAt(i);
			if (currentChar > highestChar) {
				highestChar = currentChar;
				highestIndex = i;
			}
			i++;
		}

		battery = (highestChar - 48) * 10;
		i = highestIndex + 1;
		highestChar = bank.charAt(i);
		while (i < n) {
			currentChar = bank.charAt(i);
			if (currentChar > highestChar)
				highestChar = currentChar;
			i++;
		}

		battery += highestChar - 48;

		return battery;
	}

	private static long listSum(List< Integer > list) {
		long	total = 0;

		for (Integer element : list)
			total += element.longValue();
		
		return total;
	}
}