package model

import com.me.resources.library.MR

sealed interface Emotion {
    fun name(): String
    val feelings: List<Feeling>

    fun nameCompact(): String {
        return when (this) {
            Anger -> string(MR.strings.months_k)
            Fear -> string(MR.strings.fear_b)
            Happy -> string(MR.strings.happy_b)
            Peaceful -> string(MR.strings.peaceful_b)
            Sad -> string(MR.strings.sad_b)
            Surprise -> string(MR.strings.surprise_b)
        }
    }

    companion object {
        fun random() = values.random()
        val values: List<Emotion> = listOf(Anger, Fear, Sad, Happy, Surprise, Peaceful)
        fun values() = values
    }
}

data object Happy : Emotion {
    sealed class HappyFeeling : Feeling() {
        override fun emotion() = Happy
        override fun color() = emotion().color()
    }

    object Content : HappyFeeling()
    object Cheerful : HappyFeeling()
    object Proud : HappyFeeling()
    object Confident : HappyFeeling()
    object Silly : HappyFeeling()
    object Energetic : HappyFeeling()
    object Excited : HappyFeeling()
    object Enthusiastic : HappyFeeling()
    object Eager : HappyFeeling()
    object Hopeful : HappyFeeling()
    object Optimistic : HappyFeeling()
    object Elation : HappyFeeling()

    override fun name(): String = string(MR.strings.happy)

    override val feelings: List<Feeling>
        get() = listOf(
            Content,
            Cheerful,
            Proud,
            Confident,
            Silly,
            Energetic,
            Excited,
            Enthusiastic,
            Eager,
            Hopeful,
            Optimistic,
            Elation
        )
}

data object Peaceful : Emotion {
    sealed class PeacefulFeeling : Feeling() {
        override fun emotion() = Peaceful
        override fun color() = emotion().color()
    }

    object Calm : PeacefulFeeling()
    object Loving : PeacefulFeeling()
    object Affectionate : PeacefulFeeling()
    object Trusting : PeacefulFeeling()
    object Relaxed : PeacefulFeeling()
    object Thoughtful : PeacefulFeeling()
    object Caring : PeacefulFeeling()
    object Thankful : PeacefulFeeling()
    object Accepted : PeacefulFeeling()
    object Valued : PeacefulFeeling()
    object Inspired : PeacefulFeeling()
    object Passionate : PeacefulFeeling()

    override fun name(): String = string(MR.strings.peaceful)

    override val feelings: List<Feeling>
        get() = listOf(
            Calm,
            Loving,
            Affectionate,
            Trusting,
            Relaxed,
            Thoughtful,
            Caring,
            Thankful,
            Accepted,
            Valued,
            Inspired,
            Passionate,
        )
}

data object Fear : Emotion {
    sealed class FearfulFeeling : Feeling() {
        override fun emotion() = Fear
        override fun color() = emotion().color()
    }

    object Shy : FearfulFeeling()
    object Nervous : FearfulFeeling()
    object Worried : FearfulFeeling()
    object Anxious : FearfulFeeling()
    object Regret : FearfulFeeling()
    object Excluded : FearfulFeeling()
    object Insecure : FearfulFeeling()
    object Embarrassed : FearfulFeeling()
    object Scared : FearfulFeeling()
    object Frightened : FearfulFeeling()
    object Terrified : FearfulFeeling()
    object Panic : FearfulFeeling()

    override fun name(): String = string(MR.strings.fear)

    override val feelings: List<Feeling>
        get() = listOf(
            Shy,
            Nervous,
            Worried,
            Anxious,
            Regret,
            Excluded,
            Insecure,
            Embarrassed,
            Scared,
            Frightened,
            Terrified,
            Panic,
        )
}

data object Surprise : Emotion {
    sealed class SurpriseFeeling : Feeling() {
        override fun emotion() = Surprise
        override fun color() = emotion().color()
    }

    object Startled : SurpriseFeeling()
    object Confused : SurpriseFeeling()
    object Shocked : SurpriseFeeling()
    object OverWhelmed : SurpriseFeeling()
    object Speechless : SurpriseFeeling()
    object Curious : SurpriseFeeling()
    object Amazed : SurpriseFeeling()
    object Moved : SurpriseFeeling()
    object AweStruck : SurpriseFeeling()
    object Crushed : SurpriseFeeling()
    object Stunned : SurpriseFeeling()
    object Bewildered : SurpriseFeeling()

    override fun name(): String = string(MR.strings.surprise)

    override val feelings: List<Feeling>
        get() = listOf(
            Startled,
            Confused,
            Shocked,
            OverWhelmed,
            Speechless,
            Curious,
            Amazed,
            Moved,
            AweStruck,
            Crushed,
            Stunned,
            Bewildered,
        )
}

data object Sad : Emotion {
    sealed class SadFeeling : Feeling() {
        override fun emotion() = Sad
        override fun color() = emotion().color()
    }

    object Bored : SadFeeling()
    object Tired : SadFeeling()
    object Lonely : SadFeeling()
    object Guilty : SadFeeling()
    object Disappointed : SadFeeling()
    object Hurt : SadFeeling()
    object Insignificant : SadFeeling()
    object Inferior : SadFeeling()
    object Rejected : SadFeeling()
    object Hopeless : SadFeeling()
    object Depressed : SadFeeling()
    object Miserable : SadFeeling()

    override fun name(): String = string(MR.strings.sad)

    override val feelings: List<Feeling>
        get() = listOf(
            Bored,
            Tired,
            Lonely,
            Guilty,
            Disappointed,
            Hurt,
            Insignificant,
            Inferior,
            Rejected,
            Hopeless,
            Depressed,
            Miserable,
        )
}

data object Anger : Emotion {
    sealed class AngerFeeling : Feeling() {
        override fun emotion() = Anger
        override fun color() = emotion().color()
    }

    object Annoyed : AngerFeeling()
    object Frustrated : AngerFeeling()
    object Irritated : AngerFeeling()
    object Jealous : AngerFeeling()
    object Offended : AngerFeeling()
    object Mad : AngerFeeling()
    object Disgusted : AngerFeeling()
    object Hateful : AngerFeeling()
    object Furious : AngerFeeling()
    object Agitated : AngerFeeling()
    object Enraged : AngerFeeling()
    object Hostile : AngerFeeling()

    override fun name(): String = string(MR.strings.anger)

    override val feelings: List<Feeling>
        get() = listOf(
            Annoyed,
            Frustrated,
            Irritated,
            Jealous,
            Offended,
            Mad,
            Disgusted,
            Hateful,
            Furious,
            Agitated,
            Enraged,
            Hostile,
        )
}
