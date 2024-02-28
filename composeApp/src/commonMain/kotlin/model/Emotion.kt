package model

import com.me.resources.library.MR
import kotlinx.serialization.Serializable

@Serializable
sealed class Emotion {
    abstract fun name(): String
    abstract val feelings: List<Feeling>

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

@Serializable
data object Happy : Emotion() {
    sealed class HappyFeeling : Feeling() {
        override fun emotion() = Happy
        override fun color() = emotion().color()
    }

    @Serializable
    object Content : HappyFeeling()

    @Serializable
    object Cheerful : HappyFeeling()

    @Serializable
    object Proud : HappyFeeling()

    @Serializable
    object Confident : HappyFeeling()

    @Serializable
    object Silly : HappyFeeling()

    @Serializable
    object Energetic : HappyFeeling()

    @Serializable
    object Excited : HappyFeeling()

    @Serializable
    object Enthusiastic : HappyFeeling()

    @Serializable
    object Eager : HappyFeeling()

    @Serializable
    object Hopeful : HappyFeeling()

    @Serializable
    object Optimistic : HappyFeeling()

    @Serializable
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

@Serializable
data object Peaceful : Emotion() {
    sealed class PeacefulFeeling : Feeling() {
        override fun emotion() = Peaceful
        override fun color() = emotion().color()
    }

    @Serializable
    object Calm : PeacefulFeeling()

    @Serializable
    object Loving : PeacefulFeeling()

    @Serializable
    object Affectionate : PeacefulFeeling()

    @Serializable
    object Trusting : PeacefulFeeling()

    @Serializable
    object Relaxed : PeacefulFeeling()

    @Serializable
    object Thoughtful : PeacefulFeeling()

    @Serializable
    object Caring : PeacefulFeeling()

    @Serializable
    object Thankful : PeacefulFeeling()

    @Serializable
    object Accepted : PeacefulFeeling()

    @Serializable
    object Valued : PeacefulFeeling()

    @Serializable
    object Inspired : PeacefulFeeling()

    @Serializable
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

@Serializable
data object Fear : Emotion() {
    sealed class FearfulFeeling : Feeling() {
        override fun emotion() = Fear
        override fun color() = emotion().color()
    }

    @Serializable
    object Shy : FearfulFeeling()

    @Serializable
    object Nervous : FearfulFeeling()

    @Serializable
    object Worried : FearfulFeeling()

    @Serializable
    object Anxious : FearfulFeeling()

    @Serializable
    object Regret : FearfulFeeling()

    @Serializable
    object Excluded : FearfulFeeling()

    @Serializable
    object Insecure : FearfulFeeling()

    @Serializable
    object Embarrassed : FearfulFeeling()

    @Serializable
    object Scared : FearfulFeeling()

    @Serializable
    object Frightened : FearfulFeeling()

    @Serializable
    object Terrified : FearfulFeeling()

    @Serializable
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

@Serializable
data object Surprise : Emotion() {
    sealed class SurpriseFeeling : Feeling() {
        override fun emotion() = Surprise
        override fun color() = emotion().color()
    }

    @Serializable
    object Startled : SurpriseFeeling()

    @Serializable
    object Confused : SurpriseFeeling()

    @Serializable
    object Shocked : SurpriseFeeling()

    @Serializable
    object OverWhelmed : SurpriseFeeling()

    @Serializable
    object Speechless : SurpriseFeeling()

    @Serializable
    object Curious : SurpriseFeeling()

    @Serializable
    object Amazed : SurpriseFeeling()

    @Serializable
    object Moved : SurpriseFeeling()

    @Serializable
    object AweStruck : SurpriseFeeling()

    @Serializable
    object Crushed : SurpriseFeeling()

    @Serializable
    object Stunned : SurpriseFeeling()

    @Serializable
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

@Serializable
data object Sad : Emotion() {
    sealed class SadFeeling : Feeling() {
        override fun emotion() = Sad
        override fun color() = emotion().color()
    }

    @Serializable
    object Bored : SadFeeling()

    @Serializable
    object Tired : SadFeeling()

    @Serializable
    object Lonely : SadFeeling()

    @Serializable
    object Guilty : SadFeeling()

    @Serializable
    object Disappointed : SadFeeling()

    @Serializable
    object Hurt : SadFeeling()

    @Serializable
    object Insignificant : SadFeeling()

    @Serializable
    object Inferior : SadFeeling()

    @Serializable
    object Rejected : SadFeeling()

    @Serializable
    object Hopeless : SadFeeling()

    @Serializable
    object Depressed : SadFeeling()

    @Serializable
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

@Serializable
data object Anger : Emotion() {
    sealed class AngerFeeling : Feeling() {
        override fun emotion() = Anger
        override fun color() = emotion().color()
    }

    @Serializable
    object Annoyed : AngerFeeling()
    @Serializable
    object Frustrated : AngerFeeling()
    @Serializable
    object Irritated : AngerFeeling()
    @Serializable
    object Jealous : AngerFeeling()
    @Serializable
    object Offended : AngerFeeling()
    @Serializable
    object Mad : AngerFeeling()
    @Serializable
    object Disgusted : AngerFeeling()
    @Serializable
    object Hateful : AngerFeeling()
    @Serializable
    object Furious : AngerFeeling()
    @Serializable
    object Agitated : AngerFeeling()
    @Serializable
    object Enraged : AngerFeeling()
    @Serializable
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
