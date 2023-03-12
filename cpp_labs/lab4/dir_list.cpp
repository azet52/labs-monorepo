#include "stdafx.h"
#include "dir_list.h"


void Dir_list::addstud(Student s)
{

}
void Dir_list::showstud(int)
{

}
void Dir_list::showlist()
{

}
void Dir_list::delstud(int)
{

}
void Dir_list::loadlist()
{
	head = new Student();
	//TODO: plik istnieje?
	fstream file("baza.txt", ios::in);
	if (file.good()) 
		cout << "baza.txt otwarta" << endl;
	file >> list_length;
	file >> *head;

	
	cout << "Wczytywanie " << list_length << " el." << endl;
	tail = head;
/*	while (tail != NULL)
	{
		file << t_name << t_surname << t_nr_album << t_subjects_size;
		for (int i = 0; i < t_subjects_size; i++)
			file << t_array[i];
		//TODO zwalnianie pamiêci!
		tail->next = new Student(t_name, t_surname, t_nr_album, t_subjects_size, t_array);
		tail = tail->next;
	}*/
	file.close();
}
void Dir_list::savelist()
{
	fstream file("baza.txt", ios::out | ios::in);

	file.close();
}