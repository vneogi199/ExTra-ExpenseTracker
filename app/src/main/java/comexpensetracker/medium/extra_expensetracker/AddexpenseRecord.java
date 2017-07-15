package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Created by akash on 15/7/17.
 */

public class AddexpenseRecord {

    @SerializedName("exp_id")
    Integer exp_id;

    @SerializedName("exp_name")
    String exp_name;

    @SerializedName("user_id")
    Integer user_id;

    @SerializedName("exp_amt")
    Integer exp_amt;

    @SerializedName("exp_created")
    Timestamp exp_created;

    @SerializedName("exp_note")
    String exp_note;

    @SerializedName("exp_tag")
    String exp_tag;

    @SerializedName("exp_category")
    Integer exp_category;

    public AddexpenseRecord(String exp_name, Integer user_id, Integer exp_amt, Timestamp exp_created, String exp_note, String exp_tag, Integer exp_category) {
        this.exp_name = exp_name;
        this.user_id = user_id;
        this.exp_amt = exp_amt;
        this.exp_created = exp_created;
        this.exp_note = exp_note;
        this.exp_tag = exp_tag;
        this.exp_category = exp_category;
    }

    public Integer getExpId() {
        return exp_id;
    }

    public void setExpId(Integer exp_id) {
        this.exp_id = exp_id;
    }

    public String getExpName() {
        return exp_name;
    }

    public void setExpName(String exp_name) {
        this.exp_name = exp_name;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer userId) {
        this.user_id = userId;
    }

    public Integer getExpAmt() {
        return exp_amt;
    }

    public void setExpAmt(Integer exp_amt) {
        this.exp_amt = exp_amt;
    }

    public Timestamp getExpCreated() {
        return exp_created;
    }

    public void setExpCreated(Timestamp exp_created) {
        this.exp_created = exp_created;
    }

    public String getExpNote() {
        return exp_note;
    }

    public void setExpNote(String exp_note) {
        this.exp_note = exp_note;
    }

    public String getExpTag() {
        return exp_tag;
    }

    public void setExpTag(String exp_tag) {
        this.exp_tag = exp_tag;
    }

    public Integer getExpCategory() {
        return exp_category;
    }

    public void setExpCategory(Integer exp_category) {
        this.exp_category = exp_category;
    }

}
