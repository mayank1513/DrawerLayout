package com.mayank.srilaprabhupadavani;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class DrawerLayout extends RelativeLayout implements View.OnTouchListener{
    public static final float CLICK_DRAG_TOLERANCE = 10;
    static int animDuration = 300;
    static float dp10;
    static float size_x;

    private float downX;
    private float downY;
    private float dX;
    public View background; //set this from activity or fragment
    float alphaMax = 1;
    int scrollDirection = 0, direction = 1, drawerWidth = 0;
    final public static int Horizontal = 1, Vertical = 2;

    public DrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dp10 = getResources().getDimensionPixelSize(R.dimen.dp10);
        Point size = new Point();
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(size);
        size_x = size.x;
        init();
    }

    public void init(){
        if(getGravity()==Gravity.RIGHT) direction = -1;
        if(direction==-1) {
            addView(inflate(getContext(), R.layout.rd, null));
            drawerWidth = getResources().getDimensionPixelSize(R.dimen.r_drawer_width);
        } else {
            addView(inflate(getContext(), R.layout.d, null));
            drawerWidth = getResources().getDimensionPixelSize(R.dimen.drawer_width);
        }
        setOnTouchListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            scrollDirection = 0;
            downX = ev.getRawX();
            downY = ev.getRawY();
            dX = this.getX() - downX;
            peep();
        } else if(action == MotionEvent.ACTION_MOVE){
            if(scrollDirection == 0) {
                scrollDirection = Math.abs(ev.getRawX() - downX) > CLICK_DRAG_TOLERANCE ? Horizontal
                        : Math.abs(ev.getRawY() - downY) > CLICK_DRAG_TOLERANCE ? Vertical : 0;
            } else if(scrollDirection == Horizontal){
                return onTouch(this, ev);
            }
        } else if(action == MotionEvent.ACTION_UP && scrollDirection != 0 && scrollDirection != Vertical)
            return onTouch(this, ev);
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent ev) {
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            downX = ev.getRawX();
            dX = this.getX() - downX;
            peep();
        } else if(action == MotionEvent.ACTION_MOVE){
            float newX = ev.getRawX() + dX;
            newX = direction*Math.min(0, direction*newX);
            view.animate().x(newX).setDuration(0).start();
            background.animate().alpha(alphaMax + direction*newX/drawerWidth).setDuration(0).start();
        } else if(action == MotionEvent.ACTION_UP) {
            float upRawX = ev.getRawX();
            if(direction==1? downX >drawerWidth: downX < size_x - drawerWidth) collapse();
            else if (direction*(upRawX- downX) > 10*CLICK_DRAG_TOLERANCE) expand();
            else if(direction*(downX -upRawX) > 10*CLICK_DRAG_TOLERANCE) collapse();
            else if(Math.abs(this.getX()) > drawerWidth) collapse();
            else expand();
        }
        return true;
    }

    public void collapse(){
        background.animate().alpha(0).setDuration(animDuration).start();
        if(direction == -1)
            this.animate().x(size_x - dp10).setDuration(animDuration).start();
        else {
            this.animate().x(-size_x + dp10).setDuration(animDuration).start();
        }
    }

    public void disableDrawer(){
        this.animate().x(-this.getWidth()).setDuration(0).start();
        background.animate().alpha(0).setDuration(0).start();
    }

    public void peep(){
        if(this.getX()!=0) {
            float newX = direction*(-drawerWidth + 2 * dp10);
            this.animate().x(newX).setDuration(animDuration).start();
            background.animate().alpha(alphaMax + direction*newX/drawerWidth).setDuration(animDuration).start();
            dX += direction*(size_x - drawerWidth + 2 * dp10);
        }
    }

    public void expand(){
        this.animate().x(0).setDuration(animDuration).start();
        background.animate().alpha(alphaMax).setDuration(animDuration).start();
    }

    public boolean isCollapsed() {
        return this.getX()!=0;
    }
}
