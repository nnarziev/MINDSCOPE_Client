package kr.ac.inha.stress_sensor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbMgr {
    private static SQLiteDatabase db;
    static Context con;

    public static void init(Context context) {
        con = context;
        db = context.openOrCreateDatabase(context.getPackageName(), Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists Data(id integer primary key autoincrement, dataSourceId int default(0), timestamp bigint default(0), accuracy float default(0.0), data varchar(512) default(null));");
    }

    public static void saveNumericData(int sensorId, long timestamp, float accuracy, float[] data) {
        StringBuilder sb = new StringBuilder();
        for (float value : data)
            sb.append(value).append(',');
        if (sb.length() > 0)
            sb.replace(sb.length() - 1, sb.length(), "");
        saveStringData(sensorId, timestamp, accuracy, sb.toString());
    }

    public static void saveMixedData(int sensorId, long timestamp, float accuracy, Object... params) {
        assert sensorId != 0;
        StringBuilder sb = new StringBuilder();
        for (Object value : params)
            sb.append(value).append(Tools.DATA_SOURCE_SEPARATOR);
        if (sb.length() > 0)
            sb.replace(sb.length() - 1, sb.length(), "");
        saveStringData(sensorId, timestamp, accuracy, sb.toString());
        //String res = sensorId + "," + timestamp + "," + sb.toString();
        //Log.e("saveMixedData", "Insertion: " + res);
    }

    private static void saveStringData(int dataSourceId, long timestamp, float accuracy, String data) {
        db.execSQL("insert into Data(dataSourceId, timestamp, accuracy, data) values(?, ?, ?, ?);", new Object[]{
                dataSourceId,
                timestamp,
                accuracy,
                data
        });

        /*String res = dataSourceId + "," + timestamp + "," + data;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.openFileOutput("data.csv", Context.MODE_PRIVATE));
            outputStreamWriter.write(res);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }*/
    }

    public static synchronized void cleanDb() {
        db.execSQL("delete from Data;");
    }

    public static int countSamples() {
        Cursor cursor = db.rawQuery("select count(*) from Data;", new String[0]);
        int result = 0;
        if (cursor.moveToFirst())
            result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    public static Cursor getSensorData() {
        return db.rawQuery("select * from Data;", new String[0]);
    }

    public static void deleteRecord(int id) {
        db.execSQL("delete from Data where id=?;", new Object[]{id});
    }
}