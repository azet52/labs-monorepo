// lab7.cpp: Okreœla punkt wejœcia dla aplikacji konsoli.
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include <fstream>
#include <stdlib.h> 
#include "pipeline.h"
#include "addblock.h"
#include "timeshift.h"
#include "thresholdingblock.h"
#include "integrationblock.h"

using namespace std;


int main(int argc, char* argv[]) {

	string input;
	Pipeline pipeline;

	for (int i = 1; i < argc; i++){
		string flag(argv[i]);
		if (flag == "-i")
		{
			i++;
			string temp(argv[i]);
			input = temp;
		}
		else if (flag == "-n")
		{
			IntegrationBlock *integrationBlock = new IntegrationBlock();
			pipeline.block_add(integrationBlock);
		}
		else if (flag == "-a")
		{
			i++;
			double add = atof (argv[i]);
			AddBlock *addBlock = new AddBlock(add);
			pipeline.block_add(addBlock);
		}
		else if (flag == "-t")
		{
			i++;
			double add = atof(argv[i]);
			TimeShift *timeShift = new TimeShift(add);
			pipeline.block_add(timeShift);
		}
		else if (flag == "-m")
		{
			i++;
			double thresh = atof (argv[i]);
			ThresholdingBlock *thresholdingBlock = new ThresholdingBlock(thresh);
			pipeline.block_add(thresholdingBlock);
		}
	}
	// method 1


	Signal signal;
	signal.load_file(input);

	Signal output_signal = pipeline.process(signal);

	fstream output_file("output.txt", ios::out);
	output_file << output_signal;
	output_file.close();

	return 0;
}
