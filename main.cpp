#include <vector>
#include <memory>

#include "Connector.h"
#include "Thread.h"

//delete it
#include <iostream>

int main(int, char**)
{
	std::vector<uint16_t> ports = {{ 843, 81, 90 }};
	std::vector<std::shared_ptr<Connector>> connectors;

	for(auto &port: ports)
	{
		auto connector = std::make_shared<Connector>(port);
		connectors.push_back(connector);
		connector->start();
	}

	std::cout << "Connectors started" << std::endl;

	for(auto &iter: connectors)
		iter->stop();

	std::cout << "Connectors stopped" << std::endl;

	connectors.clear();

	std::cout << "Connectors removed" << std::endl;

	std::cout << "Gracefull exit" << std::endl;

	return 0;
}

