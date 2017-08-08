package comexpensetracker.medium.extra_expensetracker

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.getDescription
import android.support.v4.app.NotificationCompat.getCategory
import android.R.attr.data





/**
 * Created by vinit on 7/8/17.
 */
class RecyclerViewAdapter(context: Context, recyclerViewItems: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // A menu item view type.
    var MENU_ITEM_VIEW_TYPE = 0

    // The Native Express ad view type.
    var NATIVE_EXPRESS_AD_VIEW_TYPE = 1

    // An Activity's Context.
    var mContext: Context? = null

    // The list of Native Express ads and menu items.
    var mRecyclerViewItems: List<Any>? = null

    init {
        this.mContext = context
        this.mRecyclerViewItems = recyclerViewItems
    }

    class ExpenseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var timestampText:TextView?  = null
        var nameText:TextView? = null
        var amtText:TextView? = null
        init {
            timestampText = itemView?.findViewById(R.id.timestampText) as TextView
            nameText = itemView.findViewById(R.id.nameText) as TextView
            amtText = itemView.findViewById(R.id.amtText) as TextView
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        if(viewType==MENU_ITEM_VIEW_TYPE){
            return  null
        }
        else{
            var expenseItemLayoutView: View? = LayoutInflater.from(parent?.context).inflate(R.layout.expense_list_item, parent, false)
            return ExpenseViewHolder(expenseItemLayoutView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewType = getItemViewType(position)
        if(viewType == MENU_ITEM_VIEW_TYPE){}
        else{
            val expenseItemHolder = holder as ExpenseViewHolder
            val expenseItem = mRecyclerViewItems?.get(position) as ExpenseRecord

            // Get the menu item image resource ID.
            //val imageName = menuItem.getImageName()
            //val imageResID = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName())

            // Add the menu item details to the menu item view.
            //expenseItemHolder.menuItemImage.setImageResource(imageResID)
            expenseItemHolder.timestampText?.text = expenseItem.getExpCreated()
            expenseItemHolder.nameText?.text = expenseItem.getExpName()
            expenseItemHolder.amtText?.setText(expenseItem.getExpAmt())
        }
    }

    override fun getItemCount(): Int {
        return  mRecyclerViewItems!!.size
    }

}