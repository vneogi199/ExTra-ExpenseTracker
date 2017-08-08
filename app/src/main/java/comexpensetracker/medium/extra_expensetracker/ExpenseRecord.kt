package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


/**
 * Created by akash on 23/7/17.
 */
class ExpenseRecord(exp_name: String, exp_amt: Int, exp_created: String, category_name: String) {

    @SerializedName("exp_name")
    var exp_name: String = ""

    @SerializedName("exp_amt")
    var exp_amt: Int = 0

    @SerializedName("exp_created")
    var exp_created: String? = null

    @SerializedName("exp_category")
    var category_name: String? = null

    init {
        this.exp_name = exp_name
        this.exp_amt = exp_amt
        this.exp_created = exp_created
        this.category_name = category_name
    }


    fun ExpenseRecord(exp_name: String, exp_amt: Int, exp_created: String, category_name: String) {
        this.exp_name = exp_name
        this.exp_amt = exp_amt
        this.exp_created = exp_created
        this.category_name = category_name
    }

    fun getExpName() : String {
        return exp_name
    }

    fun setExpName(exp_name: String){
        this.exp_name = exp_name
    }

    fun getExpAmt() : Int {
        return exp_amt
    }

    fun setExpAmt(exp_amt: Int){
        this.exp_amt = exp_amt
    }
    fun getExpCreated() : String? {
        return exp_created
    }

    fun setExpCreated(exp_created: String){
        this.exp_created = exp_created
    }

    fun getExpCategory() : String? {
        return category_name
    }

    fun setExpCategory(exp_category: String){
        this.category_name = category_name
    }

}
