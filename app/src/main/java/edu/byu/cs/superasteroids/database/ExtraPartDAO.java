package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.ImageObject;
import edu.byu.cs.superasteroids.model.Point;

/**
 * Created by zachhalvorsen on 2/12/16.
 */
public class ExtraPartDAO
{
    /** the database for the ExtraPart DAO */
    private SQLiteDatabase db;

    /** constructor */
    public ExtraPartDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    /** gets all of the ExtraParts by a certain ID number */
    public List<ExtraPart> getExtraParts()
    {
        final String SQL = "select * " +
                "from extra_parts";

        List<ExtraPart> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                ExtraPart ep = new ExtraPart();

                ep.setID((int) cursor.getLong(0));
                ep.setAttachPoint(new Point(cursor.getString(1)));
                ep.setMyImage(new ImageObject(cursor.getString(2), cursor.getInt(3), cursor.getInt(4)));

                result.add(ep);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return result;
    }

    /** adds an ExtraPart to the database */
    public boolean addExtraPart(ExtraPart ep)
    {
        ContentValues values = new ContentValues();

        values.put("attachPoint", ep.getAttachPoint().getStringPoint());
        values.put("image", ep.getMyImage().getAddress());
        values.put("imageWidth", ep.getMyImage().getWidth());
        values.put("imageHeight", ep.getMyImage().getHeight());

        int id = (int)db.insert("extra_parts", null, values);
        if (id >= 0)
        {
            ep.setID(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    /** Empties all of the ExtraParts from the database */
    public boolean emptyExtraParts()
    {
        return false;
    }
}
