package comexpensetracker.medium.extra_expensetracker

import io.hasura.sdk.query.HasuraQuery



/**
 * Created by akash on 23/7/17.
 */
interface DataService {

    fun getExpense(request: SelectExpenseRequest): HasuraQuery<ExpenseRecord>

}
