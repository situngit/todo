package com.situn.todoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.situn.todoapp.R;
import com.situn.todoapp.adapter.TodoListAdapter;
import com.situn.todoapp.database.TodoListHelper;
import com.situn.todoapp.databinding.ActivityHomeBinding;
import com.situn.todoapp.interfaces.ClickEventListener;
import com.situn.todoapp.model.TodoModel;

import java.util.ArrayList;
import java.util.List;

import static com.situn.todoapp.database.TodoListContract.COLUMN_NAME_CHECK;
import static com.situn.todoapp.database.TodoListContract.COLUMN_NAME_DESC;
import static com.situn.todoapp.database.TodoListContract.COLUMN_NAME_TITLE;
import static com.situn.todoapp.database.TodoListContract.TABLE_NAME;

public class HomeActivity extends AppCompatActivity implements ClickEventListener {

    private ActivityHomeBinding activityHomeBinding;

    private ImageView imgFilter;
    private List<TodoModel> todoList;
    private List<TodoModel> todoSearchList;
    private TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setRecycler();
        searchTodo();
        insertNwTodo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDataFromDb();
    }

    private void setRecycler() {
        activityHomeBinding.rvTodo.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false));
        todoList =new ArrayList< TodoModel >();
        todoSearchList =new ArrayList< TodoModel >();
        adapter = new TodoListAdapter(todoSearchList,this);
        activityHomeBinding.rvTodo.setAdapter(adapter);
    }


    private void fetchDataFromDb() {
        TodoListHelper todoListHelper = new TodoListHelper(HomeActivity.this);
        todoList.clear();
        todoSearchList.clear();
        todoList .addAll(todoListHelper.fetchDataFromDb());
        todoSearchList .addAll(todoListHelper.fetchDataFromDb());
        adapter.notifyDataSetChanged();
    }


    private void searchTodo() {
        activityHomeBinding.etSearchTodo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                todoSearchList.clear();
                if (s.length() > 0)
                {
                    String searchText = s.toString().toLowerCase();
                    for (TodoModel todoModel : todoList) {
                        if (todoModel.getTitle().toLowerCase().contains(searchText) ||
                                todoModel.getDesc().toLowerCase().contains(searchText)) {
                            todoSearchList.add(todoModel);
                        }
                    }

                }
                else {
                    todoSearchList.addAll(todoList);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void insertNwTodo() {
        activityHomeBinding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AddTodoActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onItemClick( int position,boolean checked) {
        TodoModel todoModel;
        if (checked)
            todoModel = new TodoModel(todoList.get(position).getTitle(),todoList.get(position).getDesc(),"1");
        else
            todoModel = new TodoModel(todoList.get(position).getTitle(),todoList.get(position).getDesc(),"0");

        TodoListHelper todoListHelper = new TodoListHelper(HomeActivity.this);
       // todoListHelper.updateTodo(todoModel,position);

        todoSearchList.remove(position);
        todoSearchList.add(todoModel);
    }
}