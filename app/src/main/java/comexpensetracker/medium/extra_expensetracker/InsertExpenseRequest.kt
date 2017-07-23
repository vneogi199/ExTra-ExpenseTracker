package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


/**
 * Created by akash on 23/7/17.
 */
class InsertExpenseRequest {

    @SerializedName("type")
    var type = "insert"

    @SerializedName("args")
    var args: Args? = null

    fun InsertExpenseRequest(username: String, userId: Int?, exp_amt: Int, exp_created: Timestamp, exp_note: String, exp_tag: String, exp_category: Int) {
        args = Args()
        (args as Args).objects = ArrayList()
        val record = ExpenseRecord(username, userId, exp_amt, exp_created, exp_note, exp_tag, exp_category)
        (args as Args).objects!!.add(record)
    }

    inner class Args {

        @SerializedName("table")
        var table = "expense"

        @SerializedName("returning")
        var returning = arrayOf("exp_id", "exp_name", "exp_amt", "exp_created", "exp_note", "exp_tag", "exp_category")

        @SerializedName("objects")
        var objects: MutableList<ExpenseRecord>? = null

    }

}
