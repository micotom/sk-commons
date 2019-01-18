fun <T> Collection<T>.powerSetOfEight(): Set<Set<T>> = powerSetOfEight(this, setOf(emptySet()))

private tailrec fun <T> powerSetOfEight(left: Collection<T>, acc: Set<Set<T>>): Set<Set<T>> =
    when(left.isEmpty()) {
        true -> acc
        false -> powerSetOfEight(left.drop(1), acc + acc.map { it + left.take(1) })
    }