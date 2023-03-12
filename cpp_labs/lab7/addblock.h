#include "block.h"
#include "signal.h"
class AddBlock : public Block
{
private:
	double offset;
public:
	AddBlock(double);
	~AddBlock() {}
	Signal &process(Signal &signal);
};