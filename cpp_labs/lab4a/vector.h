#pragma once

#include <iostream>
#include "matrix.h"
using namespace std;

class Vector
{
	friend class Matrix;
private:
	double vector_length;
	double * vector;
public:
	Vector();
	Vector(double);
	~Vector();
	Vector operator= (const Vector&);
	Vector operator+ (Vector);
	Vector operator- (Vector);
	Vector operator* (double);
	friend istream& operator>>(istream &inp, Vector &c);
	friend ostream& operator<<(ostream &out, Vector &c);
};