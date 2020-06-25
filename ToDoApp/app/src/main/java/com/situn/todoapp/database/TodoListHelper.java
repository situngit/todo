package com.situn.todoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.situn.todoapp.activity.AddTodoActivity;
import com.situn.todoapp.model.TodoModel;

import java.util.ArrayList;
import java.util.List;

import static com.situn.todoapp.database.TodoListContract.COLUMN_NAME_CHECK;
import static com.situn.todoapp.database.TodoListContract.COLUMN_NAME_DESC;
import static com.situn.todoapp.database.TodoListContract.COLUMN_NAME_ID;
import static com.situn.todoapp.database.TodoListContract.COLUMN_NAME_TITLE;
import static com.situn.todoapp.database.TodoListContract.TABLE_NAME;


public class TodoListHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";

    private static  final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( " +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY ," +
            COLUMN_NAME_TITLE + " TEXT , " +
            COLUMN_NAME_DESC + " TEXT , " +
            COLUMN_NAME_CHECK + " TEXT " + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String FETCH_RECORD = "SELECT * FROM " + TABLE_NAME;


    public TodoListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public List<TodoModel> fetchDataFromDb() {
        ArrayList<TodoModel> todoModelArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        Cursor res = null;
        try {
            sqLiteDatabase = this.getReadableDatabase();
            res = sqLiteDatabase.rawQuery(TodoListHelper.FETCH_RECORD, null);
            res.moveToFirst();
            TodoModel assignedModel;
            while (!res.isAfterLast()) {
                assignedModel = new TodoModel(
                        res.getString(res.getColumnIndex(COLUMN_NAME_TITLE)),
                        res.getString(res.getColumnIndex(COLUMN_NAME_DESC)),
                        res.getString(res.getColumnIndex(COLUMN_NAME_CHECK)));

                todoModelArrayList .add(assignedModel);
                res.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (res != null)
                res.close();
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
                sqLiteDatabase = null;
                res = null;
            }
        }
        return todoModelArrayList;

    }

    public void insertNewTodoToDb(AddTodoActivity context, String title, String description, String checked) {
        TodoListHelper todoListHelper = new TodoListHelper(context);
        SQLiteDatabase db = todoListHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, title);
        values.put(COLUMN_NAME_DESC,  description);
        values.put(COLUMN_NAME_CHECK,checked);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }



    public void updateTodo(TodoModel todoModel, int position)
    {
        SQLiteDatabase updateDb = this.getReadableDatabase();;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CHECK,  todoModel.getChecktodo());
        updateDb.update(TABLE_NAME, contentValues,fetchDataFromDb().get(position).getTitle() + " = " + todoModel.getTitle(), null);
        updateDb.close();
    }

}
