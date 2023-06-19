import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Калькулятор приветствует тебя!");

        while (true) {
            System.out.println("Введите выражение или 'exit' для выхода:");
            String input = scanner.nextLine(); //всё что ввел пользователь будет сохранено в переменную инпат

            if (input.equals("exit")) {
                break;
            }

            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("Калькулятор прощается с тобой!");
        scanner.close();
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("throws Exception"); //т.к. строка не является математической операцией
        }

        String operand1 = tokens[0];
        char operator = tokens[1].charAt(0);
        String operand2 = tokens[2];

        boolean isRomanNumerals = isRomanNumerals(operand1) && isRomanNumerals(operand2);

        int num1 = parseNumber(operand1, isRomanNumerals);
        int num2 = parseNumber(operand2, isRomanNumerals);

        int result;
        switch (operator) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль недопустимо.");
                }
                result = num1 / num2;
            }
            default -> throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
        }

        if (isRomanNumerals) {
            if (result <= 0) {
                throw new IllegalArgumentException("throws Exception."); //т.к. в римской системе нет отрицательных чисел
            }
            return toRomanNumerals(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRomanNumerals(String input) {
        return input.matches("^[IVXLCMD]+$");
    }

    private static int parseNumber(String input, boolean isRomanNumerals) {
        if (isRomanNumerals) {
            return fromRomanNumerals(input);
        } else {
            return Integer.parseInt(input);
        }
    }

    private static String toRomanNumerals(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("throws Exception");
        }

        StringBuilder sb = new StringBuilder();

        Map<Character, Integer> romanNumerals = createRomanNumeralsMap();
        for (Map.Entry<Character, Integer> entry : romanNumerals.entrySet()) {
            char romanNumeral = entry.getKey();
            int value = entry.getValue();

            while (number >= value) {
                sb.append(romanNumeral);
                number -= value;
            }
        }

        return sb.toString();
    }

    private static int fromRomanNumerals(String input) {
        int number = 0;

        Map<Character, Integer> romanNumerals = createRomanNumeralsMap();
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            int currentValue = romanNumerals.get(current);

            if (i < input.length() - 1) {
                char next = input.charAt(i + 1);
                int nextValue = romanNumerals.get(next);

                if (nextValue > currentValue) {
                    number -= currentValue;
                } else {
                    number += currentValue;
                }
            } else {
                number += currentValue;
            }
        }

        return number;
    }

    private static Map<Character, Integer> createRomanNumeralsMap() {
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);
        romanNumerals.put('D', 500);
        romanNumerals.put('M', 1000);
        return romanNumerals;
    }
}
