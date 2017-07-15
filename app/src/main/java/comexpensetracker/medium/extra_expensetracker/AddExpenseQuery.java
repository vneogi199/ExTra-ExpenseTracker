package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 15/7/17.
 */

public class AddExpenseQuery {

    @SerializedName("type")
    String type = "insert";

    @SerializedName("args")
    Args args;

    public AddExpenseQuery(String exp_name, Integer userId, Integer exp_amt, Timestamp exp_created, String exp_note, String exp_tag, Integer exp_category) {
        args = new Args();
        args.objects = new ArrayList<>();
        AddexpenseRecord record = new AddexpenseRecord(exp_name, userId, exp_amt, exp_created, exp_note, exp_tag, exp_category);
        args.objects.add(record);
    }

    class Args {

        @SerializedName("table")
        String table = "expense";

        @SerializedName("returning")
        String[] returning = {
                "exp_id","exp_name","exp_amt","exp_created","exp_note","exp_tag","exp_category"
        };

        @SerializedName("objects")
        List<AddexpenseRecord> objects;

    }

}
