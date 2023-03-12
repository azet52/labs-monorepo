#include "block.h"
#include "signal.h"
class IntegrationBlock : public Block
{
private:
	double area;
public:
	IntegrationBlock();
	~IntegrationBlock() {}
	Signal &process(Signal &signal);
};