#pragma once
#include <vector>
#include "signal.h"
using namespace std;

class Block
{
public:
	virtual ~Block() {}
	virtual Signal &process(Signal &signal) = 0;
};