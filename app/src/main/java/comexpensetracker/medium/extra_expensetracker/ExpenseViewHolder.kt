package comexpensetracker.medium.extra_expensetracker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView



/**
 * Created by akash on 23/7/17.
 */
class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var timestampText:TextView?  = null
    var nameText:TextView? = null
    var amtText:TextView? = null
    init {
        timestampText = itemView.findViewById(R.id.timestampText) as TextView
        nameText = itemView.findViewById(R.id.nameText) as TextView
        amtText = itemView.findViewById(R.id.amtText) as TextView
    }
}
