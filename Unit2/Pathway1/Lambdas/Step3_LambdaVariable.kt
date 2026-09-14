private val trick = {
    println("No treats!")
}

fun main() {
    val trickFunction = trick
    trick()
    trickFunction()
}
