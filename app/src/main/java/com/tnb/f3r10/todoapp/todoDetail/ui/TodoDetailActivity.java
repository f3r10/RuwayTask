package com.tnb.f3r10.todoapp.todoDetail.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnb.f3r10.todoapp.R;
import com.tnb.f3r10.todoapp.addTodo.ui.AddTodoFragment;
import com.tnb.f3r10.todoapp.model.Todo;
import com.tnb.f3r10.todoapp.todoDetail.TodoDetailPresenter;
import com.tnb.f3r10.todoapp.todoDetail.TodoDetailPresenterImpl;
import com.tnb.f3r10.todoapp.todoList.ui.MainActivity;
import com.tnb.f3r10.todoapp.todoList.ui.adapter.DialogFragmentCloseListener;

import org.parceler.Parcels;

import java.text.DateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoDetailActivity extends AppCompatActivity implements DialogFragmentCloseListener {

    @Bind(R.id.imgColorPriority)
    ImageView imgColorPriority;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.txtDueDate)
    TextView txtDueDate;
    @Bind(R.id.txtNotes)
    TextView txtNotes;
    @Bind(R.id.txtPriority)
    TextView txtPriority;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    Todo todoForView;
    int colorPriority;

    TodoDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        ButterKnife.bind(this);
        todoForView = Parcels.unwrap(getIntent().getParcelableExtra("TODO_KEY"));
        setUpView(todoForView);
        setToolbar();
        presenter = new TodoDetailPresenterImpl();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todotask_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_delete){
            showDialogConfirm(todoForView);

        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpView(Todo todo) {
        if ( todo !=null ){
            collapsing.setTitle(todo.getTodoName());
            colorPriority = Color.RED;
            String priorityText = "HIGH";
            String statusText = todo.getStatus() ? "COMPLETED" : "TO-DO";
            String formatDate = DateFormat.getDateInstance().format(todo.getTodoDate());
            switch (todo.getPriority()){
                case Todo.HIGH:
                    colorPriority = Color.RED;
                    priorityText = "HIGH";
                    break;
                case Todo.MEDIUM:
                    colorPriority = Color.GREEN;
                    priorityText = "MEDIUM";
                    break;
                case Todo.LOW:
                    colorPriority = Color.BLUE;
                    priorityText = "LOW";
            }
            //imgColorPriority.setBackgroundColor(colorPriority);
            txtDueDate.setText(formatDate);
            txtNotes.setText(todo.getNotes());
            txtPriority.setText(priorityText);
            txtStatus.setText(statusText);

        }
    }

    private void setToolbar() {
        //toolbar.setBackgroundColor(colorPriority);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void showDialogConfirm(final Todo todo){
        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteTask(todo.getId());
                        nagivateToMainActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void nagivateToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                |intent.FLAG_ACTIVITY_NEW_TASK
                |intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void editTask(){
        if(todoForView !=null){
            Bundle args = new Bundle();
            AddTodoFragment addTodoFragment = new AddTodoFragment();
            args.putParcelable("TODO_KEY", Parcels.wrap(todoForView));
            addTodoFragment.setArguments(args);
            addTodoFragment.show(getSupportFragmentManager(), getString(R.string.addTodo_message_title));
        }
    }

    @Override
    public void addTodoActionFinish() {

    }

    @Override
    public void updateTodoActionFinish(Todo todo) {
        setUpView(todo);
    }
}
