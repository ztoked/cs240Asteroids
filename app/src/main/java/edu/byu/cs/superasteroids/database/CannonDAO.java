package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.ImageObject;
import edu.byu.cs.superasteroids.model.Point;


/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class CannonDAO
{
    /** the database for the CannonDAO */
    private SQLiteDatabase db;

    /** the constructor */
    public CannonDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the cannons by a certain ID number */
    public List<Cannon> getCannons()
    {
        final String SQL = "select * " +
                "from cannons";

        List<Cannon> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Cannon c = new Cannon();

                c.setID((int) cursor.getLong(0));
                c.setAttachPoint(new Point(cursor.getString(1)));
                c.setEmitPoint(new Point(cursor.getString(2)));
                c.setMyImage(new ImageObject(cursor.getString(3), cursor.getInt(4), cursor.getInt(5)));
                c.setAttackImage(new ImageObject(cursor.getString(6), cursor.getInt(7), cursor.getInt(8)));
                c.setAttackSound(cursor.getString(9));
                c.setDamage(10);

                result.add(c);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;
    }

    /** adds a cannon to the database */
    public boolean addCannon(Cannon c)
    {
        ContentValues values = new ContentValues();

        values.put("attachPoint", c.getAttachPoint().getStringPoint());
        values.put("emitPoint", c.getEmitPoint().getStringPoint());
        values.put("image", c.getMyImage().getAddress());
        values.put("imageWidth", c.getMyImage().getWidth());
        values.put("imageHeight", c.getMyImage().getHeight());
        values.put("attackImage", c.getAttackImage().getAddress());
        values.put("attackImageWidth", c.getAttackImage().getWidth());
        values.put("attackImageHeight", c.getAttackImage().getHeight());
        values.put("attackSound", c.getAttackSound());
        values.put("damage", c.getDamage());


        int id = (int)db.insert("cannons", null, values);
        if (id >= 0)
        {
            c.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }
    /** empties all of the cannons from the database */
    public boolean emptyCannons()
    {
        return false;
    }
}
