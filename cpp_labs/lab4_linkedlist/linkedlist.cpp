#include "stdafx.h"
#include "linkedlist.h"

LinkedList::Node::Node(Student *a)
{
	this->value = a;
	this->next = NULL;
}

LinkedList::Node::~Node()
{
	if (this->value != NULL)
		delete this->value;
}


LinkedList::~LinkedList()
{
	Node *current = head;
	Node *tmp;
	while (current != NULL)
	{
		tmp = current->next;
		delete current;
		current = tmp;
	}
	head = NULL;
	tail = NULL;
}

void LinkedList::add(Student *s)
{
	if (head == NULL)
	{
		head = new Node(s);
		tail = head;
	}
	else
	{
		tail->next = new Node(s);
		tail = tail->next;
	}
}

int LinkedList::size()
{
	int size = 0;
	Node *current = head;
	while (current != NULL)
	{
		current = current->next;
		++size;
	}
	return size;
}

Student* LinkedList::get(int idx)
{
	Student *student = NULL;
	Node *current = head;

	for (int i = 0; i < idx && current != NULL; i++)
		current = current->next;

	if (current != NULL)
		student = current->value;

	return student;
}

void LinkedList::erase(int to_erase)
{
	Node *node_to_erase = head;
	Node *tmp = head;

	for (int i = 0; node_to_erase != NULL && i < to_erase; i++)
	{
		tmp = node_to_erase;
		node_to_erase = node_to_erase->next;
	}

	if (node_to_erase != NULL)
	{
		if (node_to_erase == head)
		{
			head = node_to_erase->next;
			tmp = NULL;
		}
		else
			tmp->next = tmp->next->next;
		if (node_to_erase == tail)
			tail = tmp;
		delete node_to_erase;
	}
}

int LinkedList::loadlist(const string &file_path)
{
	fstream file(file_path, ios::in);
	if (!file.good())
		return -1;

	while (!file.eof())
	{
		Student *student = new Student();
		file >> *student;
		add(student);
	}

	file.close();
	return 0;
}

int LinkedList::savelist(const string &file_path)
{
	fstream file(file_path, ios::out);
	if (!file.good())
		return -1;

	Node *current = head;
	while (current != NULL)
	{
		file << *(current->value) << endl;
		current = current->next;
	}

	file.close();
	return 0;
}