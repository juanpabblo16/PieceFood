package icesi.edu.co.piecefood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import icesi.edu.co.piecefood.R
import icesi.edu.co.piecefood.model.Portion

class PortionAdapter(
    private val context: Context,
    private var portions: List<Portion>,
    private var ingredientNames: Map<String, String>
) : BaseAdapter() {

    override fun getCount(): Int = portions.size

    override fun getItem(position: Int): Portion = portions[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_portion, parent, false)
        val portion = getItem(position)

        val ingredientNameTextView = view.findViewById<TextView>(R.id.ingredientNameTextView)
        val quantityTextView = view.findViewById<TextView>(R.id.quantityTextView)

        val ingredientName = ingredientNames[portion.ingredientId] ?: "Unknown"
        ingredientNameTextView.text = ingredientName
        quantityTextView.text = portion.quantity.toString()

        return view
    }

    fun updatePortions(newPortions: List<Portion>, newIngredientNames: Map<String, String>) {
        portions = newPortions
        ingredientNames = newIngredientNames
        notifyDataSetChanged()
    }
}
