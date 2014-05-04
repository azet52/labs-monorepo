#include <iostream>
#include <string>
using namespace std;

class Student
{
private:
	string name;
	string surname;
	int nr_album;
	int subjects_size;
	string *array;
public:
	Student *next = NULL;
	static int counter;
	Student();
	Student(string, string, int, int, string *);
	~Student();
	Student operator= (const Student&);
	friend istream& operator>>(istream &inp, Student &c);
	friend ostream& operator<< (ostream &outp, Student &c);
};
