fun main() {
    println(birthdayGreetingStep4("Rover"))
    println(birthdayGreetingStep4("Rex"))
}

fun birthdayGreetingStep4(name: String): String {
    val nameGreeting = "Happy Birthday, $name!"
    val ageGreeting = "You are now 5 years old!"
    return "$nameGreeting\n$ageGreeting"
}
