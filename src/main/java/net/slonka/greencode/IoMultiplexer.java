package net.slonka.greencode;

public enum IoMultiplexer {
    EPOLL, KQUEUE, JDK, IO_URING
}