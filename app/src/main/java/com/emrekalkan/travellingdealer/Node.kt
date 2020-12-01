package com.emrekalkan.travellingdealer

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Node(var coordinate: Coordinate) {

    var following: Node? = null
        set(value) {
            value?.coordinate?.also { calculateDistance(it) }
            field = value
        }

    var followingDistance = 0.0

    fun appendToEnd(coordinate: Coordinate) {
        val end = Node(coordinate)
        var n: Node? = this
        while (n!!.following != null) {
            n = n.following
        }
        n.following = end
    }

    private fun calculateDistance(followingCoordinate: Coordinate) {
        val x = (abs(this.coordinate.x) - abs(followingCoordinate.x)).toDouble()
        val y = (abs(this.coordinate.y) - abs(followingCoordinate.y)).toDouble()
        followingDistance = sqrt(x.pow(2) + y.pow(2))
    }

    // For example:: --> 5 --> 6 --> 7 --> 3 --> .
    fun printNodes(): String {
        ArrayList<Node>().apply {
            var node: Node? = this@Node
            add(node!!)
            while (node?.following != null) {
                node = node.following
                node?.also { add(it) }
            }
            joinToString(
                separator = " --> ",
                transform = { "${it.coordinate.x},${it.coordinate.y}" }
            ).also {
                println("TD Nodes: $it")
                return it
            }
        }
    }

    fun length(h: Node?): Int {
        var length = 0
        return h?.run {
            length++
            var node: Node? = this
            while (node?.following != null) {
                node = node.following
                length++
            }
            length
        } ?: 0
    }

    fun sumOfNodes(): Double {
        var distance = 0.0
        var node: Node? = this
        while (node?.following != null) {
            distance += node.followingDistance
            node = node.following
        }
        return distance
    }

    fun deleteNode(head: Node, coordinate: Coordinate): Node? {
        val n: Node = head

        if (n.coordinate == coordinate) {
            return head.following
        }

        val nodes = ArrayList<Node>().apply {
            add(n)
        }
        var previousNode: Node = n
        var node: Node? = n.following
        while (node != null && node.coordinate != coordinate) {
            nodes.add(node)
            previousNode = node
            node = node.following
        }

        if (node?.coordinate == coordinate) {
            val previousNodePos = nodes.indexOf(previousNode)
            if (previousNodePos > 1 && nodes.size > previousNodePos) {
                // Previous node is factory node.
                // So find before the factory node and set new following node
                nodes[previousNodePos - 1].following = node.following
            }
        }

        //
        return head
    }
}