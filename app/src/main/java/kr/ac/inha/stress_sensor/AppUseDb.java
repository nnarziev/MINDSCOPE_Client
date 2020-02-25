package kr.ac.inha.stress_sensor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class AppUseDb {
    static private SQLiteDatabase db;
    private static final String TAG = "AppUseDb";

    static void init(Context context) {
        db = context.openOrCreateDatabase(context.getPackageName(), Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists AppUse(id integer primary key autoincrement, package_name varchar(256), start_timestamp bigint, end_timestamp bigint, total_time_in_foreground bigint);");
    }

    static private List<AppUsageRecord> getOverlappingRecords(String packageName, long startTimestamp, long endTimestamp) {
        List<AppUsageRecord> res = new ArrayList<>();
        Cursor cursor = db.rawQuery(String.format(
                Locale.getDefault(),
                "select * from AppUse where package_name=? and (start_timestamp=%d or end_timestamp=%d or (start_timestamp < %d and %d < end_timestamp) or (start_timestamp < %d and %d < end_timestamp) or (start_timestamp < %d and %d < end_timestamp));",
                startTimestamp,
                endTimestamp,
                startTimestamp,
                startTimestamp,
                endTimestamp,
                endTimestamp,
                startTimestamp,
                endTimestamp
                ),
                new String[]{packageName});
        if (cursor.moveToFirst())
            do {
                res.add(new AppUsageRecord(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getLong(2),
                        cursor.getLong(3),
                        cursor.getLong(4)
                ));
            } while (cursor.moveToNext());
        cursor.close();
        return res;
    }

    static private AppUsageRecord getLastRecord(String packageName) {
        Cursor cursor = db.rawQuery("select * from AppUse where package_name=? order by end_timestamp desc limit(1);", new String[]{packageName});

        if (cursor.moveToFirst()) {
            AppUsageRecord res = new AppUsageRecord(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getLong(3),
                    cursor.getLong(4)
            );
            cursor.close();
            return res;
        } else {
            cursor.close();
            return null;
        }
    }

    static private boolean isUniqueRecord(String packageName, long startTimestamp) {
        Cursor cursor = db.rawQuery(String.format(
                Locale.getDefault(),
                "select exists(select 1 from AppUse where package_name=? and start_timestamp=%d)",
                startTimestamp
        ), new String[]{packageName});
        boolean res = cursor.moveToFirst() && cursor.getInt(0) <= 0;
        cursor.close();
        return res;
    }

    static void saveAppUsageStat(String packageName, long endTimestamp, long totalTimeInForeground) {
        AppUsageRecord lastRecord = getLastRecord(packageName);
        if (lastRecord == null){
            Log.e(TAG, "saveAppUsageStat: Inserted 1 -> " + packageName + "; " + totalTimeInForeground);
            db.execSQL(String.format(
                    Locale.getDefault(),
                    "insert into AppUse(package_name, start_timestamp, end_timestamp, total_time_in_foreground) values(?, %d, %d, %d);",
                    endTimestamp - totalTimeInForeground,
                    endTimestamp,
                    totalTimeInForeground
            ), new String[]{packageName});
        }
        else {
            // the interesting part
            long startTimestamp = endTimestamp - (totalTimeInForeground - lastRecord.totalTimeInForeground);
            if (startTimestamp != endTimestamp) {
                if (startTimestamp == lastRecord.endTimestamp)
                    db.execSQL(String.format(
                            Locale.getDefault(),
                            "update AppUse set end_timestamp = %d and total_time_in_foreground = %d where id=%d;",
                            endTimestamp,
                            totalTimeInForeground,
                            lastRecord.id
                    ), new String[0]);
                else if (isUniqueRecord(packageName, startTimestamp)) {
                    List<AppUsageRecord> overlappingElements = getOverlappingRecords(packageName, startTimestamp, endTimestamp);
                    if (overlappingElements.isEmpty()){
                        Log.e(TAG, "saveAppUsageStat: Inserted 2 -> " + packageName + "; " + totalTimeInForeground);
                        db.execSQL(String.format(
                                Locale.getDefault(),
                                "insert into AppUse(package_name, start_timestamp, end_timestamp, total_time_in_foreground) values(?, %d, %d, %d);",
                                startTimestamp,
                                endTimestamp,
                                totalTimeInForeground
                        ), new String[]{packageName});
                    }
                    else {
                        long minStartTimestamp = startTimestamp;
                        long maxEndTimestamp = endTimestamp;
                        long maxTotalTimeInForeground = totalTimeInForeground;
                        for (AppUsageRecord appUse : overlappingElements) {
                            if (appUse.startTimestamp < minStartTimestamp)
                                minStartTimestamp = appUse.startTimestamp;
                            if (appUse.endTimestamp > maxEndTimestamp)
                                maxEndTimestamp = appUse.endTimestamp;
                            if (appUse.totalTimeInForeground > maxTotalTimeInForeground)
                                maxTotalTimeInForeground = appUse.totalTimeInForeground;

                            db.execSQL(String.format(
                                    Locale.getDefault(),
                                    "delete from AppUse where id=%d;",
                                    appUse.id
                            ));
                        }
                        Log.e(TAG, "saveAppUsageStat: Inserted 3 -> " + packageName + "; " + totalTimeInForeground);
                        db.execSQL(String.format(Locale.getDefault(),
                                "insert into AppUse(package_name, start_timestamp, end_timestamp, total_time_in_foreground) values(?,%d,%d,%d);",
                                minStartTimestamp,
                                maxEndTimestamp,
                                maxTotalTimeInForeground
                        ), new String[]{packageName});
                    }
                }
            }
        }
    }

    static private class AppUsageRecord {
        AppUsageRecord(int id, String packageName, long startTimestamp, long endTimestamp, long totalTimeInForeground) {
            this.id = id;
            this.packageName = packageName;
            this.startTimestamp = startTimestamp;
            this.endTimestamp = endTimestamp;
            this.totalTimeInForeground = totalTimeInForeground;
        }

        int id;
        String packageName;
        long startTimestamp;
        long endTimestamp;
        long totalTimeInForeground;
    }
}
