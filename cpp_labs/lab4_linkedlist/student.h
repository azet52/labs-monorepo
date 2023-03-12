#pragma once
#include <string>
#include <fstream>
#include <iostream>
using namespace std;
class Student
{
	friend class LinkedList;
private:
	string name;
	string surname;
	int index_number;
	int subjects_number;
	string *subjects;
public:
	Student();
	Student(const Student &);
	Student(string, string, int, int, string *);
	~Student();
	Student& operator=(const Student &);
	friend istream& operator>>(istream &inp, Student &c);
	friend ostream& operator<<(ostream &outp, Student &c);
};
