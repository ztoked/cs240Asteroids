package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.ImageObject;
import edu.byu.cs.superasteroids.model.Point;
import edu.byu.cs.superasteroids.model.ShipBody;

/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class ShipBodyDAO
{
    /** the database for ship bodies */
    private SQLiteDatabase db;

    /** the constructor */
    public ShipBodyDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the ship bodies by a certain ID number */
    public List<ShipBody> getShipBodies()
    {
        final String SQL = "select * " +
                "from main_bodies";

        List<ShipBody> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                ShipBody body = new ShipBody();

                body.setID((int) cursor.getLong(0));
                body.setCannonAttach(new Point(cursor.getString(1)));
                body.setEngineAttach(new Point(cursor.getString(2)));
                body.setExtraAttach(new Point(cursor.getString(3)));
                body.setMyImage(new ImageObject(cursor.getString(4),
                        cursor.getInt(5), cursor.getInt(6)));


                result.add(body);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;      }

    /** adds a ship body to the database */
    public boolean addShipBody(ShipBody sb)
    {
        ContentValues values = new ContentValues();

        values.put("cannonAttach", sb.getCannonAttach().getStringPoint());
        values.put("engineAttach", sb.getEngineAttach().getStringPoint());
        values.put("extraAttach", sb.getExtraAttach().getStringPoint());
        values.put("image", sb.getMyImage().getAddress());
        values.put("imageWidth", sb.getMyImage().getWidth());
        values.put("imageHeight", sb.getMyImage().getHeight());

        int id = (int)db.insert("main_bodies", null, values);
        if (id >= 0)
        {
            sb.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    /** empties all of the ship bodies from the database */
    public boolean emptyShipBodies()
    {
        return false;
    }
}
