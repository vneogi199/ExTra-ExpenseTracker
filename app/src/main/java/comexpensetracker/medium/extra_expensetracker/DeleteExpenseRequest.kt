package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName



/**
 * Created by akash on 23/7/17.
 */
class DeleteExpenseRequest {

    @SerializedName("type")
    var type = "delete"

    @SerializedName("args")
    var args: Args = null!!

    fun DeleteExpenseRequest(exp_id: Int?, userId: Int?) {
        args = Args()
        args.where = Where()
        args.where!!.exp_id = exp_id
        args.where!!.userId = userId
    }

    inner class Args {

        @SerializedName("table")
        var table = "expense"

        @SerializedName("where")
        var where: Where? = null
    }

    inner class Where {
        @SerializedName("user_id")
        var userId: Int? = null

        @SerializedName("id")
        var exp_id: Int? = null
    }

}
