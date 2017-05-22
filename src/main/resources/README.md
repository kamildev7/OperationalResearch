BO project - Bees Algorithm used to deploy goods in warehouses depending on stores' demands.
Team members: Jakub Janusz, Krzysztof Podsiadło, Adam Szady, Dawid Tryliński, Krzysztof Węgrzyński

Założenia
---------
1. Magazyny mają skończoną pojemność.
2. Zapotrzebowania są skończone.
3. Sklep może mieć wiele zapotrzebowań.
4. Każde zapotrzebowanie musi być realizowane przez jeden magazyn.
5. Różne zapotrzebowania tego samego sklepu mogą być realizowane przez różne magazyny.
6. Magazyn nie może realizować więcej zapotrzebowań niż jego pojemność.
7. Każde zapotrzebowanie musi być spełnione. *
8. Suma pojemności magazynów jest większa od sumy pojemności sklepów.
9. Rozwiązanie może nie istnieć.

* Zaytać o karę za niespełnienie i jak ma wyglądać funkcjakosztu i jej modyfikacja.

Wejście
-------
M - ilość magazynów
S - ilość sklepów
M_size[M] - pojemności magazynów
M_place[M] - położenia magazynów
S_place[S] - położenia sklepów
S_order[S][x][y] - zapotrzebowania magazynów; x - towar, y - ilość

Wyjście (rozwiązanie)
---------------------
M_content[] // Tu mam puste miejsce, pamięta ktoś?

Funkcja kosztu
--------------
Suma po i należących do S_order[] z (i * metryka * 2), gdzie metryka to d(M_place, S_place), a 2 było chyba tak o, bo ktoś powiedział, że "razy dwa"