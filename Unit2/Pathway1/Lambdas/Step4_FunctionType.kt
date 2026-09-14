private val trick = {
    println("No treats!")
}

private val treat: () -> Unit = {
    println("Have a treat!")
}

fun main() {
    val trickFunction = trick
    trick()
    trickFunction()
    treat()
}
