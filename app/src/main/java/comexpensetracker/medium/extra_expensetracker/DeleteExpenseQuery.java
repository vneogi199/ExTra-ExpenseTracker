package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 15/7/17.
 */

public class DeleteExpenseQuery {

    @SerializedName("type")
    String type = "delete";

    @SerializedName("args")
    Args args;

    public DeleteExpenseQuery(Integer exp_id, Integer userId) {
        args = new Args();
        args.where = new Where();
        args.where.exp_id = exp_id;
        args.where.user_id = userId;
    }

    class Args {

        @SerializedName("table")
        String table = "expense";

        @SerializedName("where")
        Where where;
    }

    class Where {
        @SerializedName("user_id")
        Integer user_id;

        @SerializedName("id")
        Integer exp_id;
    }

}
