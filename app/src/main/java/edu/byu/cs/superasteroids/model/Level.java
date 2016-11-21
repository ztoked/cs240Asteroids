package edu.byu.cs.superasteroids.model;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class Level
{
    private int ID;
    private int number;
    /** Title of the level */
    private String title;
    /** Hint to help with the level */
    private String hint;
    /** Width of the level */
    private int width;
    /** Height of the level */
    private int height;
    /** the music that plays in the background of the level */
    private String music;

    List<Asteroid> levelAsteroids;

    List<LevelObject> levelObjects;

    public Level(int ID, int number, String title, String hint, int width, int height, String music)
    {
        this.ID = ID;
        this.number = number;
        this.title = title;
        this.hint = hint;
        this.width = width;
        this.height = height;
        this.music = music;
        levelAsteroids = new ArrayList<>();
        levelObjects = new ArrayList<>();
    }

    public Level()
    {
        levelAsteroids = new ArrayList<>();
        levelObjects = new ArrayList<>();
    }

    /** Starts the music for the level */
    public void startMusic()
    {

    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getHint()
    {
        return hint;
    }

    public void setHint(String hint)
    {
        this.hint = hint;
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

    public String getMusic()
    {
        return music;
    }

    public void setMusic(String music)
    {
        this.music = music;
    }


    public List<Asteroid> getLevelAsteroids()
    {
        return levelAsteroids;
    }

    public void setLevelAsteroids(List<Asteroid> levelAsteroids)
    {
        this.levelAsteroids = levelAsteroids;
    }

    public List<LevelObject> getLevelObjects()
    {
        return levelObjects;
    }

    public void setLevelObjects(List<LevelObject> levelObjects)
    {
        this.levelObjects = levelObjects;
    }

    public void draw()
    {
        for(int i = 0; i < levelAsteroids.size(); i++)
        {
            levelAsteroids.get(i).draw();
        }
    }

    public void update(double elapsedTime)
    {
        for(int i = 0; i < levelAsteroids.size(); i++)
        {
            levelAsteroids.get(i).update(elapsedTime);
        }
    }
}
