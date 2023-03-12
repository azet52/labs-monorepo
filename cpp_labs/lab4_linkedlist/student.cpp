#include "stdafx.h"
#include "student.h"


Student::Student(string name, string surname, int index_number, int subjects_number, string *subjects)
{
	cout << "n" << name;
	this->name = name;
	cout << "s" << surname;
	this->surname = surname;
	cout << "i" << index_number;
	this->index_number = index_number;
	cout << "sn" << subjects_number;
	this->subjects_number = subjects_number;
	subjects = new string[subjects_number];
	cout << "subjects" << subjects;
	for (int i = 0; i < subjects_number; i++) {
		cout << "idx1" << i;
		this->subjects[i] = subjects[i];
		cout << "idx2" << i;
	}
}

Student::~Student()
{
	if (subjects)
	{
		delete[] subjects;
		subjects = NULL;
	}
}

Student::Student()
{
	name = "";
	surname = "";
	index_number = 0;
	subjects_number = 0;
	subjects = NULL;
}

Student::Student(const Student& a)
{
	if (subjects != NULL)
	{
		delete[] subjects;
		subjects = NULL;
		subjects_number = 0;
	}
	name = a.name;
	surname = a.surname;
	index_number = a.index_number;
	subjects_number = a.subjects_number;
	subjects = new string[subjects_number];
	for (int i = 0; i < a.subjects_number; i++)
		subjects[i] = a.subjects[i];
}

Student& Student::operator=(const Student& a)
{
	if (subjects != NULL)
	{
		delete[] subjects;
		subjects = NULL;
		subjects_number = 0;
	}
	name = a.name;
	surname = a.surname;
	index_number = a.index_number;
	subjects_number = a.subjects_number;
	subjects = new string[subjects_number];
	for (int i = 0; i < a.subjects_number; i++)
		subjects[i] = a.subjects[i];
	return *this;
}



istream& operator>>(istream &inp, Student &c)
{
	if (c.subjects != NULL)
	{
		delete[] c.subjects;
		c.subjects = NULL;
		c.subjects_number = 0;
	}
	inp >> c.name >> c.surname >> c.index_number >> c.subjects_number;
	c.subjects = new string[c.subjects_number];
	for (int i = 0; i < c.subjects_number; i++)
		inp >> c.subjects[i];
	return inp;
}


ostream& operator<<(ostream &outp, Student &c)
{
	outp << c.name << c.surname << c.index_number << c.subjects_number;
	for (int i = 0; i < c.subjects_number; i++)
		outp << c.subjects[i];

	return outp;
}
