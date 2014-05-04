#include "stdafx.h"
#include "student.h"

using namespace std;
Student::Student()
{
	counter++;
	name = "0";
	surname = "0";
	nr_album = 0;
	subjects_size = 0;
	this->array = NULL;
}

Student::Student(string name, string surname, int nr_album, int subjects_size, string *subjects)
{
	counter++;
	this->name = name;
	this->surname = surname;
	this->nr_album = nr_album;
	this->subjects_size = subjects_size;
	for (int i = 0; i < subjects_size; i++)
		this->array[i] = subjects[i];
}

Student::~Student()
{
	if (array)
	{
		delete[] array;
		array = NULL;
	}
}

Student Student:: operator= (const Student& a)
{
	if (subjects_size != 0)
		delete[] array;
	name = a.name;
	surname = a.surname;
	nr_album = a.nr_album;
	subjects_size = a.subjects_size;
	for (int i = 0; i < a.subjects_size; i++)
		array[i] = a.array[i];
	return *this;

}


istream& operator>>(istream &inp, Student &c)
{
	inp >> c.name >> c.surname >> c.nr_album >> c.subjects_size;
	for (int i = 0; i < c.subjects_size; i++)
		inp >> c.array[i];
	return inp;
}


ostream& operator<<(ostream &outp, Student &c)
{
	outp << c.name << c.surname << c.nr_album << c.subjects_size;
	for (int i = 0; i < c.subjects_size; i++)
		outp << c.array[i];

	return outp;
}
