package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName



/**
 * Created by akash on 23/7/17.
 */
class ExpenseReturingResponse {

    @SerializedName("affected_rows")
    var affectedRows: Int? = null

    @SerializedName("returning")
    var addexpenseRecords: List<ExpenseRecord>? = null

    fun getAffectedRows(): Int? {
        return affectedRows
    }

    fun getAddexpenseRecords(): List<ExpenseRecord>? {
        return addexpenseRecords
    }

}
