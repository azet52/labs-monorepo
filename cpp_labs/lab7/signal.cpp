#include "stdafx.h"
#include "signal.h"
#include <fstream>

Signal::Signal()
{
	reset();
}

Signal &Signal::operator=(const Signal &signal)
{
	this->time_start = signal.time_start;
	this->time_sample = signal.time_sample;
	this->value_vector = signal.value_vector;
	return *this;
}

void Signal::reset()
{
	time_start = 0;
	time_sample = 0;
	value_vector.clear();

}

int Signal::load_file(const string &input_path)
{
	reset();
	fstream input(input_path, ios::in);
	if (!input.good())
		return -1;
	input >> *this;
	input.close();
	return 0;
}


istream& operator>>(istream &inp, Signal &c)
{
	//TODO: check if there is enough data to read and input stream is readable
	double time, next_time, value;
	inp >> time >> value;
	c.value_vector.push_back(value);
	c.time_start = time;
	inp >> next_time >> value;
	c.time_sample = next_time - time;
	c.value_vector.push_back(value);
	while (!inp.eof())
	{
		inp >> time >> value;
		c.value_vector.push_back(value);
	}
	return inp;
}

ostream& operator<<(ostream &out, Signal &c)
{
	// TODO: Check if can write to output stream
	for (unsigned int i = 0; i < c.value_vector.size(); i++)
		out << c.time_start + (c.time_sample * i) << '\t' << c.value_vector[i] << endl;
	return out;
}