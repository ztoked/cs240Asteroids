package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.*;

/**
 * Created by zachhalvorsen on 2/9/16.
 */
public class DbOpenHelper extends SQLiteOpenHelper
{
    /** the name of the database file */
    private static final String DB_NAME = "asteroidsGame.sqlite";
    /** version of the database, this will not be changing throughout the project */
    private static final int DB_VERSION = 1;

    /** constructor */
    public DbOpenHelper(Context context)    {super(context, DB_NAME, null, DB_VERSION);}

    /** how the database is constructed */
    public void onCreate(SQLiteDatabase db)
    {
        String SQL = "drop table if exists background_objects;";
        db.execSQL(SQL);
        SQL =   "create table background_objects " +
                "(" +
                "   id integer not null primary key," +
                "   image text not null" +
                ");";
        db.execSQL(SQL);


        SQL =   "drop table if exists asteroids;";
        db.execSQL(SQL);
        SQL =   "create table asteroids " +
                "(" +
                "   id integer not null primary key," +
                "   name text not null," +
                "   image text not null," +
                "   imageWidth integer not null," +
                "   imageHeight integer not null," +
                "   type text not null" +
                ");";
        db.execSQL(SQL);


        SQL =   "drop table if exists levels;" ;
        db.execSQL(SQL);
        SQL =   "create table levels" +
                "(" +
                "   id integer not null primary key," +
                "   number integer not null," +
                "   title text not null," +
                "   hint text not null," +
                "   width integer not null," +
                "   height integer not null," +
                "   music text not null" +
                ");" ;
        db.execSQL(SQL);


        SQL =   "drop table if exists level_objects;" ;
        db.execSQL(SQL);
        SQL =   "create table level_objects" +
                "(" +
                "   id integer not null primary key," +
                "   position text not null," +
                "   objectId integer not null," +
                "   scale real not null," +
                "   levelId integer not null" +
                ");" ;
        db.execSQL(SQL);


        SQL =   "drop table if exists level_asteroids;" ;
        db.execSQL(SQL);
        SQL =   "create table level_asteroids" +
                "(" +
                "   id integer not null primary key," +
                "   number integer not null," +
                "   asteroidId integer not null," +
                "   levelId integer not null" +
                ");" ;
        db.execSQL(SQL);


        SQL =   "drop table if exists main_bodies;" ;
        db.execSQL(SQL);
        SQL =   "create table main_bodies" +
                "(" +
                "   id integer not null primary key," +
                "   cannonAttach text not null," +
                "   engineAttach text not null," +
                "   extraAttach text not null," +
                "   image text not null," +
                "   imageWidth integer not null," +
                "   imageHeight integer not null" +
                ");" ;
        db.execSQL(SQL);


        SQL =   "drop table if exists cannons;" ;
        db.execSQL(SQL);
        SQL =   "create table cannons" +
                "(" +
                "   id integer not null primary key," +
                "   attachPoint text not null," +
                "   emitPoint text not null," +
                "   image text not null," +
                "   imageWidth integer not null," +
                "   imageHeight integer not null," +
                "   attackImage text not null," +
                "   attackImageWidth integer not null," +
                "   attackImageHeight integer not null," +
                "   attackSound text not null," +
                "   damage integer" +
                ");" ;
        db.execSQL(SQL);


        SQL =   "drop table if exists extra_parts;" ;
        db.execSQL(SQL);
        SQL =   "create table extra_parts" +
                "(" +
                "   id integer not null primary key," +
                "   attachPoint text not null," +
                "   image text not null," +
                "   imageWidth integer not null," +
                "   imageHeight integer not null" +
                ");" ;
        db.execSQL(SQL);


        SQL =   "drop table if exists engines;" ;
        db.execSQL(SQL);
        SQL =   "create table engines" +
                "(" +
                "   id integer not null primary key," +
                "   baseSpeed integer not null," +
                "   baseTurnRate integer not null," +
                "   attachPoint text not null," +
                "   image text not null," +
                "   imageWidth integer not null," +
                "   imageHeight integer not null" +
                ");" ;
        db.execSQL(SQL);


        SQL =   "drop table if exists power_cores;" ;
        db.execSQL(SQL);
        SQL =   "create table power_cores" +
                "(" +
                "   id integer not null primary key," +
                "   cannonBoost integer not null," +
                "   engineBoost integer not null," +
                "   image text not null" +
                ");";
        db.execSQL(SQL);
    }

    /** What happens when a new version of the database is added */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }
}
