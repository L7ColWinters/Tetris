package labs.brian.database;

import java.sql.SQLException;

import labs.brian.beans.HighScoreInfo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "tetrisHighScores.db";
	private static final int DATABASE_VERSION = 1;
	
	private Dao<HighScoreInfo, Integer> simpleDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
		try {
			TableUtils.createTable(connectionSource, HighScoreInfo.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion,
			int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, HighScoreInfo.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
		}
	}
	
	/**
	 * Returns the Database Access Object (DAO) for our HighScoreInfo class. It will create it or just give the cached
	 * value.
	 */
	public Dao<HighScoreInfo, Integer> getSimpleDataDao() throws SQLException {
		if (simpleDao == null) {
			simpleDao = getDao(HighScoreInfo.class);
		}
		return simpleDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		simpleDao = null;
	}
}
