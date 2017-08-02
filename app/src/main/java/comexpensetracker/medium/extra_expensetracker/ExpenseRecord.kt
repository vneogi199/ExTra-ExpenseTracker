package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


/**
 * Created by akash on 23/7/17.
 */
class ExpenseRecord(username: String, userId: Int?, exp_amt: Int, exp_created: Timestamp, exp_note: String, exp_tag: String, exp_category: Int) {

    @SerializedName("exp_id")
    var exp_id: Int? = null

    @SerializedName("exp_name")
    var exp_name: String = ""

    @SerializedName("user_id")
    var user_id: Int? = null

    @SerializedName("exp_amt")
    var exp_amt: Int? = null

    @SerializedName("exp_created")
    var exp_created: Timestamp? = null

    @SerializedName("exp_note")
    var exp_note: String = ""

    @SerializedName("exp_tag")
    var exp_tag: String = ""

    @SerializedName("exp_category")
    var exp_category: Int? = null

    fun ExpenseRecord(exp_name: String, user_id: Int?, exp_amt: Int?) {
        this.exp_name = exp_name
        this.user_id = user_id
        this.exp_amt = exp_amt
    }

    fun getExpId(): Int? {
        return exp_id
    }

    fun setExpId(exp_id: Int?) {
        this.exp_id = exp_id
    }

    fun getExpName(): String {
        return exp_name
    }

    fun setExpName(exp_name: String) {
        this.exp_name = exp_name
    }

    fun getUserId(): Int? {
        return user_id
    }

    fun setUserId(userId: Int?) {
        this.user_id = userId
    }

    fun getExpAmt(): Int? {
        return exp_amt
    }

    fun setExpAmt(exp_amt: Int?) {
        this.exp_amt = exp_amt
    }

    fun getExpCreated(): Timestamp? {
        return exp_created
    }

    fun setExpCreated(exp_created: Timestamp) {
        this.exp_created = exp_created
    }

    fun getExpNote(): String {
        return exp_note
    }

    fun setExpNote(exp_note: String) {
        this.exp_note = exp_note
    }

    fun getExpTag(): String {
        return exp_tag
    }

    fun setExpTag(exp_tag: String) {
        this.exp_tag = exp_tag
    }

    fun getExpCategory(): Int? {
        return exp_category
    }

    fun setExpCategory(exp_category: Int?) {
        this.exp_category = exp_category
    }

}
