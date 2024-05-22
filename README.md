System zarządzania zadaniami w zespole.

Do uruchomienia programu potrzebne jest pobranie pakietów JavaFX, Maven oraz ustawienia zmiennej środowiskowej dla Maven. 
Aplikację uruchamiamy poprzez przycisk 'Run Java' dla pliku App.java

Z uwagi na sposób przekazania pracy wybrałem zapis danych do pliku 'taks.json' oraz 'team_members.json' zamiast bazy danych. Każda wprowadzona zmiana powoduje aktualizację i zapis danych do pliku.

Po otwarciu okna aplikacji dostępne jest 5 zakładek w bocznym menu nawigacyjnym:
- Zadania: W tej zakładce widoczne są wszystkie utworzone i przypisane zadania ze statusem 'Nowe' oraz 'W trakcie'. U góry dostępne są dwa przyciski zmiany statusu zadania. Zmiana statusu z 'W trakcie' na 'Zrealizowane' spowoduje dodanie daty zakończenia zadania oraz jego usunięcie z tej listy i przeniesienie jej do zakładki 'Zrealizowane'. Nie można zmienić statusu od razu z 'Nowe' na 'Zrealizowane' z pominięciem statusu 'W trakcie'
- Zrealizowane: Ta zakładka zawiera listę wszystkich zadań jako zrealizowane w poprzedniej zakładce.
- Zarządzanie zadaniami: Zakładka umożliwia tworzenie, edycję oraz usuwanie zadań. Podczas dodawania zadania należy wypełnić każde pole włącznie z przypisaniem pracownika. Edycja zadania działa na zasadzie usunięcia zadania przeniesienia danych do pól edycji oraz ponownego dodania (Według mnie prostsze rozwiązanie z uwagi na zapis danych do pliku JSON. W przypadku użycia bazy danych można by robić update pól).
- Zarządzanie zespołem: Zakładka umożliwia dodawanie oraz usuwanie członków zespołu. Podczas dodawania członka zespołu wszystkie pola muszą być uzupełnione.
- Statystyki: Pierwsze uruchomienie zakładki spowoduje wyświetlenie listy wszystkich utworzonych zadań bez podziału na pracownika. Dostępny jest również wykres kołowy ilustrujący stosunek zadań według statusu 'Nowe', 'W trakcie' oraz 'Zrealizowane'. Po wskazaniu pracownika w oknie wyboru nastąpi wyfiltrowanie zadań przypisanych do danego pracownika.