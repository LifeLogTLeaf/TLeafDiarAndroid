package com.tleaf.tiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * �ۼ���: �ֽ���
 * ���: ���� db�� �ʱ�ȭ �ϴ� Ŭ�����̴�. 
 * 
 * SQLiteOpenHelper�� ��ӹ��� Ŭ�����̴�.
 * �⺻ ���̺��� �����ϰ� �ʱ� ���̵����͸� �ִ´�.
 */

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context) {
		super(context, "DiaryDb.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		String sql = "create table diary (diaryno integer primary key autoincrement, " +
//				"isbn text, " +
//				"title text, " +
//				"author text, " +
//				"publisher text, " +
//				"reprice text, " +
//				"image text, " +
//				"regDate text, " +
//				"price text, " +
//				"rating text, " +
//				"univ text, " +
//				"major text, " +
//				"lecture text, " +
//				"professor text, " +
//				"usedYear text, " +
//				"usedTerm text, " +
//				"daelLocation text)";

//		String insertSql1 = "insert into salebook values (" +
//				"null, " +
//				"'123456789', " +
//				"'�̻����', " +
//				"'�����', " +
//				"'������', " +
//				"'30000', " +
//				"null, " +
//				"2013/12/5, " +
//				"'3000', " +
//				"'��', " +
//				"'��������', " +
//				"'��ǻ���а�', " +
//				"'�̻����', " +
//				"'������', " +
//				"'2011', " +
//				"'1', " +
//				"'���')";
//
//		String insertSql2 = "insert into salebook values (" +
//				"null, " +
//				"'123456789101112', " +
//				"'�ȵ���̵� �����ϱ�', " +
//				"'�����', " +
//				"'������', " +
//				"'30000', " +
//				"null, " +
//				"2013/12/5, " +
//				"'3000', " +
//				"'��', " +
//				"'��������', " +
//				"'��ǻ���а�', " +
//				"'�̻����', " +
//				"'������', " +
//				"'2011', " +
//				"'1', " +
//				"'���')";
//
//		db.execSQL(sql);
//		db.execSQL(insertSql1);
//		db.execSQL(insertSql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
