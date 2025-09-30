class Fraction(var numerator: Int, var denominator: Int) {

    fun input() {
        while(true){
            print("Enter numerator: ")
            this.numerator = readLine()!!.toInt()
            print("Enter denominator: ")
            this.denominator = readLine()!!.toInt()
            if (this.denominator != 0) {
                break
            }
            println("Denominator can not be zero!")
        }


    }
    fun simplyfyFraction() {
        val uc = ucln(this.numerator, this.denominator)
        this.numerator /= uc;
        this.denominator /= uc;
    }
    fun comparison(ps: Fraction): Int {
        val ans = this.numerator * ps.denominator - this.denominator * ps.numerator
        if (ans == 0)
            return 0
        else if (ans < 0)
            return -1
        else
            return 1
    }

    fun add(ps: Fraction): Fraction {
        var res = Fraction(0, 1)
        res.numerator = this.numerator * ps.denominator + this.denominator * ps.numerator
        res.denominator = this.denominator * ps.denominator
        res.simplyfyFraction()
        return res
    }
    fun display() {
        println("Fraction = $numerator/$denominator")
    }

}
fun ucln(a: Int, b: Int): Int {
    var x = a
    var y = b
    while (x != y) {
        if (x > y)
            x -= y
        else
            y -= x
    }
    return x
}
fun main() {
    print("Enter number of fractions: ")
    val n = readLine()!!.toInt()
    val fractions = Array(n) { Fraction(1, 1) }
    var fractionSum = Fraction(0, 1)
    var fractionMax = Fraction(1, 1)
    for (i in 0 until n) {
        println("Enter fraction ${i + 1}: ")
        fractions[i].input()
        fractions[i].simplyfyFraction()
        fractionSum = fractionSum.add(fractions[i])
    }
    print("List of fractions: ")
    for (fraction in fractions) {
        print("${fraction.numerator}/${fraction.denominator} ")
    }
    println()

    println("Sum of fractions: ${fractionSum.numerator}/${fractionSum.denominator}")

    fractionMax = fractions[0]
    for(fraction in fractions) {
        if(fractionMax.comparison(fraction) == -1)
            fractionMax = fraction
    }
    println("Max of fractions: ${fractionMax.numerator}/${fractionMax.denominator}")

    for(i in 0 until n) {
        var index: Int = i
        for(j in i + 1 until n) {
            if(fractions[index].comparison(fractions[j]) == -1)
                index = j
        }
        var tempFraction = fractions[i]
        fractions[i]=fractions[index]
        fractions[index]=tempFraction
    }

    print("List of sorted fractions : ")
    for (fraction in fractions) {
        print("${fraction.numerator}/${fraction.denominator} ")
    }
    println()
}
