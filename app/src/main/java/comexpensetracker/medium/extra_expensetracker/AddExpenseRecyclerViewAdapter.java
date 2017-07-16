package comexpensetracker.medium.extra_expensetracker;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 15/7/17.
 */

public class AddExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

    List<AddexpenseRecord> data = new ArrayList<>();

    public interface Interactor {
        void onExpenseClicked(int position, AddexpenseRecord record);
        void onExpenseLongClicked(int position, AddexpenseRecord record);
    }

    Interactor interactor;

    public AddExpenseRecyclerViewAdapter(Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_add_expense,parent,false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, final int position) {
        final AddexpenseRecord addexpenseRecord = data.get(position);
        holder.description.setText(addexpenseRecord.getExpName());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                interactor.onExpenseLongClicked(position,addexpenseRecord);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactor.onExpenseClicked(position,addexpenseRecord);
            }
        });

        holder.checkbox.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<AddexpenseRecord> recordList) {
        this.data = recordList;
        notifyDataSetChanged();
    }

    public void deleteData(int position, AddexpenseRecord record) {
        this.data.remove(position);
        notifyDataSetChanged();
    }

    public void updateData(int position, AddexpenseRecord record) {
        this.data.set(position,record);
        notifyDataSetChanged();
    }

    public void addData(AddexpenseRecord record) {
        this.data.add(record);
        notifyDataSetChanged();
    }
}
