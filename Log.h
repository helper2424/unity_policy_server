#pragma once
#include "string"

class Log {
public:
	Log();

	Log* warning(const char*);
	Log* debug(const char*);
	Log* error(const char*);
};
