// lab4.cpp: Okreœla punkt wejœcia dla aplikacji konsoli.
//


#include "stdafx.h"
#include <iostream>
#include "dir_list.h"

using namespace std;
int Student::counter = 0;
int _tmain(int argc, _TCHAR* argv[])
{

	Dir_list baza;
	baza.loadlist();
	baza.showlist();

	string sname, ssurname;
	int snr_album, ssubjects_size;
	string * sarray;
	//TODO: zwolnienie pamiêci!
	cout << "Dodawanie studenta: \n";
	cout << "Imie [spacja] Nazwisko: ";
	cin >> sname >> ssurname;
	cout << "Nr albumu [spacja] Il. przedmiotow: ";
	cin >> snr_album >> ssubjects_size;
	sarray = new string[ssubjects_size];

	Student s(sname, ssurname, snr_album, ssubjects_size, sarray);
    baza.addstud(s);

	int studtodel;
	cout << "Student do usuniecia z listy:";
	cin >> studtodel;
	baza.delstud(studtodel);

	baza.savelist();
	system("pause");
	return 0;
}

