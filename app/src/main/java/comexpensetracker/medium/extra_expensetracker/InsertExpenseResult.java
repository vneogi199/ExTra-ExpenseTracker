package comexpensetracker.medium.extra_expensetracker;
import com.google.gson.annotations.SerializedName;


/**
 * Created by vinit on 23/7/17.
 */

public class InsertExpenseResult {

    @SerializedName("exp_id")
    Integer exp_id;

    @SerializedName("user_id")
    Integer user_id;

    @SerializedName("exp_name")
    String exp_name;

    @SerializedName("exp_amt")
    Integer exp_amt;

    @SerializedName("exp_created")
    String exp_created;

    @SerializedName("exp_note")
    String exp_note;

    @SerializedName("exp_tag")
    String exp_tag;

    @SerializedName("exp_category")
    Integer exp_category;


    public InsertExpenseResult(Integer user_id, String exp_name,  Integer exp_amt, String  exp_created, Integer exp_category) {
        this.user_id=user_id;
        this.exp_name=exp_name;
        this.exp_amt=exp_amt;
        this.exp_created=exp_created;
        this.exp_category=exp_category;

    }

    public void setExp_id(Integer exp_id) {
        this.exp_id = exp_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setExp_name(String exp_name) {
        this.exp_name = exp_name;
    }

    public void setExp_amt(Integer exp_amt) {
        this.exp_amt = exp_amt;
    }

    public void setExp_created(String exp_created) {
        this.exp_created = exp_created;
    }

    public void setExp_note(String exp_note) {
        this.exp_note = exp_note;
    }

    public void setExp_tag(String exp_tag) {
        this.exp_tag = exp_tag;
    }

    public void setExp_category(Integer exp_category) {
        this.exp_category = exp_category;
    }

    public Integer getExp_id() {

        return exp_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getExp_name() {
        return exp_name;
    }

    public Integer getExp_amt() {
        return exp_amt;
    }

    public String getExp_created() {
        return exp_created;
    }

    public String getExp_note() {
        return exp_note;
    }

    public String getExp_tag() {
        return exp_tag;
    }

    public Integer getExp_category() {
        return exp_category;
    }
}
