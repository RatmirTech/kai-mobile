fun main() {
    println(birthdayGreetingStep6("Rover", 5))
    println(birthdayGreetingStep6(age = 2, name = "Rex"))
}

fun birthdayGreetingStep6(name: String, age: Int): String {
    val nameGreeting = "Happy Birthday, $name!"
    val ageGreeting = "You are now $age years old!"
    return "$nameGreeting\n$ageGreeting"
}
