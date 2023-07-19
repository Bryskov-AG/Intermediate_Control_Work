
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<Toy> toys = new ArrayList<>();
    private static List<Toy> prizeToys = new ArrayList<>();

    public static void main(String[] args) {
        // Инициализация игрушек
        toys.add(new Toy(1, "Cat", 20, 30));
        toys.add(new Toy(2, "Dog", 20, 30));
        toys.add(new Toy(3, "Fox", 20, 30));
        toys.add(new Toy(4, "Bear", 20, 30));
        toys.add(new Toy(5, "Panda", 20, 30));

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    startLottery();
                    break;
                case 2:
                    addNewToy(scanner);
                    break;
                case 3:
                    updateFrequency(scanner);
                    break;
                case 4:
                    getPrizeToy();
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Некорректный выбор.");
                    break;
            }
        } while (choice != 0);
    }
    // Меню
    private static void printMenu() {
        System.out.println("==========Меню:==========");
        System.out.println("0. Выйти из меню");
        System.out.println("1. Запустить розыгрыш");
        System.out.println("2. Добавить новую игрушку");
        System.out.println("3. Изменить частоту (вес выпадения игрушки)");
        System.out.println("4. Получить призовую игрушку");
        System.out.print("Выберите действие: ");
    }

    // Розыгрыш игрушек
    private static void startLottery() {
        System.out.println("Розыгрыш игрушек:");
        if (toys.isEmpty()) {
            System.out.println("На складе нет игрушек.");
        } else {
            List<Integer> lotteryList = new ArrayList<>();
            for (Toy toy : toys) {
                for (int i = 0; i < toy.getFrequency(); i++) {
                    lotteryList.add(toy.getId());
                }
            }

            Random random = new Random();
            int randomIndex = random.nextInt(lotteryList.size());
            int winningToyId = lotteryList.get(randomIndex);

            Toy winningToy = null;
            for (Toy toy : toys) {
                if (toy.getId() == winningToyId) {
                    winningToy = toy;
                    break;
                }
            }

            if (winningToy != null) {
                if (winningToy.getQuantity() > 0) {
                    System.out.println("Вы выиграли игрушку: " + winningToy.getName());
                    addToPrizeToys(winningToy);
                    winningToy.setQuantity(winningToy.getQuantity() - 1);
                } else {
                    System.out.println("Извините, данной игрушки нет в наличии.");
                }
            } else {
                System.out.println("Ошибка: не удалось определить выигрышную игрушку.");
            }
        }
    }

    // Добавляем выигранную игрушку в список призовых игрушек.
    private static void addToPrizeToys(Toy toy) {
        prizeToys.add(toy);
    }

    // Выдача призовой игрушки
    private static void getPrizeToy() {
        if (prizeToys.isEmpty()) {
            System.out.println("Нет призовых игрушек для выдачи.");
        } else {
            Toy prizeToy = prizeToys.get(0);
            prizeToys.remove(0);
    
            try {
                FileWriter writer = new FileWriter("prize.txt", true);
                writer.write("Выдана призовая игрушка: " + prizeToy.getName() + "\n");
                writer.close();
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл.");
            }
    
            System.out.println("Призовая игрушка выдана: " + prizeToy.getName());
            prizeToy.setQuantity(prizeToy.getQuantity() - 1);
        }
    }
    
    // Добавляем новую игрушку
    private static void addNewToy(Scanner scanner) {
        System.out.print("Введите id новой игрушки: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введите название новой игрушки: ");
        String name = scanner.nextLine();

        System.out.print("Введите частоту новой игрушки (вес выпадения в %): ");
        int frequency = scanner.nextInt();
        scanner.nextLine();

        Toy newToy = new Toy(id, name, frequency, 30);
        toys.add(newToy);

        System.out.println("Новая игрушка добавлена на склад.");
    }
    // Изменяем частоту
    private static void updateFrequency(Scanner scanner) {
        System.out.print("Введите id игрушки, частоту которой нужно изменить: ");
        int toyId = scanner.nextInt();
        scanner.nextLine();

        Toy toyToUpdate = null;
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toyToUpdate = toy;
                break;
            }
        }

        if (toyToUpdate != null) {
            System.out.print("Введите новую частоту игрушки (вес выпадения в %): ");
            int newFrequency = scanner.nextInt();
            scanner.nextLine();

            toyToUpdate.setFrequency(newFrequency);
            System.out.println("Частота игрушки с id " + toyToUpdate.getId() + " изменена.");
        } else {
            System.out.println("Ошибка: игрушка с указанным id не найдена.");
        }
    }
}
