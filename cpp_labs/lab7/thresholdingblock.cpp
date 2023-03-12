#include "stdafx.h"
#include "thresholdingblock.h"

ThresholdingBlock::ThresholdingBlock(double threshold )
{
	this->threshold = threshold;

}

Signal &ThresholdingBlock::process(Signal &signal)
{
	for (unsigned int i = 0; i < signal.value_vector.size(); i++)
	{
		if (signal.value_vector[i] > threshold)
			signal.value_vector[i] = 1;
		else if (signal.value_vector[i] <= threshold)
			signal.value_vector[i] = 0;
	}
	return signal;
}