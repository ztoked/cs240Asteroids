package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.ImageObject;

/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class AsteroidDAO
{
    /** the asteroid db */
    private SQLiteDatabase db;

    /** constructor */
    public AsteroidDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** add an Asteroid to the database */
    public boolean addAsteroid(Asteroid a)
    {
        ContentValues values = new ContentValues();


        values.put("name", a.getName());
        values.put("image", a.getMyImage().getAddress());
        values.put("imageWidth", a.getMyImage().getWidth());
        values.put("imageHeight", a.getMyImage().getHeight());
        values.put("type", a.getType());

        int id = (int)db.insert("asteroids", null, values);
        if (id >= 0)
        {
            a.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    /** gets all of the asteroids that have a certain ID */
    public List<Asteroid> getAsteroids()
    {
        final String SQL = "select * " +
                "from asteroids";

        List<Asteroid> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Asteroid a = new Asteroid();

                a.setID((int)cursor.getLong(0));
                a.setName(cursor.getString(1));
                ImageObject newImage =
                        new ImageObject(cursor.getString(2),cursor.getInt(3),cursor.getInt(4));
                a.setMyImage(newImage);
                a.setType(cursor.getString(5));

                result.add(a);

                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;
    }

    /** gets all of the asteroids by the type of asteroid */
    public List<Asteroid> getAsteroidsByType(String type)
    {
        return null;
    }

}