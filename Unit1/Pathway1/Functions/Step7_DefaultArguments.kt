fun main() {
    println(birthdayGreetingStep7(age = 5))
    println(birthdayGreetingStep7(age = 2))

    test()
}

fun birthdayGreetingStep7(name: String = "Rover", age: Int): String {
    return "Happy Birthday, $name! You are now $age years old!"
}

fun test() {

}