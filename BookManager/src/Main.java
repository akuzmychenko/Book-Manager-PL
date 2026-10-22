import java.io.*;
import java.util.*;

/**PROJEKT: PROSTY SYSTEM ZARZĄDZANIA KSIĄŻKAMI*/

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);
        List<Book> books = new ArrayList<>();
        boolean fileLoaded = false;

        /**Wczytanie danych z pliku CSV*/
        while (!fileLoaded) {
            System.out.print("Podaj nazwę lub ścieżkę do pliku CSV(standardowo 'books.csv'): ");
            String fileName = scanner.nextLine();
            try {
                books = FileManagement.loadBooks(fileName);
                fileLoaded = true;
            } catch (IOException e) {
                System.out.println("Nie udało się wczytać pliku. Spróbuj ponownie.");
            }
        }

        boolean running = true;
        int addCount = 0, searchCount = 0;
        while (running) {
            MenuManagement.displayMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    MenuManagement.addBook(books, scanner);
                    addCount++;
                    break;

                case "2":
                    MenuManagement.printBooks(books);
                    break;

                case "3":
                    MenuManagement.searchBooks(books, scanner);
                    searchCount++;
                    break;

                case "4":
                    MenuManagement.sortByTitle(books);
                    break;

                case "5":
                    MenuManagement.sortByAuthor(books);
                    break;

                case "6":
                    MenuManagement.removeBook(books, scanner);
                    break;

                case "7":
                    MenuManagement.updateBook(books, scanner);
                    break;

                case "8":
                    MenuManagement.saveBooks("updated_books.csv", books);
                    running = false;
                    break;

                default:
                    System.out.println("Nieprawidłowa opcja.");
                    break;
            }
        }

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        System.out.printf("Czas działania programu: %.2f sekund%n", duration);
        System.out.println("Dodano książek: " + addCount + ", Wyszukiwań: " + searchCount);
    }
}
/**DODATKOWO:
 - Obsługa błędów wejściowych (np. wpisanie tekstu zamiast liczby).
 - Program działa w pętli do momentu wybrania opcji zakończenia.
 - Dane wczytywane są z pliku CSV wskazanego przez użytkownika.*/