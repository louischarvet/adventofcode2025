import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.lang.Integer;

public class findPassword {
    private static List< String > readFile(String fileName) {
        File file = new File(fileName);
        List< String > lines = new ArrayList<>();

        try (Scanner scanner= new Scanner(file)) {
            while (scanner.hasNextLine())
                lines.add(scanner.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return lines;
    }

    private static int calculate(List< String > lines) {
        int pos = 50,
            count = 0;

        for (String line : lines) {
            try {
                int clicks = Integer.parseInt(line.substring(1));
                int prvPos = pos;
                if (line.charAt(0) == 'L') {
                    pos -= clicks % 100;
                    if (pos <= 0) {
                        if (prvPos != 0)
                            count += 1;
                        if (pos < 0)
                            pos += 100;
                    }
                } else if (line.charAt(0) == 'R') {
                    pos += clicks % 100;
                    if (pos > 99) {
                        if (prvPos != 0)
                            count += 1;
                        pos -= 100;
                    }
                }
                count += clicks / 100;
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return count;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java findPassword <fileName>");
            return ;
        }

        List< String > lines = readFile(args[0]);
        int password = calculate(lines);

        System.out.print(password);
    }
}