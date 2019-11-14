package com.tq.bluetoothdemo;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public final class NoticeDao_Impl implements NoticeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfNotice;

  public NoticeDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNotice = new EntityInsertionAdapter<Notice>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `notice`(`id`,`time`,`text`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Notice value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        stmt.bindLong(2, value.getTime());
        if (value.getText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getText());
        }
      }
    };
  }

  @Override
  public void insertNotice(final Notice notice) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfNotice.insert(notice);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Notice>> getNotices() {
    final String _sql = "SELECT id,time,text FROM notice";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"notice"}, new Callable<List<Notice>>() {
      @Override
      public List<Notice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final List<Notice> _result = new ArrayList<Notice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Notice _item;
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            final long _tmpTime;
            _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            _item = new Notice(_tmpId,_tmpTime,_tmpText);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
