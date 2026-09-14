fun main() {
    println(birthdayGreetingStep5("Rover", 5))
    println(birthdayGreetingStep5("Rex", 2))
}

fun birthdayGreetingStep5(name: String, age: Int): String {
    val nameGreeting = "Happy Birthday, $name!"
    val ageGreeting = "You are now $age years old!"
    return "$nameGreeting\n$ageGreeting"
}
