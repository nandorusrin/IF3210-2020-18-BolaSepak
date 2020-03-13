package com.example.sepakbola;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    private static  final int DATABASE_VERSION = 2;
    private  static  final String DATABASE_NAME = "scheduleDB";
    private  static  final String TABLE_NAME = "schedule";
    private  static  final String KEY_ID = "id";
    private  static  final String KEY_HOME = "home";
    private  static  final String KEY_AWAY = "away";
    private  static  final String KEY_HOMESCORE = "homeScore";
    private  static  final String KEY_AWAYSCORE = "awayScore";
    private  static  final String KEY_DATE = "date";
    private  static  final String KEY_GOALHOMEDETAILS = "goalHomeDetails";
    private  static  final String KEY_GOALAWAYDETAILS = "goalAwayDetails";
    private  static  final String KEY_HOMETEAMID = "homeTeamId";
    private  static  final String[] COLUMNS = {KEY_ID, KEY_HOME, KEY_AWAY, KEY_HOMESCORE, KEY_AWAYSCORE, KEY_DATE,   KEY_GOALHOMEDETAILS, KEY_GOALAWAYDETAILS, KEY_HOMETEAMID};

    public SQLiteDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATION_TABLE = "CREATE TABLE Schedule ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "home TEXT, "
                + "away TEXT, " + "homeScore INTEGER, " + "awayScore  INTEGER, " + "date TEXT, " + "goalHomeDetails TEXT, " + "goalAwayDetails TEXT, " + "homeTeamId TEXT)";
        db.execSQL(CREATION_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void insertData(Schedule schedule){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_AWAY, schedule.getAway());
        contentValues.put(KEY_AWAYSCORE, schedule.getAwayScore());
        contentValues.put(KEY_DATE, schedule.getDate());
        contentValues.put(KEY_HOME, schedule.getHome());
        contentValues.put(KEY_HOMESCORE, schedule.getHomeScore());
        contentValues.put(KEY_GOALHOMEDETAILS, schedule.getGoalHomeDetails());
        contentValues.put(KEY_GOALAWAYDETAILS, schedule.getGoalAwayDetails());
        contentValues.put(KEY_HOMETEAMID, schedule.getHomeTeamId());
        db.insert(TABLE_NAME, null,contentValues);
        db.close();
    }

    public List<Schedule> allSchedule(){
        List<Schedule> schedules = new LinkedList<>();
        String q = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q,null);
        Schedule schedule = null;

        if (cursor.moveToFirst()){
            do{
                schedule = new Schedule(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
                schedules.add(schedule);
            }while(cursor.moveToNext());
        }
        return schedules;
    }
}
