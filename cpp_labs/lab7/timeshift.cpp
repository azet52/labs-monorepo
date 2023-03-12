#include "stdafx.h"
#include "timeshift.h"

TimeShift::TimeShift(double offset)
{
	this->offset = offset;
}

Signal &TimeShift::process(Signal &signal)
{
	signal.time_start = signal.time_start + offset;
	return signal;
}