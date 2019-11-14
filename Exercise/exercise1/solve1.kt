

fun main(args: Array<String>) {
        for (comb in combine(listOf(1,2,3,4), 3)) {
                for (perm in permutation(comb)) {
                        println(perm)
                }
        }
}

fun permutation(numbers: List<Int>): List<List<Int>> {
        var result = mutableListOf<List<Int>>()
        if (numbers.size == 0) {
                return result
        }

        if (numbers.size == 1) {
                result.add(numbers)
                return result
        }

        var perm = mutableListOf<Int>()
        perm.addAll(numbers)
        for (i in 0..(numbers.size-1)) {
                var t = perm[i]
                perm[i] = perm[0]
                perm[0] = t
                
                for (subPerm in permutation(perm.takeLast(perm.size - 1))) {
                        var newPerm = mutableListOf<Int>()
                        newPerm.add(perm.first())
                        newPerm.addAll(subPerm)
                        result.add(newPerm)
                }
                
                t = perm[i]
                perm[i] = perm[0]
                perm[0] = t
        }
        
        return result
}

fun combine(elements: List<Int>, n: Int):List<List<Int>> {
        var result = mutableListOf<List<Int>>()
        if (n <= 0 || elements.size < n) {
                return result
        }
        
        if (elements.size == n) {
                result.add(elements)
                return result
        }
        
        result.addAll(combine(elements.takeLast(elements.size - 1), n))
        for (sub in combine(elements.takeLast(elements.size - 1), n - 1)) {
                var numbers = mutableListOf<Int>()
                numbers.add(elements.first())
                numbers.addAll(sub)
                result.add(numbers)
        }
        
        
        return result
}

