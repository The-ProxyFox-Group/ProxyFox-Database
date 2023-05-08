package dev.proxyfox.database.comparison

// TODO!
public sealed interface Comparison {
    public sealed interface Operator : Comparison {
        public data class And(
                val comparisons: ArrayList<Comparison>
        ): Operator
        public data class Or(
                val comparisons: ArrayList<Comparison>
        ): Operator
        public data class Xor(
                val comparisons: ArrayList<Comparison>
        ): Operator
        public data class Not(
                val comparison: Comparison
        ): Operator
    }

    public sealed interface Record : Comparison {
        public val key: String

        public data class Eq(
                override val key: String,
                val value: Any
        ) : Record
        public data class NotEq(
                override val key: String,
                val value: Any
        ) : Record
        public data class Gt(
                override val key: String,
                val value: Number
        ) : Record
        public data class Lt(
                override val key: String,
                val value: Number
        ) : Record
        public data class GtEq(
                override val key: String,
                val value: Number
        ) : Record
        public data class LtEq(
                override val key: String,
                val value: Number
        ) : Record
        public data class ArrHas(
                override val key: String,
                val value: ArrayList<Any>
        ) : Record
        public data class ObjHas(
                override val key: String,
                val value: Comparison
        ) : Record
    }
}

public fun and(vararg comparisons: Comparison): Comparison = Comparison.Operator.And(arrayListOf(*comparisons))
public fun or(vararg comparisons: Comparison): Comparison = Comparison.Operator.Or(arrayListOf(*comparisons))
public fun xor(vararg comparisons: Comparison): Comparison = Comparison.Operator.Xor(arrayListOf(*comparisons))
public fun not(comparison: Comparison): Comparison = Comparison.Operator.Not(comparison)

public infix fun String.eq(value: Any): Comparison = Comparison.Record.Eq(this, value)
public infix fun String.neq(value: Any): Comparison = Comparison.Record.NotEq(this, value)
public infix fun String.gt(value: Number): Comparison = Comparison.Record.Gt(this, value)
public infix fun String.lt(value: Number): Comparison = Comparison.Record.Gt(this, value)
public infix fun String.gtEq(value: Number): Comparison = Comparison.Record.GtEq(this, value)
public infix fun String.ltEq(value: Number): Comparison = Comparison.Record.GtEq(this, value)
public infix fun String.has(comparison: Comparison): Comparison = Comparison.Record.ObjHas(this, comparison)
public infix fun String.has(value: ArrayList<Any>): Comparison = Comparison.Record.ArrHas(this, value)
