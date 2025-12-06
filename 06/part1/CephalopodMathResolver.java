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
		List< Stack< String > >	operationsList = parseLines(lines);
		List< Long >	resultsList = resolveOperations(operationsList);
		long	total = listSum(resultsList);

		System.out.println(total);
	}

	private static List< Stack< String > >	parseLines(List< String > lines) {
		List< Stack< String > >	operationsList = new ArrayList<>();
		List< List< String > >	splittedLines = new ArrayList<>();

		for (String line : lines) {
			List< String >	splittedLineList = new ArrayList<>();
			String	splittedLineArray[] = line.split(" ");

			for (String element : splittedLineArray) {
				if (element.length() > 0)
					splittedLineList.add(element);
			}

			splittedLines.add(splittedLineList);
		}

		int	i = 0,
			operationsNumber = splittedLines.get(0).size();
		
		while (i < operationsNumber) {
			Stack< String >	operation = new Stack<>();
			int	j = 0,
			linesNumber = splittedLines.size();

			while (j < linesNumber) {
				operation.add(splittedLines.get(j).get(i));
				j++;
			}

			operationsList.add(operation);
			i++;
		}
		
		return operationsList;
	}

	private static List< Long >	resolveOperations(List< Stack< String > > operationsList) {
		List< Long >	results = new ArrayList<>();

		for (Stack< String > operation : operationsList) {
			String	operator = operation.pop();
			Long	result;
			if (operator.charAt(0) == '+')
				result = (long) 0;
			else
				result = (long) 1;

			while (!operation.empty()) {
				if (operator.charAt(0) == '+')
					result += Long.parseLong(operation.pop());
				else
					result *= Long.parseLong(operation.pop());
			}

			results.add(result);
		}

		return results;
	}

	private static long listSum(List< Long > list) {
		long	total = 0;

		for (Long element : list)
			total += element.longValue();
		
		return total;
	}
}