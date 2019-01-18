package model

enum class Cards(val color: Color, val value: Value) {

    EICHEL_AS(Color.EICHEL, Value.AS),
    EICHEL_ZEHNER(Color.EICHEL, Value.ZEHNER),
    EICHEL_KOENIG(Color.EICHEL, Value.KOENIG),
    EICHEL_OBER(Color.EICHEL, Value.OBER),
    EICHEL_UNTER(Color.EICHEL, Value.UNTER),
    EICHEL_NEUNER(Color.EICHEL, Value.NEUNER),
    EICHEL_ACHTER(Color.EICHEL, Value.ACHTER),
    EICHEL_SIEBENER(Color.EICHEL, Value.SIEBENER),

    HERZ_AS(Color.HERZ, Value.AS),
    HERZ_ZEHNER(Color.HERZ, Value.ZEHNER),
    HERZ_KOENIG(Color.HERZ, Value.KOENIG),
    HERZ_OBER(Color.HERZ, Value.OBER),
    HERZ_UNTER(Color.HERZ, Value.UNTER),
    HERZ_NEUNER(Color.HERZ, Value.NEUNER),
    HERZ_ACHTER(Color.HERZ, Value.ACHTER),
    HERZ_SIEBENER(Color.HERZ, Value.SIEBENER),

    GRAS_AS(Color.GRAS, Value.AS),
    GRAS_ZEHNER(Color.GRAS, Value.ZEHNER),
    GRAS_KOENIG(Color.GRAS, Value.KOENIG),
    GRAS_OBER(Color.GRAS, Value.OBER),
    GRAS_UNTER(Color.GRAS, Value.UNTER),
    GRAS_NEUNER(Color.GRAS, Value.NEUNER),
    GRAS_ACHTER(Color.GRAS, Value.ACHTER),
    GRAS_SIEBENER(Color.GRAS, Value.SIEBENER),

    SCHELLEN_AS(Color.SCHELLEN, Value.AS),
    SCHELLEN_ZEHNER(Color.SCHELLEN, Value.ZEHNER),
    SCHELLEN_KOENIG(Color.SCHELLEN, Value.KOENIG),
    SCHELLEN_OBER(Color.SCHELLEN, Value.OBER),
    SCHELLEN_UNTER(Color.SCHELLEN, Value.UNTER),
    SCHELLEN_NEUNER(Color.SCHELLEN, Value.NEUNER),
    SCHELLEN_ACHTER(Color.SCHELLEN, Value.ACHTER),
    SCHELLEN_SIEBENER(Color.SCHELLEN, Value.SIEBENER);

    enum class Color(val asValueTrumpRank: Int) {
        EICHEL(4), GRAS(3), HERZ(2), SCHELLEN(1)
    }

    enum class Value(val defaultRank: Int) {
        AS(8), ZEHNER(7), KOENIG(6), OBER(5), UNTER(4), NEUNER(3), ACHTER(2), SIEBENER(1)
    }

}