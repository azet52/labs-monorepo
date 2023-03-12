#include <vector>
#include "block.h"
#include "signal.h"

class Pipeline
{
private:
	vector<Block*> pipeline_vector;
public:
	~Pipeline();
	void block_add(Block *block);
	Signal process(Signal signal);
};