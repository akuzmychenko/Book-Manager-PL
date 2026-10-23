# Book Manager

Aplikacja konsolowa w języku Java do zarządzania osobistym katalogiem książek. Powstała jako projekt uniwersytecki z programowania. Umożliwia wyszukiwanie, sortowanie i edycję danych książek, a do ich przechowywania wykorzystuje pliki CSV.

## Funkcje

- Wczytywanie katalogu z pliku CSV wskazanego przez użytkownika.
- Dodawanie książek z tytułem, autorem i rokiem wydania.
- Wyświetlanie rekordów w sformatowanej tabeli.
- Wyszukiwanie po fragmencie tytułu lub nazwiska autora, bez rozróżniania wielkości liter.
- Sortowanie alfabetyczne według tytułu lub autora.
- Aktualizowanie danych książki lub usuwanie rekordów według tytułu.
- Zapisywanie katalogu do pliku `updated_books.csv` wyłącznie wtedy, gdy końcowe dane lub kolejność książek różnią się od stanu początkowego.
- Wyświetlanie czasu trwania sesji, liczby pomyślnych operacji dodawania książek oraz liczby wykonanych wyszukiwań przy zakończeniu programu.

Podczas dodawania lub aktualizowania książki aplikacja odrzuca puste tytuły, lata zapisane w niepoprawnym formacie, lata późniejsze niż bieżący rok oraz powtórzenia tej samej kombinacji tytułu, autora i roku wydania. Po błędzie wejścia/wyjścia podczas wczytywania pliku program pozwala ponownie podać jego ścieżkę.

## Technologie

**Standardowa biblioteka Javy:** kolekcje, strumienie, komparatory, wejście konsolowe oraz operacje na plikach. Projekt nie wymaga zewnętrznych zależności ani narzędzia do budowania.

## Struktura projektu

Główne pliki aplikacji znajdują się w katalogu `BookManager/`:

```text
BookManager/
├── src/
│   ├── Book.java
│   ├── FileManagement.java
│   ├── Main.java
│   └── MenuManagement.java
└── books.csv
```

| Plik | Odpowiedzialność |
| --- | --- |
| `Book.java` | Reprezentuje książkę: tytuł, autor i rok wydania. |
| `FileManagement.java` | Wczytuje i zapisuje pliki CSV. |
| `Main.java` | Uruchamia aplikację, obsługuje wybór opcji menu i zbiera statystyki sesji. |
| `MenuManagement.java` | Realizuje operacje na katalogu oraz walidację danych wprowadzanych przez użytkownika. |

## Uruchomienie

Zainstaluj Java Development Kit (JDK) w wersji 8 lub nowszej. Kod wykorzystuje funkcje języka Java 8, w tym wyrażenia lambda i strumienie.

### VS Code / GitHub Codespaces

W VS Code potrzebne jest rozszerzenie **Extension Pack for Java**.

1. Otwórz główny katalog repozytorium.
2. Przejdź do panelu **Run and Debug**.
3. Wybierz konfigurację **Uruchom Book Manager** i kliknij ▶.
4. Gdy program poprosi o nazwę pliku, wpisz `books.csv`.

Konfiguracja `.vscode/launch.json` ustawia katalog roboczy na `BookManager`, dzięki czemu program znajduje dołączony plik CSV.

### Terminal

W głównym katalogu repozytorium wykonaj:

```sh
cd BookManager
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

Jeżeli terminal znajduje się już w katalogu `BookManager`, pomiń pierwszą komendę.

Po wyświetleniu pierwszego komunikatu wpisz:

```text
books.csv
```

Możesz również podać ścieżkę do innego pliku CSV. Ścieżki względne są rozpatrywane względem katalogu roboczego programu. Interfejs i komunikaty aplikacji są w języku polskim.

## Obsługa

| Opcja | Działanie |
| --- | --- |
| 1 | Dodaj książkę |
| 2 | Wyświetl wszystkie książki |
| 3 | Wyszukaj według tytułu lub autora |
| 4 | Sortuj według tytułu |
| 5 | Sortuj według autora |
| 6 | Usuń książki według tytułu |
| 7 | Zaktualizuj książkę według tytułu |
| 8 | Zapisz i zakończ |

Na przykład wybierz **1** i podaj tytuł, autora oraz rok wydania. Wybierz **3**, aby wyszukać pasujące rekordy, a następnie **8**, aby zapisać zmiany, jeśli wystąpiły, i zakończyć sesję.

Zmiany są przechowywane w pamięci do momentu wybrania opcji **8**. Jeżeli końcowe dane lub kolejność książek różnią się od stanu początkowego, plik wynikowy `updated_books.csv` jest zapisywany w bieżącym katalogu roboczym. W przeciwnym razie program wyświetla komunikat o braku zmian i nie tworzy ani nie nadpisuje pliku wynikowego.

Aby kontynuować edycję zapisanego katalogu podczas kolejnej sesji, przy uruchomieniu podaj `updated_books.csv`.

## Format CSV

Plik wejściowy musi zawierać nagłówek, a następnie rekordy z trzema polami oddzielonymi przecinkami:

```csv
Title,Author,Year
The Hobbit,J. R. R. Tolkien,1937
Pride and Prejudice,Jane Austen,1813
```

Program pomija pierwszy wiersz i usuwa białe znaki otaczające każde pole. Rok musi być poprawną liczbą całkowitą.

## Obecne ograniczenia

- Odczyt CSV opiera się na prostym podziale według przecinków. Pola w cudzysłowach oraz przecinki wewnątrz tytułów lub nazwisk autorów nie są obsługiwane.
- Importowane rekordy nie przechodzą takiej samej walidacji jak dane wprowadzane interaktywnie. Wiersze z liczbą pól inną niż trzy są pomijane; niepoprawny format importowanego roku może zakończyć działanie aplikacji.
- Usuwanie obejmuje wszystkie rekordy z dokładnie pasującym tytułem, bez rozróżniania wielkości liter. Aktualizacja zmienia pierwszy pasujący rekord.
- Sortowanie korzysta z domyślnego porządku napisów w Javie i rozróżnia wielkość liter.
- Licznik dodawania uwzględnia pomyślne dodania podczas sesji, nawet jeżeli dodana książka zostanie później usunięta. Nie przedstawia różnicy między początkową a końcową liczbą książek.
- Jeśli wykryto zmiany, zapis nadpisuje plik `updated_books.csv`. Błędy zapisu są zgłaszane, ale aplikacja mimo to kończy działanie.

## Rozwijane umiejętności

Projekt przedstawia modelowanie z użyciem klas, operacje na kolekcjach, wyszukiwanie i sortowanie, trwały zapis danych w plikach, obsługę wyjątków oraz podział odpowiedzialności między klasy.

## Zespół

## Zespół i dalszy rozwój

Pierwotna wersja projektu powstała w ramach zajęć uniwersyteckich w trzyosobowym zespole.

Następnie samodzielnie rozwinąłem projekt: dodałem wykrywanie zmian przed zapisem, poprawiłem zliczanie dodanych książek oraz uzupełniłem konfigurację uruchamiania i dokumentację.