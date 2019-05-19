package com.blabla.structurize.TaskPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blabla.structurize.CategoryPackage.Category;
import com.blabla.structurize.DataBaseHandler;
import com.blabla.structurize.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    public static final String EXTRA_TASK_ID = "TASK_ID";
    private Context context;
    private DataBaseHandler dataBaseHandler;
    private List<Task> tasks;

    public TaskAdapter(Context context) {
        this.context = context;
        this.dataBaseHandler = new DataBaseHandler(context);
        this.tasks = this.dataBaseHandler.getAllTasks();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_task_layout,viewGroup,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder viewHolder, int i) {
        viewHolder.bind(this.tasks.get(i));
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView textViewName;
        private TextView textViewDateFirst;
        private TextView textViewDateSecond;
        private TextView textViewCategoryName;
        private ImageView imageViewLine;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.recycler_item_task_layout_linear_layout);
            textViewName = itemView.findViewById(R.id.recycler_item_task_layout_edit_text_name);
            textViewDateFirst = itemView.findViewById(R.id.recycler_item_task_layout_edit_text_date_first);
            textViewDateSecond = itemView.findViewById(R.id.recycler_item_task_layout_edit_text_date_second);
            textViewCategoryName = itemView.findViewById(R.id.recycler_item_task_layout_edit_text_name_category);
            imageViewLine = itemView.findViewById(R.id.recycler_item_task_layout_image_view_line);
        }

        public void bind(final Task task){
            this.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),TaskActivity.class);
                    intent.putExtra(EXTRA_TASK_ID,task.getId());
                    context.startActivity(intent);
                }
            });
            this.textViewName.setText(task.getName());
            String date = task.getDate();
            String[] str = new String[3];
            if(date.contains("-")) {
                str = date.split("-");
                this.textViewDateFirst.setText(str[0] + "." + str[1] + ".");
            }
            if(date.contains(".")) {
                str = date.split(".");
                this.textViewDateFirst.setText(str[0] + "." + str[1] + ".");
            }
            this.textViewDateSecond.setText(str[2]);
            DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
            Category category = dataBaseHandler.getCategoryById(task.getIdCategory());
            this.textViewCategoryName.setText(category.getName());
            this.imageViewLine.setBackgroundColor(Color.parseColor(category.getColor()));
        }


    }
}
