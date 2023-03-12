#include "stdafx.h"
#include "integrationblock.h"

IntegrationBlock::IntegrationBlock()
{
	area = 0;
}

Signal &IntegrationBlock::process(Signal &signal)
{
	area = 0;
	for (unsigned int i = 0; i < signal.value_vector.size(); i++) {
		if (i < signal.value_vector.size() - 1) {
			area = area + signal.time_sample * (signal.value_vector[i] + signal.value_vector[i + 1]) / 2;
		}
		signal.value_vector[i] = area;
	}
	return signal;
}