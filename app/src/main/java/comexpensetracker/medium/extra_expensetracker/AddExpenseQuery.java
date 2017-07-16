package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

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

    public AddExpenseQuery(String exp_name, Integer userId) {
        args = new Args();
        args.objects = new ArrayList<>();
        AddexpenseRecord record = new AddexpenseRecord(exp_name, userId, 50);
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
