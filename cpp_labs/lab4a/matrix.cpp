#include "matrix.h"
#include <iostream>
#include "stdafx.h"
using namespace std;

Matrix::Matrix()
{
	matrix_length = 0;
	matrix = NULL;
}
Matrix::Matrix(double matrix_length)
{
	this->matrix_length = matrix_length;
	matrix = new Vector[(int)matrix_length];
	for (int i = 0; i < matrix_length; i++)
	{
		Vector t(matrix_length);
		matrix[i] = t;
	}
}
Matrix:: ~Matrix()
{
	if (matrix)
	{
		delete[] matrix;
		matrix = NULL;
	}
}

Matrix Matrix::operator=(const Matrix& a)
{
	if (matrix_length != 0)
		delete[] matrix;

	matrix = new Vector[(int)a.matrix_length];
	matrix_length = a.matrix_length;
	for (int i = 0; i < a.matrix_length; i++)
	{
		matrix[i] = a.matrix[i];
	}
	return *this;
}
Matrix Matrix::operator+ (Matrix a)
{
	if (matrix_length == a.matrix_length)
	{
		for (int i = 0; i < matrix_length; i++)
			matrix[i] = matrix[i] + a.matrix[i];
	}
	else
		cout << "Nie mozna dodac matrixow o roznych rozmiarach\n";

	return *this;
}

Matrix Matrix::operator- (Matrix a)
{
	if (matrix_length == a.matrix_length)
	{
		for (int i = 0; i < matrix_length; i++)
			matrix[i] = matrix[i] - a.matrix[i];
	}
	else
	{
		cout << "Nie mozna odjac matrixow o roznych rozmiarach\n";
	}
	return *this;
}

Matrix Matrix::operator* (double a)
{
	for (int i = 0; i < matrix_length; i++)
		matrix[i] = matrix[i] * a;
	return *this;
}
Matrix Matrix::operator* (Vector a)
{
	for (int i = 0; i < matrix_length; i++)
	{
		for (int j = 0; j < matrix_length; j++)
		matrix[j].vector[i] = matrix[j].vector[i] * a.vector[i];
	}
		return *this;
}
Matrix Matrix::operator* (Matrix a)
{
	for (int i = 0; i < matrix_length; i++)
	{
		for (int j = 0; j < matrix_length; j++)
			matrix[j].vector[i] = matrix[j].vector[i] * a.matrix[i].vector[j];
	}
	return *this;
}
istream& operator>>(istream &inp, Matrix &c)
{
	inp >> c.matrix_length;
	for (int i = 0; i < c.matrix_length; i++)
		//c.matrix[i].vector_length = c.matrix_length;
		inp >> c.matrix[i];
	return inp;
}
ostream& operator<<(ostream &out, Matrix &c)
{
	for (int i = 0; i < c.matrix_length; i++)
		out << c.matrix[i] << " ";
	return out;
}
