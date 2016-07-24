package com.lyft.scoop;

import android.view.View;

public abstract class ViewController {

    public boolean attached;
    public Scoop scoop;
    public View view;
    public boolean destroyOnDetach = true;

    final void attach(View view) {
        this.attached = true;
        this.view = view;
        onAttach();
    }

    public void onAttach() {}

    protected final boolean attached() {
        return this.attached;
    }

    final void detach(View view) {
        onDetach();

        if (destroyOnDetach) {
            this.view = null;
            this.attached = false;
        }
    }

    public void onDetach() {}

    public View getView() {
        if (!attached) {
            throw new IllegalStateException("View accessed while ViewController is detached.");
        }
        return this.view;
    }

    protected abstract int layoutId();

    protected Scoop getScoop() {
        return scoop;
    }

    void setScoop(Scoop scoop) {
        this.scoop = scoop;
    }

    static ViewController fromView(View view) {
        if (view != null) {
            ViewController viewController = (ViewController) view.getTag(ViewControllerInflater.VIEW_CONTROLLER_TAG);
            return viewController;
        }

        return null;
    }
}
