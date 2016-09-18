package com.tnb.f3r10.todoapp.todoList.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.tnb.f3r10.todoapp.R;
import com.tnb.f3r10.todoapp.addTodo.AddTodoFragment;
import com.tnb.f3r10.todoapp.model.Todo;
import com.tnb.f3r10.todoapp.todoList.ui.adapter.DialogFragmentCloseListener;
import com.tnb.f3r10.todoapp.todoList.ui.adapter.OnItemClickListener;
import com.tnb.f3r10.todoapp.todoList.TodoListPresenter;
import com.tnb.f3r10.todoapp.todoList.TodoListPresenterImpl;
import com.tnb.f3r10.todoapp.todoList.ui.adapter.TodoListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, TodoListView, DialogFragmentCloseListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewTodos)
    RecyclerView recyclerViewTodos;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.container)
    CoordinatorLayout container;
    private TodoListAdapter adapter;
    private TodoListPresenter todoListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();
        setUpAdapter();
        setUpRecyclerView();

        todoListPresenter = new TodoListPresenterImpl(this);
        todoListPresenter.onCreate();

        todoListPresenter.getTodos();

    }



    private void setUpRecyclerView() {
        recyclerViewTodos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTodos.setAdapter(adapter);
    }

    private void setUpAdapter() {
        adapter = new TodoListAdapter(new ArrayList<Todo>(), this);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
    }


    @Override
    public void onItemClick(Todo todo) {
        Bundle args = new Bundle();
        AddTodoFragment addTodoFragment = new AddTodoFragment();
        args.putString("ID" , todo.getId());
        args.putString("TITLE" , todo.getTodoName());
        addTodoFragment.setArguments(args);
        addTodoFragment.show(getSupportFragmentManager(), getString(R.string.addTodo_message_title));
    }

    @Override
    public void onItemLongClick(Todo todo) {
        showDialogConfirm(todo);
    }


    @OnClick(R.id.fab)
    public void addTodo() {
        new AddTodoFragment().show(getSupportFragmentManager(), getString(R.string.addTodo_message_title));
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        //Snackbar.make(container, msg, Snackbar.LENGTH_INDEFINITE).show();
        todoListPresenter.getTodos();
        showSnackBar(msg);
    }

    @Override
    public void setTodos(List<Todo> data) {
        adapter.setTodos(data);
    }

    private void showSnackBar(String msg) {
        Snackbar.make(container, msg, Snackbar.LENGTH_LONG).show();
    }

    private void showDialogConfirm(final Todo todo){
        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        todoListPresenter.removedTodo(todo);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void addTodoActionFinish() {
        todoListPresenter.getTodos();
    }
}
