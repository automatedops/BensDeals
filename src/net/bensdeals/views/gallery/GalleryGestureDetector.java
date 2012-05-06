package net.bensdeals.views.gallery;

import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
* Created with IntelliJ IDEA.
* User: Wei
* Date: 12-5-4
* Time: 上午12:33
* To change this template use File | Settings | File Templates.
*/
class GalleryGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private GalleryView galleryView;

    public GalleryGestureDetector(GalleryView galleryView) {
        this.galleryView = galleryView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return galleryView.onDown(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return galleryView.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {

        galleryView.getParent().requestDisallowInterceptTouchEvent(true);

        synchronized (galleryView) {
            galleryView.mNextX += (int) distanceX;
        }
        galleryView.requestLayout();

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Rect viewRect = new Rect();
        for (int i = 0; i < galleryView.getChildCount(); i++) {
            View child = galleryView.getChildAt(i);
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getTop();
            int bottom = child.getBottom();
            viewRect.set(left, top, right, bottom);
            if (viewRect.contains((int) e.getX(), (int) e.getY())) {
                if (galleryView.getOnItemClicked() != null) {
                    galleryView.getOnItemClicked().onItemClick(galleryView, child, galleryView.getLeftViewIndex() + 1 + i, galleryView.mAdapter.getItemId(galleryView.getLeftViewIndex() + 1 + i));
                }
                if (galleryView.getOnItemSelected() != null) {
                    galleryView.getOnItemSelected().onItemSelected(galleryView, child, galleryView.getLeftViewIndex() + 1 + i, galleryView.mAdapter.getItemId(galleryView.getLeftViewIndex() + 1 + i));
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Rect viewRect = new Rect();
        int childCount = galleryView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = galleryView.getChildAt(i);
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getTop();
            int bottom = child.getBottom();
            viewRect.set(left, top, right, bottom);
            if (viewRect.contains((int) e.getX(), (int) e.getY())) {
                if (galleryView.getOnItemLongClicked() != null) {
                    galleryView.getOnItemLongClicked().onItemLongClick(galleryView, child, galleryView.getLeftViewIndex() + 1 + i, galleryView.mAdapter.getItemId(galleryView.getLeftViewIndex() + 1 + i));
                }
                break;
            }
        }
    }
}
