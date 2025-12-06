import java.lang.Object;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import mypackage.FileReader;

public class CephalopodMathResolver {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java CephalopodMathResolver <filename>");
			return ;
		}

		FileReader reader = new FileReader(args[0]);
		List< String >	lines = reader.getFileLines();
		List< Long >	resultsList = resolveOperations(lines);
		long	total = listSum(resultsList);

		System.out.println(total);
	}

	private static List< Long >	resolveOperations(List< String > operationsList) {
		List< Long >	resultsList = new ArrayList<>();
		List< Long >	numbersList = new ArrayList<>();
		int	i = operationsList.get(0).length() - 1,
			size = operationsList.size();

		while (i >= 0) {
			String	numberString = "";
			char	operator = 0;

			for (String operation : operationsList) {
				char	c = operation.charAt(i);

				switch (c) {
					case ' ':
						continue ;
					case '+':
						operator = c;
						break ;
					case '*':
						operator = c;
						break ;
					default:
						numberString += c;
				}
			}

			if (!numberString.isEmpty()) {
				numbersList.add(Long.parseLong(numberString));
				if (operator != 0) {
					resultsList.add(calculate(numbersList, operator));
					numbersList.clear();
					operator = 0;
				}
			}

			i--;
		}

		return resultsList;
	}

	private static long	calculate(List< Long > numbersList, char operator) {
		long	result;
		if (operator == '+') {
			result = 0;
			for (Long number : numbersList)
				result += number;
		}
		else {
			result = 1;
			for (Long number : numbersList)
				result *= number;
		}

		return result;
	}

	private static long listSum(List< Long > list) {
		long	total = 0;

		for (Long element : list)
			total += element.longValue();
		
		return total;
	}
}