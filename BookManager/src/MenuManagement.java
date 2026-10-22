import java.io.*;
import java.util.*;

public class MenuManagement {
    public static void displayMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Dodaj książkę");
        System.out.println("2. Wyświetl wszystkie");
        System.out.println("3. Wyszukaj (tytuł lub autor)");
        System.out.println("4. Sortuj po tytule");
        System.out.println("5. Sortuj po autorze");
        System.out.println("6. Usuń książkę");
        System.out.println("7. Aktualizuj książkę");
        System.out.println("8. Zapisz i zakończ");
        System.out.print("Wybierz opcję: ");
    }
    //1
     static void addBook(List<Book> books, Scanner scanner) {
         /** 1. Dodaj książkę
          Użytkownik podaje tytuł, autora i rok.
          Dane są dodawane do listy książek.
          OBSŁUGINAWE WYJĄTKI:
          -Tytuł dodawanej książki nie może być pusty.
          -Rok nie może być większy niż aktualny rok.
          -Dodawanie takiej samej książki nie jest dozwolone.*/
        try {
            System.out.print("Tytuł: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                throw new IllegalArgumentException("Tytuł nie może być pusty.");
            }
            System.out.print("Autor: ");
            String author = scanner.nextLine().trim();
            System.out.print("Rok: ");
            int year = Integer.parseInt(scanner.nextLine());
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (year > currentYear) {
                throw new IllegalArgumentException("Rok nie może być większy niż " + currentYear + ".");
            }
            boolean exists = books.stream().anyMatch(b ->
                    b.title.equalsIgnoreCase(title) &&
                            b.author.equalsIgnoreCase(author) &&
                            b.year == year
            );
            if (exists) {
                throw new IllegalArgumentException("Taka książka już istnieje.");
            }

            books.add(new Book(title, author, year));
            System.out.println("Pomyślnie dodano książkę!");
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy format roku. Spróbuj ponownie.");
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }
    //2
     static void printBooks(List<Book> books) {
        /** 2. Wyświetl wszystkie książki
         Wszystkie książki wypisane w formie tabeli.*/
        System.out.printf("%-30s %-20s %4s%n", "Tytuł", "Autor", "Rok");
        for (Book b : books) {
            System.out.printf("%-30s %-20s %4d%n", b.title, b.author, b.year);
        }
    }
    //3
     static void searchBooks(List<Book> books, Scanner scanner) {
        /** 3. Wyszukaj książkę
         - Wyszukiwanie po tytule lub autorze (niezależnie od wielkości liter).
         - Jeśli brak wyników, wyświetlane jest: "Nie znaleziono takiej książki."*/
        System.out.print("Szukana fraza (tytuł/autor): ");
        String search = scanner.nextLine().toLowerCase();
        int count = 0;
        for (Book b : books) {
            if (b.title.toLowerCase().contains(search) || b.author.toLowerCase().contains(search)) {
                System.out.println(b);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Nie znaleziono takiej książki");
        }
    }
    //4
     static void sortByTitle(List<Book> books) {
        /** 4. Sortuj po tytule
         - Lista sortowana alfabetycznie po tytule.*/
        books.sort(Comparator.comparing(b -> b.title));
        System.out.println("Posortowano po tytule.");
    }
    //5
     static void sortByAuthor(List<Book> books) {
        /** 5. Sortuj po autorze
         - Lista sortowana alfabetycznie po autorze.*/
        books.sort(Comparator.comparing(b -> b.author));
        System.out.println("Posortowano po autorze.");
    }
    //6
     static void removeBook(List<Book> books, Scanner scanner) {
        /** 6. Usuń książkę
         - Usuwanie książki po tytule (usuwa wszystkie pasujące tytuły).*/
        System.out.print("Tytuł książki do usunięcia: ");
        String toRemove = scanner.nextLine().toLowerCase();
        books.removeIf(b -> b.title.toLowerCase().equals(toRemove));
        System.out.println("Usunięto pasujące książki.");
    }
    //7
     static void updateBook(List<Book> books, Scanner scanner) {
        /** 7. Aktualizuj książkę
         - Użytkownik podaje tytuł istniejącej książki.
         - Program pozwala zmienić tytuł, autora i rok.
         OBSŁUGINAWE WYJĄTKI:
         -Tytuł dodawanej książki nie może być pusty.
         -Rok nie może być większy niż aktualny rok.
         -Dodawanie takiej samej książki nie jest dozwolone.*/
        System.out.print("Tytuł książki do aktualizacji: ");
        String toUpdate = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Book b : books) {
            if (b.title.toLowerCase().equals(toUpdate)) {
                found = true;
                try {
                    System.out.print("Nowy tytuł: ");
                    String newTitle = scanner.nextLine().trim();
                    if (newTitle.isEmpty()) {
                        throw new IllegalArgumentException("Tytuł nie może być pusty.");
                    }

                    System.out.print("Nowy autor: ");
                    String newAuthor = scanner.nextLine().trim();

                    System.out.print("Nowy rok: ");
                    int newYear = Integer.parseInt(scanner.nextLine());
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    if (newYear > currentYear) {
                        throw new IllegalArgumentException("Rok nie może być większy niż " + currentYear + ".");
                    }

                    boolean duplicate = books.stream().anyMatch(book ->
                            book != b &&
                                    book.title.equalsIgnoreCase(newTitle) &&
                                    book.author.equalsIgnoreCase(newAuthor) &&
                                    book.year == newYear
                    );
                    if (duplicate) {
                        throw new IllegalArgumentException("Taka książka już istnieje.");
                    }

                    b.title = newTitle;
                    b.author = newAuthor;
                    b.year = newYear;
                    System.out.println("Zaktualizowano.");
                } catch (NumberFormatException e) {
                    System.out.println("Nieprawidłowy format roku.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Błąd: " + e.getMessage());
                }
                break;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono książki do aktualizacji.");
        }
    }
    //8
     static void saveBooks(String filename, List<Book> books) {
        /**8. Zapisz książki
         - Program zapisuje dane do pliku updated_books.csv.*/
        FileManagement.saveBooks(filename, books);
    }


}
