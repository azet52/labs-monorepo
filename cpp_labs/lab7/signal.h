#pragma once
#include <iostream>
#include <string>
#include <vector>
using namespace std;

class Signal
{
	friend class AddBlock;
	friend class TimeShift;
	friend class ThresholdingBlock;
	friend class IntegrationBlock;
private:
	double time_start;
	double time_sample;
	vector<double> value_vector;

public:
	Signal();
	void reset();
	int load_file(const string &input_path);
	Signal &operator=(const Signal &signal);
	friend istream& operator>>(istream &inp, Signal &c);
	friend ostream& operator<<(ostream &out, Signal &c);
};
