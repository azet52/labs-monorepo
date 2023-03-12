#include "block.h"
#include "signal.h"
class ThresholdingBlock : public Block
{
private:
	double threshold;
public:
	ThresholdingBlock(double);
	~ThresholdingBlock() {}
	Signal &process(Signal &signal);
};