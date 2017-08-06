package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


/**
 * Created by akash on 23/7/17.
 */
class ExpenseRecord{

    @SerializedName("exp_name")
    var exp_name: String = ""

    @SerializedName("exp_amt")
    var exp_amt: Int? = null

    @SerializedName("exp_created")
    var exp_created: Timestamp? = null

    @SerializedName("exp_category")
    var exp_category: Int? = null

    fun ExpenseRecord(exp_name: String, exp_amt: Int, exp_created: Timestamp, exp_category: Int) {
        this.exp_name = exp_name
        this.exp_amt = exp_amt
        this.exp_created = exp_created
        this.exp_category = exp_category
    }

    fun getExpName() : String {
        return exp_name
    }

    fun setExpName(exp_name: String){
        this.exp_name = exp_name
    }

    fun getExpAmt() : Int? {
        return exp_amt
    }

    fun setExpAmt(exp_amt: Int?){
        this.exp_amt = exp_amt
    }
    fun getExpCreated() : Timestamp? {
        return exp_created
    }

    fun setExpCreated(exp_created: Timestamp){
        this.exp_created = exp_created
    }

    fun getExpCategory() : Int? {
        return exp_category
    }

    fun setExpCategory(exp_category: Int){
        this.exp_category = exp_category
    }

}
