package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName



/**
 * Created by akash on 23/7/17.
 */
class SelectExpenseRequest {

    @SerializedName("type")
    var type = "select"

    @SerializedName("args")
    var args: Args? = null

    fun SelectExpenseRequest(userId: Int?) {
        args = Args()
        (args as Args).where = Where()
        (args as Args).where!!.userId = userId
    }

    inner class Args {

        @SerializedName("table")
        var table = "expense"

        @SerializedName("columns")
        var columns = arrayOf("exp_id", "exp_name", "exp_amt", "exp_created", "exp_note", "exp_tag", "exp_category")

        @SerializedName("where")
        var where: Where? = null

    }

    inner class Where {
        @SerializedName("user_id")
        var userId: Int? = null
    }

}
