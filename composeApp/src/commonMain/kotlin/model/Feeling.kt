package model

import androidx.compose.ui.graphics.Color
import com.me.resources.library.MR
import data.storage.utils.FeelingSerializer
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.serialization.Serializable

@Serializable(with = FeelingSerializer::class)
sealed class Feeling {

    abstract fun color(): Color
    abstract fun emotion(): Emotion

    fun name(): String {
        return when (this) {
            is Peaceful.Accepted -> "Accepted"
            is Peaceful.Affectionate -> "Affectionate"
            is Anger.Agitated -> "Agitated"
            is Surprise.Amazed -> "Amazed"
            is Anger.Annoyed -> "Annoyed"
            is Fear.Anxious -> "Anxious"
            is Surprise.AweStruck -> "AweStruck"
            is Surprise.Bewildered -> "Bewildered"
            is Sad.Bored -> "Bored"
            is Peaceful.Calm -> "Calm"
            is Peaceful.Caring -> "Caring"
            is Happy.Cheerful -> "Cheerful"
            is Happy.Confident -> "Confident"
            is Surprise.Confused -> "Confused"
            is Happy.Content -> "Content"
            is Surprise.Crushed -> "Crushed"
            is Surprise.Curious -> "Curious"
            is Sad.Depressed -> "Depressed"
            is Sad.Disappointed -> "Disappointed"
            is Anger.Disgusted -> "Disgusted"
            is Happy.Eager -> "Eager"
            is Happy.Elation -> "Elation"
            is Fear.Embarrassed -> "Embarrassed"
            is Happy.Energetic -> "Energetic"
            is Anger.Enraged -> "Enraged"
            is Happy.Enthusiastic -> "Enthusiastic"
            is Happy.Excited -> "Excited"
            is Fear.Excluded -> "Excluded"
            is Fear.Frightened -> "Frightened"
            is Anger.Frustrated -> "Frustrated"
            is Anger.Furious -> "Furious"
            is Sad.Guilty -> "Guilty"
            is Anger.Hateful -> "Hateful"
            is Happy.Hopeful -> "Hopeful"
            is Sad.Hopeless -> "Hopeless"
            is Anger.Hostile -> "Hostile"
            is Sad.Hurt -> "Hurt"
            is Sad.Inferior -> "Inferior"
            is Fear.Insecure -> "Insecure"
            is Sad.Insignificant -> "Insignificant"
            is Peaceful.Inspired -> "Inspired"
            is Anger.Irritated -> "Irritated"
            is Anger.Jealous -> "Jealous"
            is Sad.Lonely -> "Lonely"
            is Peaceful.Loving -> "Loving"
            is Anger.Mad -> "Mad"
            is Sad.Miserable -> "Miserable"
            is Surprise.Moved -> "Moved"
            is Fear.Nervous -> "Nervous"
            is Anger.Offended -> "Offended"
            is Happy.Optimistic -> "Optimistic"
            is Surprise.OverWhelmed -> "OverWhelmed"
            is Fear.Panic -> "Panic"
            is Peaceful.Passionate -> "Passionate"
            is Happy.Proud -> "Proud"
            is Fear.Regret -> "Regret"
            is Sad.Rejected -> "Rejected"
            is Peaceful.Relaxed -> "Relaxed"
            is Fear.Scared -> "Scared"
            is Surprise.Shocked -> "Shocked"
            is Fear.Shy -> "Shy"
            is Happy.Silly -> "Silly"
            is Surprise.Speechless -> "Speechless"
            is Surprise.Startled -> "Startled"
            is Surprise.Stunned -> "Stunned"
            is Fear.Terrified -> "Terrified"
            is Peaceful.Thankful -> "Thankful"
            is Peaceful.Thoughtful -> "Thoughtful"
            is Sad.Tired -> "Tired"
            is Peaceful.Trusting -> "Trusting"
            is Peaceful.Valued -> "Valued"
            is Fear.Worried -> "Worried"
        }
    }

