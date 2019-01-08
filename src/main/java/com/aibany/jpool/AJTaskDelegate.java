package com.aibany.jpool;

public interface AJTaskDelegate {

    void taskShouldStart(AJTaskObject task);

    void taskFinished(boolean success, AJTaskObject task);
}
