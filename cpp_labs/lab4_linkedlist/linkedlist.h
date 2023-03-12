#include "student.h"
class LinkedList
{
	class Node
	{
		// Only LinkedList has access to Node class
	public:
		Student *value = NULL;
		Node *next = NULL;
		Node(Student*);
		~Node();
	};

	Node *head = NULL;
	Node *tail = NULL;

public:
	~LinkedList();
	void add(Student *s);
	void erase(int);
	int size();
	Student *get(int);
	int loadlist(const string &file_path);
	int savelist(const string &file_path);
};