    fun nameTranslated(): String {
        return when (this) {
            is Peaceful.Accepted -> string(MR.strings.accepted)
            is Peaceful.Affectionate -> string(MR.strings.affectionate)
            is Anger.Agitated -> string(MR.strings.agitated)
            is Surprise.Amazed -> string(MR.strings.amazed)
            is Anger.Annoyed -> string(MR.strings.annoyed)
            is Fear.Anxious -> string(MR.strings.anxious)
            is Surprise.AweStruck -> string(MR.strings.awe_struck)
            is Surprise.Bewildered -> string(MR.strings.bewildered)
            is Sad.Bored -> string(MR.strings.bored)
            is Peaceful.Calm -> string(MR.strings.calm)
            is Peaceful.Caring -> string(MR.strings.caring)
            is Happy.Cheerful -> string(MR.strings.cheerful)
            is Happy.Confident -> string(MR.strings.confident)
            is Surprise.Confused -> string(MR.strings.confused)
            is Happy.Content -> string(MR.strings.content)
            is Surprise.Crushed -> string(MR.strings.crushed)
            is Surprise.Curious -> string(MR.strings.curious)
            is Sad.Depressed -> string(MR.strings.depressed)
            is Sad.Disappointed -> string(MR.strings.disappointed)
            is Anger.Disgusted -> string(MR.strings.disgust)
            is Happy.Eager -> string(MR.strings.eager)
            is Happy.Elation -> string(MR.strings.elation)
            is Fear.Embarrassed -> string(MR.strings.embarrassed)
            is Happy.Energetic -> string(MR.strings.energetic)
            is Anger.Enraged -> string(MR.strings.enraged)
            is Happy.Enthusiastic -> string(MR.strings.enthusiastic)
            is Happy.Excited -> string(MR.strings.excited)
            is Fear.Excluded -> string(MR.strings.excluded)
            is Fear.Frightened -> string(MR.strings.frightened)
            is Anger.Frustrated -> string(MR.strings.frustrated)
            is Anger.Furious -> string(MR.strings.furious)
            is Sad.Guilty -> string(MR.strings.guilty)
            is Anger.Hateful -> string(MR.strings.hateful)
            is Happy.Hopeful -> string(MR.strings.hopeful)
            is Sad.Hopeless -> string(MR.strings.hopeless)
            is Anger.Hostile -> string(MR.strings.hostile)
            is Sad.Hurt -> string(MR.strings.hurt)
            is Sad.Inferior -> string(MR.strings.inferior)
            is Fear.Insecure -> string(MR.strings.insecure)
            is Sad.Insignificant -> string(MR.strings.insignificant)
            is Peaceful.Inspired -> string(MR.strings.inspired)
            is Anger.Irritated -> string(MR.strings.irritated)
            is Anger.Jealous -> string(MR.strings.jealous)
            is Sad.Lonely -> string(MR.strings.lonely)
            is Peaceful.Loving -> string(MR.strings.loving)
            is Anger.Mad -> string(MR.strings.mad)
            is Sad.Miserable -> string(MR.strings.miserable)
            is Surprise.Moved -> string(MR.strings.moved)
            is Fear.Nervous -> string(MR.strings.nervous)
            is Anger.Offended -> string(MR.strings.offended)
            is Happy.Optimistic -> string(MR.strings.optimistic)
            is Surprise.OverWhelmed -> string(MR.strings.overwhelmed)
            is Fear.Panic -> string(MR.strings.panic)
            is Peaceful.Passionate -> string(MR.strings.passionate)
            is Happy.Proud -> string(MR.strings.proud)
            is Fear.Regret -> string(MR.strings.regret)
            is Sad.Rejected -> string(MR.strings.rejected)
            is Peaceful.Relaxed -> string(MR.strings.relaxed)
            is Fear.Scared -> string(MR.strings.scared)
            is Surprise.Shocked -> string(MR.strings.shocked)
            is Fear.Shy -> string(MR.strings.shy)
            is Happy.Silly -> string(MR.strings.silly)
            is Surprise.Speechless -> string(MR.strings.speechless)
            is Surprise.Startled -> string(MR.strings.startled)
            is Surprise.Stunned -> string(MR.strings.stunned)
            is Fear.Terrified -> string(MR.strings.terrified)
            is Peaceful.Thankful -> string(MR.strings.thankful)
            is Peaceful.Thoughtful -> string(MR.strings.thoughtful)
            is Sad.Tired -> string(MR.strings.tired)
            is Peaceful.Trusting -> string(MR.strings.trusting)
            is Peaceful.Valued -> string(MR.strings.valued)
            is Fear.Worried -> string(MR.strings.worried)
        }
    }

