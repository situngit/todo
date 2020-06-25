package com.situn.todoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.situn.todoapp.R;
import com.situn.todoapp.database.TodoListHelper;
import com.situn.todoapp.databinding.ActivityAddTodoBinding;


public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddTodoBinding activityAddTodoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        activityAddTodoBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_todo);
        listeners();
    }

    private void listeners() {
        activityAddTodoBinding.btnCancel.setOnClickListener(this);
        activityAddTodoBinding.btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_done:
                if (validate())
                    insertNewTodo();
                break;
        }
    }

    //Validation check for entry
    private boolean validate() {
        if (activityAddTodoBinding.etTitle.getText().toString().trim().isEmpty())
        {
            activityAddTodoBinding.etTitle.setError(getString(R.string.enter_title));
            return false;
        }
        else if (activityAddTodoBinding.etDesc.getText().toString().trim().isEmpty())
        {
            activityAddTodoBinding.etDesc.setError(getString(R.string.enter_desc));
            return false;
        }
        return true;
    }

    //InsertNewtodo
    private void insertNewTodo() {
        TodoListHelper todoListHelper = new TodoListHelper(AddTodoActivity.this);
        todoListHelper.insertNewTodoToDb(AddTodoActivity.this,activityAddTodoBinding.etTitle.getText().toString().trim(),
                activityAddTodoBinding.etDesc.getText().toString().trim(), "0");
        finish();
    }

}