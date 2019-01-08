package com.aibany.jpool;

public class AJTaskObject implements Runnable{

    private AJTaskDelegate delegate;

    private Object userObject;

    public AJTaskObject() {
        System.out.println("Create Task:" + this);
    }

    @Override
    public void run() {

        if (delegate != null) {
            delegate.taskShouldStart(this);
            delegate.taskFinished(true, this);
        }
    }

    public AJTaskDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(AJTaskDelegate delegate) {
        this.delegate = delegate;
    }

    public Object getUserObject() {
        return userObject;
    }

    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }
}
