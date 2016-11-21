package edu.byu.cs.superasteroids.model;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Point
{
    String stringPoint;
    /** the horizontal component of the point */
    int x;
    /** the vertical component of the point */
    int y;

    public Point(String stringPoint)
    {
        this.stringPoint = stringPoint;
        StringBuilder xString = new StringBuilder();
        for(int i = 0; i < stringPoint.length(); i++)
        {
            if(stringPoint.charAt(i) == ',')
            {
                break;
            }
            xString.append(stringPoint.charAt(i));
        }
        StringBuilder yString = new StringBuilder();
        boolean isYValue = false;
        for(int i = 0; i < stringPoint.length(); i++)
        {
            if(isYValue)
            {
                yString.append(stringPoint.charAt(i));
            }
            if(stringPoint.charAt(i) == ',')
            {
                isYValue = true;
            }
        }
        x = Integer.parseInt(xString.toString());
        y = Integer.parseInt(yString.toString());
    }

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
        stringPoint = x + "," + y;
    }

    public String getStringPoint()
    {
        return stringPoint;
    }

    public void setStringPoint(String stringPoint)
    {
        this.stringPoint = stringPoint;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
