package com.situn.todoapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.situn.todoapp.R;
import com.situn.todoapp.interfaces.ClickEventListener;
import com.situn.todoapp.model.TodoModel;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> {

    private List<TodoModel> todoModelList = new ArrayList<>();
    private ClickEventListener clickEventListener;

    public TodoListAdapter(List<TodoModel> todoModelList, ClickEventListener clickEventListener) {
        this.todoModelList = todoModelList;
        this.clickEventListener = clickEventListener;
    }

    @Override
    public TodoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_todo, parent, false);

        return new TodoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoListViewHolder holder, final int listPosition) {
        holder.tvTitle.setText(todoModelList.get(listPosition).getTitle());
        holder.tvDesc.setText(todoModelList.get(listPosition).getDesc());

        if (todoModelList.get(listPosition).getChecktodo().equals("0"))
            holder.cbTodo.setChecked(false);
        else
            holder.cbTodo.setChecked(true);

        holder.cbTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickEventListener.onItemClick(listPosition,isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoModelList.size();
    }


    static class TodoListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDesc;
        private CheckBox cbTodo;

        TodoListViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            cbTodo = (CheckBox) itemView.findViewById(R.id.cb_todo);

        }
    }

}
