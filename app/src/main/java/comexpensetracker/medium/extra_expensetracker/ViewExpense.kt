package comexpensetracker.medium.extra_expensetracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import io.hasura.sdk.Hasura
import io.hasura.sdk.HasuraUser
import io.hasura.sdk.ProjectConfig
import io.hasura.sdk.exception.HasuraInitException


class ViewExpense : AppCompatActivity() {
    val client = Hasura.getClient()!!
    var user : HasuraUser = Hasura.getClient().user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)

        var list_of_expensesLayout : RecyclerView = findViewById(R.id.list_of_expenses) as RecyclerView
        list_of_expensesLayout.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        list_of_expensesLayout.layoutManager = llm

        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("cobalt21.hasura-app.io")
                    //.enableOverHttp()
                    .build())
                    .initialise(this)
        }   catch (e: HasuraInitException) {
                e.printStackTrace()
        }
    }
}
