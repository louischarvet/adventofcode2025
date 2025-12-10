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

public class ButtonPresser {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java ButtonPresser <filename>");
			return ;
		}

		FileReader	reader = new FileReader(args[0]);
		List< String >	lines = reader.getFileLines();
		List< ElfMachine >	machines = parseLines(lines);

		for (ElfMachine elem : machines) {
			int	minimalPushes = elem.tryPushes();
			elem.print();
			System.out.println();
		}
	}

	private static List< ElfMachine >	parseLines(List< String > lines) {
		List< ElfMachine >	machines = new ArrayList<>();

		for (String line : lines) {
			String	splitLine[] = line.split(" ");
			int	size = 0;
			for (String s : splitLine)
				size++;
			boolean	finalState[] = parseFinalState(splitLine[0]);
			List< int[] >	buttons = parseButtons(splitLine, size - 1);
			machines.add(new ElfMachine(finalState, buttons));
		}

		return machines;
	}

	private static boolean[]	parseFinalState(String str) {
		int	size = str.length() - 2,
			i = 0;
		boolean	finalState[] = new boolean[size];

		while (i < size) {
			if (str.charAt(i + 1) == '#')
				finalState[i] = true;
			else if (str.charAt(i + 1) == '.')
				finalState[i] = false;
			i++;
		}

		return finalState;
	}

	private static List< int[] >	parseButtons(String splitLine[], int buttonNbr) {
		List< int[] >	buttons = new ArrayList<>();
		int i = 1;

		while (i < buttonNbr) {
			String	splitButton[] = splitLine[i].substring(1, splitLine[i].length() - 1)
				.split(",");
			int size = 0;
			for (String s : splitButton)
				size++;
			int	button[] = new int[size],
				j = 0;
			
			while (j < size) {
				button[j] = Integer.parseInt(splitButton[j]);
				j++;
			}

			i++;
			buttons.add(button);
		}

		return buttons;
	}

	static class	ElfMachine {
		private final boolean	finalState[]; // List
		private final List< int[] >	buttons;
		private int	minimalPushes;

		public ElfMachine(boolean finalState[], List< int[] > buttons) {
			this.finalState = finalState;
			this.buttons = buttons;
			this.minimalPushes = 0;
		}

		public boolean[]	getFinalState() { return finalState; }
		public List< int[] >	getButtons() { return buttons; }

		public void	setMinimalPushes(int n) {
			this.minimalPushes = n;
		}

		public int	tryPushes() {
			int	min = 0;

			do {
				List< boolean >	state = new ArrayList<>();
				while (state.size() != this.finalState.size()) {
					state.add(false);
				}

			} while ();
		}

		public void	print() {
			for (boolean b : finalState) {
				System.out.print(b + " ");
			}
			System.out.println();
			for (int[] elem : buttons) {
				for (int nbr : elem) {
					System.out.print(nbr + " ");
				}
				System.out.println();
			}
			System.out.println("minimalPushes = " + minimalPushes);
		}
	}
}