package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by akash on 15/7/17.
 */

public class AddExpenseReturingResponse {

    @SerializedName("affected_rows")
    Integer affectedRows;

    @SerializedName("returning")
    List<AddexpenseRecord> addexpenseRecords;

    public Integer getAffectedRows() {
        return affectedRows;
    }

    public List<AddexpenseRecord> getAddexpenseRecords() {
        return addexpenseRecords;
    }

}
