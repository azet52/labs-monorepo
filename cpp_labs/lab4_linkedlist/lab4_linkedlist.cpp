#include <iostream>
#include "stdafx.h"
#include "linkedlist.h"
#include "student.h"

int main()
{

	LinkedList database;
	database.loadlist("baza.txt");

	string antek_subjects[3] = { "Filozofia", "York", "Fog" };
	Student *antek = new Student("Antek", "Kowalski", 209906, 3, antek_subjects);
	database.add(antek);

	cout << "Aktualna zawartosc bazy danych" << endl;
	int n = database.size();
	for (int i = 0; i < n; i++)
		cout << *(database.get(i)) << endl;

	database.savelist("baza.txt");
		system("pause");
	return 0;
}