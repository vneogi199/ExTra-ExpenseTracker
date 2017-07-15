package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 15/7/17.
 */

public class SelectExpenseQuery {

    @SerializedName("type")
    String type = "select";

    @SerializedName("args")
    Args args;

    public SelectExpenseQuery(Integer userId) {
        args = new Args();
        args.where = new Where();
        args.where.userId = userId;
    }

    class Args {

        @SerializedName("table")
        String table = "expense";

        @SerializedName("columns")
        String[] columns = {
                "exp_id","exp_name","exp_amt","exp_created","exp_note","exp_tag","exp_category"
        };

        @SerializedName("where")
        Where where;

    }

    class Where {
        @SerializedName("user_id")
        Integer userId;
    }

}
