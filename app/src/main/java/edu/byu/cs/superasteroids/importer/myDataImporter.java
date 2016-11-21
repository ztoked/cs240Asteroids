package edu.byu.cs.superasteroids.importer;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import edu.byu.cs.superasteroids.core.AsteroidsGame;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.Background;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.ImageObject;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.LevelAsteroid;
import edu.byu.cs.superasteroids.model.LevelObject;
import edu.byu.cs.superasteroids.model.Point;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.ShipBody;

/**
 * Created by zachhalvorsen on 2/11/16.
 */
public class myDataImporter implements IGameDataImporter
{
    Database db;

    private Context c;

    public myDataImporter(Context c)
    {
        this.c = c;
        db = AsteroidsGame.getInstance().getDb();
    }

    //dataInputReader is the JSON file
    @Override
    public boolean importData(InputStreamReader dataInputReader)
    {
        Toast toast = Toast.makeText(c,"Importing Data...",Toast.LENGTH_LONG);
        toast.show();
        try
        {
            db.startTransaction();
            db.onCreate();
            JSONObject rootObj = new JSONObject(makeString(dataInputReader));
            JSONObject asteroidsGameObj = rootObj.getJSONObject("asteroidsGame");
            JSONArray objectsArray = asteroidsGameObj.getJSONArray("objects");
            for(int i = 0; i < objectsArray.length(); i++)
            {
                String address = objectsArray.getString(i);
                Background BG = new Background(address,i);
                db.backgroundDAO.addBackground(BG);
            }

            JSONArray asteroidsArray = asteroidsGameObj.getJSONArray("asteroids");
            for(int i = 0; i < asteroidsArray.length(); i++)
            {
                JSONObject elemObj = asteroidsArray.getJSONObject(i);

                String name = elemObj.getString("name");
                String address = elemObj.getString("image");
                int imageWidth = elemObj.getInt("imageWidth");
                int imageHeight = elemObj.getInt("imageHeight");
                String type = elemObj.getString("type");
                Asteroid a = new Asteroid(new ImageObject(address,imageWidth,imageHeight),
                        i, new Point(-1, -1), -1, -1, -1, name, type, -1);
                db.asteroidsDAO.addAsteroid(a);
            }

            JSONArray levelsArray = asteroidsGameObj.getJSONArray("levels");
            for(int i = 0; i < levelsArray.length(); i++)
            {
                JSONObject elemObj = levelsArray.getJSONObject(i);

                int number = elemObj.getInt("number");
                String title = elemObj.getString("title");
                String hint = elemObj.getString("hint");
                int width = elemObj.getInt("width");
                int height = elemObj.getInt("height");
                String music = elemObj.getString("music");
                //levelObjects
                JSONArray levelObjectsArray = elemObj.getJSONArray("levelObjects");
                for(int j = 0; j < levelObjectsArray.length(); j++)
                {
                    JSONObject LOelem = levelObjectsArray.getJSONObject(j);

                    String position = LOelem.getString("position");
                    int objectID = LOelem.getInt("objectId");
                    double scale = LOelem.getDouble("scale");

                    LevelObject lo = new LevelObject(new Point(position), objectID, scale, number);
                    db.levelObjectDAO.addLevelObject(lo);
                }
                //levelAsteroids
                JSONArray levelAsteroidsArray = elemObj.getJSONArray("levelAsteroids");
                for(int j = 0; j < levelAsteroidsArray.length(); j++)
                {
                    JSONObject LAelem = levelAsteroidsArray.getJSONObject(j);

                    int num = LAelem.getInt("number");
                    int asteroidID = LAelem.getInt("asteroidId");

                    LevelAsteroid la = new LevelAsteroid(num, asteroidID, number);
                    db.levelAsteroidsDAO.addLevelAsteroid(la);
                }

                Level l = new Level(i, number, title, hint, width, height, music);
                db.levelDAO.addLevel(l);
            }

            JSONArray bodiesArray = asteroidsGameObj.getJSONArray("mainBodies");
            for(int i = 0; i < bodiesArray.length(); i++)
            {
                JSONObject elemObj = bodiesArray.getJSONObject(i);

                String cannonAttach = elemObj.getString("cannonAttach");
                String engineAttach = elemObj.getString("engineAttach");
                String extraAttach = elemObj.getString("extraAttach");
                String image = elemObj.getString("image");
                int imageWidth = elemObj.getInt("imageWidth");
                int imageHeight = elemObj.getInt("imageHeight");

                ShipBody sb = new ShipBody(new ImageObject(image,imageWidth,imageHeight),
                        i, new Point(cannonAttach), new Point(engineAttach), new Point(extraAttach));
                db.shipBodyDAO.addShipBody(sb);
            }

            JSONArray cannonsArray = asteroidsGameObj.getJSONArray("cannons");
            for(int i = 0; i < cannonsArray.length(); i++)
            {
                JSONObject elemObj = cannonsArray.getJSONObject(i);

                String attachPoint = elemObj.getString("attachPoint");
                String emitPoint = elemObj.getString("emitPoint");
                String image = elemObj.getString("image");
                int imageWidth = elemObj.getInt("imageWidth");
                int imageHeight = elemObj.getInt("imageHeight");
                String attackImage = elemObj.getString("attackImage");
                int attackImageWidth = elemObj.getInt("attackImageWidth");
                int attackImageHeight = elemObj.getInt("attackImageHeight");
                String attackSound = elemObj.getString("attackSound");
                int damage = elemObj.getInt("damage");

                Cannon c = new Cannon(new ImageObject(image,imageWidth,imageHeight), i,
                        new Point(attachPoint), new Point(emitPoint),
                        new ImageObject(attackImage, attackImageWidth, attackImageHeight),
                        attackSound, damage);
                db.cannonDAO.addCannon(c);
            }

            JSONArray extrasArray = asteroidsGameObj.getJSONArray("extraParts");
            for(int i = 0; i < extrasArray.length(); i++)
            {
                JSONObject elemObj = extrasArray.getJSONObject(i);

                String attachPoint = elemObj.getString("attachPoint");
                String image = elemObj.getString("image");
                int imageWidth = elemObj.getInt("imageWidth");
                int imageHeight = elemObj.getInt("imageHeight");

                ExtraPart ep = new ExtraPart(new ImageObject(image,imageWidth,imageHeight),
                        i, new Point(attachPoint));
                db.extraPartDAO.addExtraPart(ep);
            }

            JSONArray enginesArray = asteroidsGameObj.getJSONArray("engines");
            for(int i = 0; i < enginesArray.length(); i++)
            {
                JSONObject elemObj = enginesArray.getJSONObject(i);

                int baseSpeed = elemObj.getInt("baseSpeed");
                int baseTurnRate = elemObj.getInt("baseTurnRate");
                String attachPoint = elemObj.getString("attachPoint");
                String image = elemObj.getString("image");
                int imageWidth = elemObj.getInt("imageWidth");
                int imageHeight = elemObj.getInt("imageHeight");
                Engine e = new Engine(new ImageObject(image,imageWidth,imageHeight),
                        i, baseSpeed, baseTurnRate, new Point(attachPoint));
                db.engineDAO.addEngine(e);
            }

            JSONArray powerCoresArray = asteroidsGameObj.getJSONArray("powerCores");
            for(int i = 0; i < powerCoresArray.length(); i++)
            {
                JSONObject elemObj = powerCoresArray.getJSONObject(i);

                int cannonBoost = elemObj.getInt("cannonBoost");
                int engineBoost = elemObj.getInt("engineBoost");
                String image = elemObj.getString("image");

                PowerCore pc = new PowerCore(new ImageObject(image), i, cannonBoost, engineBoost);
                db.powerCoreDAO.addPowerCore(pc);
            }

        }
        catch(IOException ioE)
        {
            db.endTransaction(false);
            toast = Toast.makeText(c,"myDataImporter/IO Exception",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        catch(SQLiteConstraintException SQLconstraint)
        {
            db.endTransaction(false);
            toast = Toast.makeText(c,"myDataImporter/SQLite Constraint Exception",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        catch(JSONException jsE)
        {
            db.endTransaction(false);
            toast = Toast.makeText(c,"myDataImporter/JSON Exception",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        db.endTransaction(true);
        return true;
    }


    private static String makeString(InputStreamReader dataInputReader) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        Scanner scan = new Scanner(dataInputReader);
        while(scan.hasNext())
        {
            sb.append(scan.nextLine());
        }
        scan.close();

        return sb.toString();
    }
}
