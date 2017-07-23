package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


/**
 * Created by akash on 23/7/17.
 */
class UpdateExpenseRequest {

    @SerializedName("type")
    var type = "update"

    @SerializedName("args")
    var args: Args = null!!

    fun UpdateExpenseQuery(exp_id: Int?, userId: Int?, exp_name: String, exp_amt: Int?, exp_created: Timestamp, exp_note: String, exp_tag: String, exp_category: Int?) {
        args = Args()
        args.where = Where()
        args.where!!.exp_id = exp_id
        args.where!!.userId = userId
        args.set = Set()
        args.set!!.exp_name = exp_name
        args.set!!.exp_amt = exp_amt
        args.set!!.exp_created = exp_created
        args.set!!.exp_note = exp_note
        args.set!!.exp_tag = exp_tag
        args.set!!.exp_category = exp_category
    }

    inner class Args {

        @SerializedName("table")
        var table = "expense"

        @SerializedName("where")
        var where: Where? = null

        @SerializedName("\$set")
        var set: Set? = null

        @SerializedName("returning")
        var returning = arrayOf("exp_id", "exp_name", "exp_amt", "exp_created", "exp_note", "exp_tag", "exp_category")
    }

    inner class Where {
        @SerializedName("user_id")
        var userId: Int? = null

        @SerializedName("exp_id")
        var exp_id: Int? = null
    }

    inner class Set {
        @SerializedName("exp_name")
        var exp_name: String? = null

        @SerializedName("exp_amt")
        var exp_amt: Int? = null

        @SerializedName("exp_created")
        var exp_created: Timestamp? = null

        @SerializedName("exp_note")
        var exp_note: String? = null

        @SerializedName("exp_tag")
        var exp_tag: String? = null

        @SerializedName("exp_category")
        var exp_category: Int? = null
    }

}
