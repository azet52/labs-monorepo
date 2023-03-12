#include "stdafx.h"
#include "Vector.h"
#include <iostream>
using namespace std;

Vector::Vector()
{
	vector_length = 0;
	vector = NULL;
}
Vector::Vector(double vector_length)
{
	this->vector_length = vector_length;
	vector = new double[(int)vector_length];
	for (int i = 0; i<vector_length; i++)
		cin >> vector[i];
}
Vector:: ~Vector()
{
	if (vector)
	{
		delete[] vector;
		vector = NULL;
	}
}

Vector Vector::operator=(const Vector& a)
{
	if (vector_length != 0)
		delete[] vector;

	vector = new double[(int)a.vector_length];
	vector_length = a.vector_length;
	for (int i = 0; i < a.vector_length; i++)
	{
		vector[i] = a.vector[i];
	}
	return *this;
}
Vector Vector::operator+ (Vector a)
{
	if (vector_length == a.vector_length)
	{
		for (int i = 0; i < vector_length; i++)
			vector[i] = vector[i] + a.vector[i];
	}
	else
		cout << "Nie mozna dodac vectorow o roznych rozmiarach\n";

	return *this;
}

Vector Vector::operator- (Vector a)
{
	if (vector_length == a.vector_length)
	{
		for (int i = 0; i < vector_length; i++)
			vector[i] = vector[i] - a.vector[i];
	}
	else
	{
		cout << "Nie mozna odjac vectorow o roznych rozmiarach\n";
	}
	return *this;
}

Vector Vector::operator* (double a)
{
	for (int i = 0; i < vector_length; i++)
		vector[i] = a * vector[i];
	return *this;
}

istream& operator>>(istream &inp, Vector &c)
{
	inp >> c.vector_length;
	for (int i = 0; i < c.vector_length; i++)
		inp >> c.vector[i];
	return inp;
}
ostream& operator<<(ostream &out, Vector &c)
{
	for (int i = 0; i < c.vector_length; i++)
		out << c.vector[i] << " ";
	return out;
}
