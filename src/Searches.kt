import java.io.File

/**
 * This is an assignment that aims to demystify how basic search algorithms operates.
 * It should operate on <e>many-sorted-numbers.txt<e> and log the amount of comparisons
 * in each respective algorithm. The algorithms to demonstrate is a sequential search,
 * exponential search and an interpolation search
 *
 * @author Jonas Thorhauge Grønbek
 * @constructor the filepath is the absolute path to the file which must be sorted
 */
class Searches(filePath : String, private val searchValue : Int){

    /**
     * Loads in a filepath and creates a array of string with space as delimiter
     */
    private val data : List<Int> = File(filePath).readLines().map { it.toInt() }

    /**
     * will run O(logn)
     * Ideally an exponentialsearch is more suited for unbound data structures. (range is inaccesible)
     * Or if you know the element you are looking for will generally be placed in the start of the data structure
     */
    fun exponentialSearch() : Int{
        if(data[0] == searchValue) return 1
        var comparisons = 1
        var i = 1
        while (i < data.size && data[i] <= searchValue) {
            comparisons++
            i *= 2
        }
        return binarySearch( i / 2, data.size, comparisons)
    }

    /**
     * worst case O(logn)
     */
    fun binarySearch(leftBorder: Int, rightBorder: Int, comparisons: Int) : Int {
        val pointer = (leftBorder + rightBorder) / 2
        val placement = data[pointer] - searchValue

        return when {
            placement == 0 -> comparisons + 1
            placement < 0 -> binarySearch(pointer, rightBorder, comparisons + 1)
            else -> binarySearch(leftBorder, pointer, comparisons + 1)
        }
        error("No data was found")
    }
    /**
     * worst case will run in O(n) independent of the array being sorted or not
     */
    fun sequentialSearch() : Int{
        var count = 0
        for(i in 0..data.size){
            count++
            if(data[i] == searchValue)
                break
        }
        return count
    }

    /**
     * Most efficient if the elements are uniformly distributed.
     * non sorted: Worst case O(n)
     * sorted: O(log logn)
     */
    fun interpolationSearch(): Int {

        var leftBorder = 0
        var rightBorder: Int = data.size - 1
        var comparisons = 0

        while (leftBorder <= rightBorder && searchValue >= data[leftBorder] && searchValue <= data[rightBorder]) {
            comparisons += 3

            if (leftBorder == rightBorder) {
                comparisons++
                return if (data[leftBorder] === searchValue) leftBorder else -1
            }

            // Guessing
            val pointer = leftBorder + (rightBorder - leftBorder) /
                    (data[rightBorder] - data[leftBorder]) * (searchValue - data[leftBorder])

            comparisons++
            if (data[pointer] == searchValue) return comparisons

            // If searchValue is larger, searchValue is in upper part
            comparisons++
            if (data[pointer] < searchValue) leftBorder = pointer + 1 else rightBorder = pointer - 1
        }
        return -1
    }
}

fun main(){
    val searches = Searches(File("").absolutePath + "/src/many-sorted-numbers.txt",50113299)
    println(searches.sequentialSearch())
    println(searches.exponentialSearch())
    println(searches.binarySearch(0,100003, 0))
    println(searches.interpolationSearch())
}