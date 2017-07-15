package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Created by akash on 15/7/17.
 */

public class UpdateExpenseQuery {

    @SerializedName("type")
    String type = "update";

    @SerializedName("args")
    Args args;

    public UpdateExpenseQuery(Integer exp_id, Integer userId, String exp_name, Integer exp_amt, Timestamp exp_created, String exp_note, String exp_tag, Integer exp_category) {
        args = new Args();
        args.where = new Where();
        args.where.exp_id = exp_id;
        args.where.userId = userId;
        args.set = new Set();
        args.set.exp_name = exp_name;
        args.set.exp_amt =exp_amt ;
        args.set.exp_created =exp_created ;
        args.set.exp_note =exp_note ;
        args.set.exp_tag =exp_tag ;
        args.set.exp_category =exp_category ;
    }

    class Args {

        @SerializedName("table")
        String table = "expense";

        @SerializedName("where")
        Where where;

        @SerializedName("$set")
        Set set;

        @SerializedName("returning")
        String[]  returning = {
                "exp_id","exp_name","exp_amt","exp_created","exp_note","exp_tag","exp_category"
        };
    }

    class Where {
        @SerializedName("user_id")
        Integer userId;

        @SerializedName("exp_id")
        Integer exp_id;
    }

    class Set {
        @SerializedName("exp_name")
        String exp_name;

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
    }

}
