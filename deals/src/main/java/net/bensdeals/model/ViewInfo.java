package net.bensdeals.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class ViewInfo implements Parcelable {
    private float mLeft;
    private float mTop;
    private int mWidth;
    private int mHeight;

    public ViewInfo() {
    }

    public ViewInfo(Parcel in) {
        mLeft = in.readFloat();
        mTop = in.readFloat();
        mWidth = in.readInt();
        mHeight = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(mLeft);
        parcel.writeFloat(mTop);
        parcel.writeInt(mWidth);
        parcel.writeInt(mHeight);
    }

    public static ViewInfo create(View view) {
        ViewInfo viewInfo = new ViewInfo();
        if (view != null) {
            int[] screenLocation = new int[2];
            view.getLocationOnScreen(screenLocation);
            viewInfo.setLeft(screenLocation[0]);
            viewInfo.setTop(screenLocation[1]);

            viewInfo.setWidth(view.getWidth());
            viewInfo.setHeight(view.getHeight());
        }
        return viewInfo;
    }

    public ViewInfo setLeft(int left) {
        mLeft = left;
        return this;
    }

    public ViewInfo setTop(int top) {
        mTop = top;
        return this;
    }

    public float getLeft() {
        return mLeft;
    }

    public float getTop() {
        return mTop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ViewInfo> CREATOR = new Parcelable.Creator<ViewInfo>() {
        public ViewInfo createFromParcel(Parcel in) {
            return new ViewInfo(in);
        }

        public ViewInfo[] newArray(int size) {
            return new ViewInfo[size];
        }
    };

    public static float getScaleX(ViewInfo viewInfo, View view) {
        return viewInfo.getWidth() / view.getWidth() * 1.0f;
    }

    public static float getScaleY(ViewInfo viewInfo, View view) {
        return viewInfo.getHeight() / view.getHeight() * 1.0f;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }
}
