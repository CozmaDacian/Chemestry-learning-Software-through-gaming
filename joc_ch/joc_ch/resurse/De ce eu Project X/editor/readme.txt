-----------------------------------------------------------------------------------------------
SPECIFICATII GENERALE

aplicatia (ChemEditorApp.exe) nu poate fi pornita fara 2 argumente ce semnifica:

1. path-ul catre fisierul de intrebari
2. path-ul catre fisierul de descrieri

pornirea aplicatiei fara aceste 2 path-uri va da un mesaj general de eroare.
aceste 2 argumente sunt separate de un singur spatiu. astfel, formatul pentru argumentul aplicatiei este:

"<path complet intrebari> <path complet descrieri>"

-----------------------------------------------------------------------------------------------
OBSERVATIE

orice caracter spatiu (' ') dintr-un path TREBUIE inlocuit de caracterul '|'.

-----------------------------------------------------------------------------------------------
EXEMPLU

am atasat un shortcut al aplicatiei care porneste aplicatia cu fisierele:

"C:\De ce eu Project X\intrebari.txt"
"C:\De ce eu Project X\descrieri.txt"

astfel, arguemntul aplicatiei devine: "C:\De|ce|eu|Project|X\intrebari.txt C:\De|ce|eu|Project|X\descrieri.txt"
pentru alte specificatii, vezi proprietatile shortcut-ului.

-----------------------------------------------------------------------------------------------
ALTE SPECIFICATII

daca se observa modificari neasteptate ale fisierului de intrebari, am pastrat un back-up al fisierului
original de intrebari.



