#include "student.h"
#include <fstream>
class Dir_list
{
	Student *head = NULL;
	Student *tail = NULL;
	//Student t(string name, string surname, int nr_album, int subjets_size, string *subjects);
public:
	int list_length;
	void addstud(Student s);
	void showstud(int);
	void showlist();
	void delstud(int);
	void loadlist();
	void savelist();
};