    fun nameCompact(): String {
        return when (this) {
            is Peaceful.Accepted -> string(MR.strings.accepted_b)
            is Peaceful.Affectionate -> string(MR.strings.affectionate_b)
            is Anger.Agitated -> string(MR.strings.agitated_b)
            is Surprise.Amazed -> string(MR.strings.amazed_b)
            is Anger.Annoyed -> string(MR.strings.annoyed_b)
            is Fear.Anxious -> string(MR.strings.anxious_b)
            is Surprise.AweStruck -> string(MR.strings.awe_struck_b)
            is Surprise.Bewildered -> string(MR.strings.bewildered_b)
            is Sad.Bored -> string(MR.strings.bored_b)
            is Peaceful.Calm -> string(MR.strings.calm_b)
            is Peaceful.Caring -> string(MR.strings.caring_b)
            is Happy.Cheerful -> string(MR.strings.cheerful_b)
            is Happy.Confident -> string(MR.strings.confident_b)
            is Surprise.Confused -> string(MR.strings.confused_b)
            is Happy.Content -> string(MR.strings.content_b)
            is Surprise.Crushed -> string(MR.strings.crushed_b)
            is Surprise.Curious -> string(MR.strings.curious_b)
            is Sad.Depressed -> string(MR.strings.depressed_b)
            is Sad.Disappointed -> string(MR.strings.disappointed_b)
            is Anger.Disgusted -> string(MR.strings.disgust_b)
            is Happy.Eager -> string(MR.strings.eager_b)
            is Happy.Elation -> string(MR.strings.elation_b)
            is Fear.Embarrassed -> string(MR.strings.embarrassed_b)
            is Happy.Energetic -> string(MR.strings.energetic_b)
            is Anger.Enraged -> string(MR.strings.enraged_b)
            is Happy.Enthusiastic -> string(MR.strings.enthusiastic_b)
            is Happy.Excited -> string(MR.strings.excited_b)
            is Fear.Excluded -> string(MR.strings.excluded_b)
            is Fear.Frightened -> string(MR.strings.frightened_b)
            is Anger.Frustrated -> string(MR.strings.frustrated_b)
            is Anger.Furious -> string(MR.strings.furious_b)
            is Sad.Guilty -> string(MR.strings.guilty_b)
            is Anger.Hateful -> string(MR.strings.hateful_b)
            is Happy.Hopeful -> string(MR.strings.hopeful_b)
            is Sad.Hopeless -> string(MR.strings.hopeless_b)
            is Anger.Hostile -> string(MR.strings.hostile_b)
            is Sad.Hurt -> string(MR.strings.hurt_b)
            is Sad.Inferior -> string(MR.strings.inferior_b)
            is Fear.Insecure -> string(MR.strings.insecure_b)
            is Sad.Insignificant -> string(MR.strings.insignificant_b)
            is Peaceful.Inspired -> string(MR.strings.inspired_b)
            is Anger.Irritated -> string(MR.strings.irritated_b)
            is Anger.Jealous -> string(MR.strings.jealous_b)
            is Sad.Lonely -> string(MR.strings.lonely_b)
            is Peaceful.Loving -> string(MR.strings.loving_b)
            is Anger.Mad -> string(MR.strings.mad_b)
            is Sad.Miserable -> string(MR.strings.miserable_b)
            is Surprise.Moved -> string(MR.strings.moved_b)
            is Fear.Nervous -> string(MR.strings.nervous_b)
            is Anger.Offended -> string(MR.strings.offended_b)
            is Happy.Optimistic -> string(MR.strings.optimistic_b)
            is Surprise.OverWhelmed -> string(MR.strings.overwhelmed_b)
            is Fear.Panic -> string(MR.strings.panic_b)
            is Peaceful.Passionate -> string(MR.strings.passionate_b)
            is Happy.Proud -> string(MR.strings.proud_b)
            is Fear.Regret -> string(MR.strings.regret_b)
            is Sad.Rejected -> string(MR.strings.rejected_b)
            is Peaceful.Relaxed -> string(MR.strings.relaxed_b)
            is Fear.Scared -> string(MR.strings.scared_b)
            is Surprise.Shocked -> string(MR.strings.shocked_b)
            is Fear.Shy -> string(MR.strings.shy_b)
            is Happy.Silly -> string(MR.strings.silly_b)
            is Surprise.Speechless -> string(MR.strings.speechless_b)
            is Surprise.Startled -> string(MR.strings.startled_b)
            is Surprise.Stunned -> string(MR.strings.stunned_b)
            is Fear.Terrified -> string(MR.strings.terrified_b)
            is Peaceful.Thankful -> string(MR.strings.thankful_b)
            is Peaceful.Thoughtful -> string(MR.strings.thoughtful_b)
            is Sad.Tired -> string(MR.strings.tired_b)
            is Peaceful.Trusting -> string(MR.strings.trusting_b)
            is Peaceful.Valued -> string(MR.strings.valued_b)
            is Fear.Worried -> string(MR.strings.worried_b)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return this::class == other!!::class
    }

    override fun hashCode(): Int {
        return this::class.hashCode()
    }

    companion object {
        fun random() = Emotion.random().feelings.random()

        fun ofName(name: String): Feeling = when (name) {
            "Accepted" -> Peaceful.Accepted
            "Affectionate" -> Peaceful.Affectionate
            "Agitated" -> Anger.Agitated
            "Amazed" -> Surprise.Amazed
            "Annoyed" -> Anger.Annoyed
            "Anxious" -> Fear.Anxious
            "AweStruck" -> Surprise.AweStruck
            "Bewildered" -> Surprise.Bewildered
            "Bored" -> Sad.Bored
            "Calm" -> Peaceful.Calm
            "Caring" -> Peaceful.Caring
            "Cheerful" -> Happy.Cheerful
            "Confident" -> Happy.Confident
            "Confused" -> Surprise.Confused
            "Content" -> Happy.Content
            "Crushed" -> Surprise.Crushed
            "Curious" -> Surprise.Curious
            "Depressed" -> Sad.Depressed
            "Disappointed" -> Sad.Disappointed
            "Disgusted" -> Anger.Disgusted
            "Eager" -> Happy.Eager
            "Elation" -> Happy.Elation
            "Embarrassed" -> Fear.Embarrassed
            "Energetic" -> Happy.Energetic
            "Enraged" -> Anger.Enraged
            "Enthusiastic" -> Happy.Enthusiastic
            "Excited" -> Happy.Excited
            "Excluded" -> Fear.Excluded
            "Frightened" -> Fear.Frightened
            "Frustrated" -> Anger.Frustrated
            "Furious" -> Anger.Furious
            "Guilty" -> Sad.Guilty
            "Hateful" -> Anger.Hateful
            "Hopeful" -> Happy.Hopeful
            "Hopeless" -> Sad.Hopeless
            "Hostile" -> Anger.Hostile
            "Hurt" -> Sad.Hurt
            "Inferior" -> Sad.Inferior
            "Insecure" -> Fear.Insecure
            "Insignificant" -> Sad.Insignificant
            "Inspired" -> Peaceful.Inspired
            "Irritated" -> Anger.Irritated
            "Jealous" -> Anger.Jealous
            "Lonely" -> Sad.Lonely
            "Loving" -> Peaceful.Loving
            "Mad" -> Anger.Mad
            "Miserable" -> Sad.Miserable
            "Moved" -> Surprise.Moved
            "Nervous" -> Fear.Nervous
            "Offended" -> Anger.Offended
            "Optimistic" -> Happy.Optimistic
            "OverWhelmed" -> Surprise.OverWhelmed
            "Panic" -> Fear.Panic
            "Passionate" -> Peaceful.Passionate
            "Proud" -> Happy.Proud
            "Regret" -> Fear.Regret
            "Rejected" -> Sad.Rejected
            "Relaxed" -> Peaceful.Relaxed
            "Scared" -> Fear.Scared
            "Shocked" -> Surprise.Shocked
            "Shy" -> Fear.Shy
            "Silly" -> Happy.Silly
            "Speechless" -> Surprise.Speechless
            "Startled" -> Surprise.Startled
            "Stunned" -> Surprise.Stunned
            "Terrified" -> Fear.Terrified
            "Thankful" -> Peaceful.Thankful
            "Thoughtful" -> Peaceful.Thoughtful
            "Tired" -> Sad.Tired
            "Trusting" -> Peaceful.Trusting
            "Valued" -> Peaceful.Valued
            "Worried" -> Fear.Worried
            else -> Happy.Silly
        }
    }
}

fun string(resource: StringResource): String {
    return translatedString(StringDesc.Resource(resource))
}

expect fun translatedString(stringDesc: StringDesc): String
