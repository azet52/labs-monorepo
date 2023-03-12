#pragma once
#include <iostream>
#include "vector.h"

using namespace std;
class Matrix
{
	friend class Vector;
private:
	double matrix_length;
	Vector *matrix;
public:
	Matrix();
	Matrix(double);
	~Matrix();
	Matrix operator= (const Matrix&);
	Matrix operator+ (Matrix);
	Matrix operator- (Matrix);
	Matrix operator* (double);
	Matrix operator* (Matrix);
	Matrix operator* (Vector);
	friend istream& operator>>(istream &inp, Matrix &c);
	friend ostream& operator<<(ostream &out, Matrix &c);
	};