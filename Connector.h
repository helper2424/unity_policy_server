#pragma once

#include <stdint.h>
#include "Thread.h"

class Connector : public Thread
{
public:
	Connector(uint16_t port);
protected:
	uint16_t port;

	virtual void init();
	virtual void finalize();
};
