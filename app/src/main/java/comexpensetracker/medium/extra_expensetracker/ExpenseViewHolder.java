package comexpensetracker.medium.extra_expensetracker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by akash on 15/7/17.
 */

public class ExpenseViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkbox;
    TextView description;

    public ToDoViewHolder(View itemView) {
        super(itemView);

        description = (TextView) itemView.findViewById(R.id.description);
        checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
    }

}
