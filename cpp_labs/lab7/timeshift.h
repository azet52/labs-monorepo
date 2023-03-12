#include "block.h"
#include "signal.h"
class TimeShift : public Block
{
private:
	double offset;
public:
	TimeShift(double);
	~TimeShift() {}
	Signal &process(Signal &signal);
};