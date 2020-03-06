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
class Searches(filePath : String, private val searchValue : String){

    /**
     * Loads in a filepath and creates a array of string with space as delimiter
     */
    private val data : List<String> = File(filePath).readLines()

    /**
     * will run O(logn)
     * Doesn't handle if it doesn't exist - too bad
     */
    fun exponentialSearch() : Int{
        if(data[0] == searchValue) return 1
        var comparisons = 1
        var i = 1
        while (i < data.size && data[i].toInt() <= searchValue.toInt()) {
            comparisons++
            i *= 2
        }
        return binarySearch( i / 2, data.size, comparisons)
    }

    /**
     *
     */
    fun binarySearch(leftBorder: Int, rightBorder: Int, comparisons: Int) : Int {
        val pointer = (leftBorder + rightBorder) / 2
        val placement = data[pointer].toInt() - searchValue.toInt()

        return when {
            placement == 0 -> comparisons + 1
            placement < 0 -> binarySearch(pointer, rightBorder, comparisons + 1)
            else -> binarySearch(leftBorder, pointer, comparisons + 1)
        }
        error("No data was found")
    }
    /**
     * will run in O(n) independant of the array being sorted or not
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

    fun interpolationSearch() : Int{
        return 0
    }
}

fun main(){
    val searches = Searches(File("").getAbsolutePath() + "/src/many-sorted-numbers.txt","50113299")
    println(searches.sequentialSearch())
    println(searches.exponentialSearch())
    println(searches.binarySearch(0,100003, 0))
}