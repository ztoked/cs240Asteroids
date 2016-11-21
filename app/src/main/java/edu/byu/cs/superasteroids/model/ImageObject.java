package edu.byu.cs.superasteroids.model;

import android.media.Image;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class ImageObject
{
    /** The image that pertains to the object */
    private String address;
    /** the width of the image */
    private int width;
    /** height of the image */
    private int height;

    public ImageObject(String address)
    {
        this.address = address;
    }
    public ImageObject(String address, int width, int height)
    {
        this.address = address;
        this.width = width;
        this.height = height;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String myImage)
    {
        this.address = myImage;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }
}
