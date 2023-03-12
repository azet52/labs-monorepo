#include "stdafx.h"
#include "addblock.h"

AddBlock::AddBlock(double offset)
{
	this->offset = offset;
}

Signal &AddBlock::process(Signal &signal)
{
	for (unsigned int i = 0; i < signal.value_vector.size(); i++)
		signal.value_vector[i] = signal.value_vector[i] + offset;
	return signal;
}