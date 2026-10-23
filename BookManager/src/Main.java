import java.io.*;
import java.util.*;

/**PROJEKT: PROSTY SYSTEM ZARZĄDZANIA KSIĄŻKAMI*/

public class Main {
    // Program porównuje dane książek i kolejność rekordów z katalogiem początkowym.
    // Zmiany, które zostały cofnięte, nie wymagają zapisu.
    private static boolean hasChanges(List<Book> originalBooks, List<Book> books) {
    if (originalBooks.size() != books.size()) {
        return true;
    }

    for (int i = 0; i < books.size(); i++) {
        Book original = originalBooks.get(i);
        Book current = books.get(i);

        if (!Objects.equals(original.title, current.title)
                || !Objects.equals(original.author, current.author)
                || original.year != current.year) {
            return true;
        }
    }

    return false;
}
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
        
        // Program zachowuje niezależną kopię wczytanych książek.
        // Edycja bieżącego katalogu nie może zmieniać kopii początkowej.

        List<Book> originalBooks = new ArrayList<>();

        for (Book book : books) {
            originalBooks.add(new Book(book.title, book.author, book.year));
        }

        boolean running = true;
        int addCount = 0, searchCount = 0;
        while (running) {
            MenuManagement.displayMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    // Zapamiętaj liczbę książek przed próbą dodania.
                    int countBefore = books.size();

                    MenuManagement.addBook(books, scanner);

                    // Zwiększ licznik tylko po pomyślnym dodaniu książki.
                    if (books.size() > countBefore) {
                        addCount++;
                    }

                    break;
                }

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
                    // Zapisz tylko wtedy, gdy katalog końcowy różni się od początkowego.
                    // W przeciwnym razie pozostaw istniejący plik wynikowy bez zmian.
                    if (hasChanges(originalBooks, books)) {
                        MenuManagement.saveBooks("updated_books.csv", books);
                    } else {
                        System.out.println(
                            "Nie wykryto zmian. Plik updated_books.csv nie został utworzony ani nadpisany."
                        );
                    }

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