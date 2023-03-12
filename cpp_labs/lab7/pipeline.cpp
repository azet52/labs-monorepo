#include "stdafx.h"
#include "pipeline.h"

Pipeline::~Pipeline()
{
	for (unsigned int i = 0; i < pipeline_vector.size(); i++)
		delete  pipeline_vector[i];
}


void Pipeline::block_add(Block *block)
{
	pipeline_vector.push_back(block);
}

Signal Pipeline::process(Signal signal)
{
	Signal output_signal = signal;
	for (unsigned int i = 0; i < pipeline_vector.size(); i++)
	{
		output_signal = pipeline_vector[i]->process(output_signal);
	}
	return output_signal;
}