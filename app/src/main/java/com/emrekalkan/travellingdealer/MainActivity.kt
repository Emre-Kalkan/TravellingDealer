package com.emrekalkan.travellingdealer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var deleteButton: MaterialButton
    private lateinit var calculateButton: MaterialButton

    private val customers = LinkedList<Node>()
    private val factoryCoordinate = Coordinate(3, 7)
    private var finalNode = Node(Coordinate(0, 0))
    private var distance = Double.MAX_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCustomerCoordinates()

        deleteButton = findViewById(R.id.btnDeleteNode)
        deleteButton.setOnClickListener {
            deleteButton.isEnabled = false
            val middlePos = customers.size / 2
            val middleNode = if (customers.size > middlePos) customers[middlePos] else null
            middleNode?.also {
                finalNode.deleteNode(finalNode, it.coordinate)
                customers.removeAt(middlePos)
                calculatePermutations(customers.size, customers.toTypedArray())
                showResult()
            }
        }

        calculateButton = findViewById(R.id.btnCalculate)
        calculateButton.setOnClickListener {
            deleteButton.isEnabled = true
            calculateButton.isEnabled = false
            calculatePermutations(customers.size, customers.toTypedArray())
            showResult()
        }
    }

    private fun setCustomerCoordinates() {
        customers.apply {
            add(Node(Coordinate(1, 1)))
            add(Node(Coordinate(2, 8)))
            add(Node(Coordinate(10, 3)))
            add(Node(Coordinate(6, 4)))
        }
    }

    private fun showResult() {
        val nodesString = "Nodes:\n${finalNode.printNodes()}"
        val totalDistance = "Distance: ${String.format("%.4f", finalNode.sumOfNodes())}"
        val length = "Length: ${finalNode.length(finalNode)}"
        findViewById<TextView>(R.id.tvNodes).text = nodesString
        findViewById<TextView>(R.id.tvDistance).text = totalDistance
        findViewById<TextView>(R.id.tvLength).text = length
    }

    private fun calculateDistance(elements: Array<Node>) {
        Node(Coordinate(0, 0)).apply {
            elements.forEach { node ->
                appendToEnd(node.coordinate)
                // Go to factory after every customer visit
                appendToEnd(factoryCoordinate)
            }

            val totalDistance = sumOfNodes()
            if (totalDistance < distance) {
                distance = totalDistance
                finalNode = this
            }
        }
    }

    private fun calculatePermutations(n: Int, elements: Array<Node>) {
        if (n == 1) {
            calculateDistance(elements)
            println(elements.joinToString(
                separator = " --> ",
                transform = { "${it.coordinate.x}, ${it.coordinate.y}" }
            ))
        } else {
            for (i in 0 until n - 1) {
                calculatePermutations(n - 1, elements)
                if (n % 2 == 0) {
                    swap(elements, i, n - 1)
                } else {
                    swap(elements, 0, n - 1)
                }
            }
            calculatePermutations(n - 1, elements)
        }
    }

    private fun swap(input: Array<Node>, a: Int, b: Int) {
        val tmp: Node = input[a]
        input[a] = input[b]
        input[b] = tmp
    }

    private fun printArray(input: Array<Node>) {
        for (i in input.indices) {
            println(input[i])
        }
    }
}