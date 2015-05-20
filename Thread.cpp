#include "Thread.h"

#include <iostream>

Thread::Thread()
{
	std::cout << "Thread created" << std::endl;
}

Thread::~Thread()
{
	std::cout << "Thread destructed" << std::endl;
}

void Thread::run()
{
	std::cout << "Thread " << this->thread.get_id() << " started" << std::endl;
}

void Thread::start()
{
	this->thread = thread_t(&Thread::run, this);
}

void Thread::stop()
{
	this->thread.join();
	std::cout << "Thread " << this->thread.get_id() << " stopped" << std::endl;
}


