#include "Connector.h"
#include <sys/socket.h>

Connector::Connector(uint16_t port):port(port)
{

}

void Connector::init()
{
	int sock_fd = socket(AF_INET, SOCK_STREAM, 0);

	if(sock_fd < 0)
		std::cout << "Can't create socket";
}

void Connector::finalize()
{

}
