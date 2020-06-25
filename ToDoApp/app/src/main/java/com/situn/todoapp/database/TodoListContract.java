package com.situn.todoapp.database;

import android.provider.BaseColumns;

public class TodoListContract implements BaseColumns{
        public static final String TABLE_NAME = "Todo";
        public static final String COLUMN_NAME_ID = "Id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESC= "description";
        public static String COLUMN_NAME_CHECK= "checked";
}
