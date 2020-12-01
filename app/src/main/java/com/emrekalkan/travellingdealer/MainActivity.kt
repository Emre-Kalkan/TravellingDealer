package com.emrekalkan.travellingdealer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private val customers = arrayListOf<Node>()
    private val factoryCoordinate = Coordinate(3, 7)
    private val startNode = Node(Coordinate(0, 0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCustomerCoordinates()

        val deleteButton = findViewById<MaterialButton>(R.id.btnDeleteNode)
        deleteButton.setOnClickListener {
            val middlePos = customers.size / 2
            val middleNode = if (customers.size > middlePos) customers[middlePos] else null
            middleNode?.also {
                startNode.deleteNode(startNode, it.coordinate)
                customers.removeAt(middlePos)
                calculateDistance()
            }
        }

        findViewById<MaterialButton>(R.id.btnCalculate).setOnClickListener {
            calculateDistance()
            deleteButton.isEnabled = true
        }
    }

    private fun setCustomerCoordinates() {
        customers.apply {
            add(Node(Coordinate(2, 1)))
            add(Node(Coordinate(5, 2)))
            add(Node(Coordinate(9, 2)))
            add(Node(Coordinate(1, 4)))
            add(Node(Coordinate(8, 4)))
            add(Node(Coordinate(6, 5)))
            add(Node(Coordinate(8, 9)))
            add(Node(Coordinate(1, 10)))
        }
    }

    private fun calculateDistance() {
        startNode.apply {
            if (following == null) {
                customers.forEach { node ->
                    appendToEnd(node.coordinate)
                    // Go to factory after every customer visit
                    appendToEnd(factoryCoordinate)
                }
            }
        }
        val nodesString = "Nodes:\n${startNode.printNodes()}"
        val totalDistance = "Distance: ${String.format("%.4f", startNode.sumOfNodes())}"
        val length = "Length: ${startNode.length(startNode)}"
        findViewById<TextView>(R.id.tvNodes).text = nodesString
        findViewById<TextView>(R.id.tvDistance).text = totalDistance
        findViewById<TextView>(R.id.tvLength).text = length

    }
}