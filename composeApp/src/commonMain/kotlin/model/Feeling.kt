package model

import androidx.compose.ui.graphics.Color
import com.google.errorprone.annotations.Keep
import com.me.resources.library.MR


@Keep
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
            is Peaceful.Accepted -> MR.strings.accepted.toString()
            is Peaceful.Affectionate -> MR.strings.affectionate.toString()
            is Anger.Agitated -> MR.strings.agitated.toString()
            is Surprise.Amazed -> MR.strings.amazed.toString()
            is Anger.Annoyed -> MR.strings.annoyed.toString()
            is Fear.Anxious -> MR.strings.anxious.toString()
            is Surprise.AweStruck -> MR.strings.awe_struck.toString()
            is Surprise.Bewildered -> MR.strings.bewildered.toString()
            is Sad.Bored -> MR.strings.bored.toString()
            is Peaceful.Calm -> MR.strings.calm.toString()
            is Peaceful.Caring -> MR.strings.caring.toString()
            is Happy.Cheerful -> MR.strings.cheerful.toString()
            is Happy.Confident -> MR.strings.confident.toString()
            is Surprise.Confused -> MR.strings.confused.toString()
            is Happy.Content -> MR.strings.content.toString()
            is Surprise.Crushed ->MR.strings.crushed.toString()
            is Surprise.Curious -> MR.strings.curious.toString()
            is Sad.Depressed -> MR.strings.depressed.toString()
            is Sad.Disappointed -> MR.strings.disappointed.toString()
            is Anger.Disgusted -> MR.strings.disgust.toString()
            is Happy.Eager -> MR.strings.eager.toString()
            is Happy.Elation -> MR.strings.elation.toString()
            is Fear.Embarrassed -> MR.strings.embarrassed.toString()
            is Happy.Energetic -> MR.strings.energetic.toString()
            is Anger.Enraged -> MR.strings.enraged.toString()
            is Happy.Enthusiastic -> MR.strings.enthusiastic.toString()
            is Happy.Excited -> MR.strings.excited.toString()
            is Fear.Excluded -> MR.strings.excluded.toString()
            is Fear.Frightened -> MR.strings.frightened.toString()
            is Anger.Frustrated -> MR.strings.frustrated.toString()
            is Anger.Furious -> MR.strings.furious.toString()
            is Sad.Guilty -> MR.strings.guilty.toString()
            is Anger.Hateful -> MR.strings.hateful.toString()
            is Happy.Hopeful -> MR.strings.hopeful.toString()
            is Sad.Hopeless -> MR.strings.hopeless.toString()
            is Anger.Hostile -> MR.strings.hostile.toString()
            is Sad.Hurt -> MR.strings.hurt.toString()
            is Sad.Inferior -> MR.strings.inferior.toString()
            is Fear.Insecure -> MR.strings.insecure.toString()
            is Sad.Insignificant -> MR.strings.insignificant.toString()
            is Peaceful.Inspired -> MR.strings.inspired.toString()
            is Anger.Irritated -> MR.strings.irritated.toString()
            is Anger.Jealous -> MR.strings.jealous.toString()
            is Sad.Lonely -> MR.strings.lonely.toString()
            is Peaceful.Loving -> MR.strings.loving.toString()
            is Anger.Mad -> MR.strings.mad.toString()
            is Sad.Miserable -> MR.strings.miserable.toString()
            is Surprise.Moved -> MR.strings.moved.toString()
            is Fear.Nervous -> MR.strings.nervous.toString()
            is Anger.Offended -> MR.strings.offended.toString()
            is Happy.Optimistic -> MR.strings.optimistic.toString()
            is Surprise.OverWhelmed -> MR.strings.overwhelmed.toString()
            is Fear.Panic -> MR.strings.panic.toString()
            is Peaceful.Passionate -> MR.strings.passionate.toString()
            is Happy.Proud -> MR.strings.proud.toString()
            is Fear.Regret -> MR.strings.regret.toString()
            is Sad.Rejected -> MR.strings.rejected.toString()
            is Peaceful.Relaxed -> MR.strings.relaxed.toString()
            is Fear.Scared -> MR.strings.scared.toString()
            is Surprise.Shocked -> MR.strings.shocked.toString()
            is Fear.Shy -> MR.strings.shy.toString()
            is Happy.Silly -> MR.strings.silly.toString()
            is Surprise.Speechless -> MR.strings.speechless.toString()
            is Surprise.Startled -> MR.strings.startled.toString()
            is Surprise.Stunned -> MR.strings.stunned.toString()
            is Fear.Terrified -> MR.strings.terrified.toString()
            is Peaceful.Thankful -> MR.strings.thankful.toString()
            is Peaceful.Thoughtful -> MR.strings.thoughtful.toString()
            is Sad.Tired -> MR.strings.tired.toString()
            is Peaceful.Trusting -> MR.strings.trusting.toString()
            is Peaceful.Valued -> MR.strings.valued.toString()
            is Fear.Worried -> MR.strings.worried.toString()
        }
    }

    fun nameCompact(): String {
        return when (this) {
            is Peaceful.Accepted -> MR.strings.accepted_b.toString()
            is Peaceful.Affectionate -> MR.strings.affectionate_b.toString()
            is Anger.Agitated -> MR.strings.agitated_b.toString()
            is Surprise.Amazed -> MR.strings.amazed_b.toString()
            is Anger.Annoyed -> MR.strings.annoyed_b.toString()
            is Fear.Anxious -> MR.strings.anxious_b.toString()
            is Surprise.AweStruck -> MR.strings.awe_struck_b.toString()
            is Surprise.Bewildered -> MR.strings.bewildered_b.toString()
            is Sad.Bored -> MR.strings.bored_b.toString()
            is Peaceful.Calm -> MR.strings.calm_b.toString()
            is Peaceful.Caring -> MR.strings.caring_b.toString()
            is Happy.Cheerful -> MR.strings.cheerful_b.toString()
            is Happy.Confident -> MR.strings.confident_b.toString()
            is Surprise.Confused -> MR.strings.confused_b.toString()
            is Happy.Content -> MR.strings.content_b.toString()
            is Surprise.Crushed -> MR.strings.crushed_b.toString()
            is Surprise.Curious -> MR.strings.curious_b.toString()
            is Sad.Depressed -> MR.strings.depressed_b.toString()
            is Sad.Disappointed -> MR.strings.disappointed_b.toString()
            is Anger.Disgusted -> MR.strings.disgust_b.toString()
            is Happy.Eager -> MR.strings.eager_b.toString()
            is Happy.Elation -> MR.strings.elation_b.toString()
            is Fear.Embarrassed -> MR.strings.embarrassed_b.toString()
            is Happy.Energetic -> MR.strings.energetic_b.toString()
            is Anger.Enraged -> MR.strings.enraged_b.toString()
            is Happy.Enthusiastic -> MR.strings.enthusiastic_b.toString()
            is Happy.Excited -> MR.strings.excited_b.toString()
            is Fear.Excluded -> MR.strings.excluded_b.toString()
            is Fear.Frightened -> MR.strings.frightened_b.toString()
            is Anger.Frustrated -> MR.strings.frustrated_b.toString()
            is Anger.Furious -> MR.strings.furious_b.toString()
            is Sad.Guilty -> MR.strings.guilty_b.toString()
            is Anger.Hateful -> MR.strings.hateful_b.toString()
            is Happy.Hopeful -> MR.strings.hopeful_b.toString()
            is Sad.Hopeless -> MR.strings.hopeless_b.toString()
            is Anger.Hostile -> MR.strings.hostile_b.toString()
            is Sad.Hurt -> MR.strings.hurt_b.toString()
            is Sad.Inferior -> MR.strings.inferior_b.toString()
            is Fear.Insecure -> MR.strings.insecure_b.toString()
            is Sad.Insignificant -> MR.strings.insignificant_b.toString()
            is Peaceful.Inspired -> MR.strings.inspired_b.toString()
            is Anger.Irritated -> MR.strings.irritated_b.toString()
            is Anger.Jealous -> MR.strings.jealous_b.toString()
            is Sad.Lonely -> MR.strings.lonely_b.toString()
            is Peaceful.Loving -> MR.strings.loving_b.toString()
            is Anger.Mad -> MR.strings.mad_b.toString()
            is Sad.Miserable -> MR.strings.miserable_b.toString()
            is Surprise.Moved -> MR.strings.moved_b.toString()
            is Fear.Nervous -> MR.strings.nervous_b.toString()
            is Anger.Offended -> MR.strings.offended_b.toString()
            is Happy.Optimistic -> MR.strings.optimistic_b.toString()
            is Surprise.OverWhelmed -> MR.strings.overwhelmed_b.toString()
            is Fear.Panic -> MR.strings.panic_b.toString()
            is Peaceful.Passionate -> MR.strings.passionate_b.toString()
            is Happy.Proud -> MR.strings.proud_b.toString()
            is Fear.Regret -> MR.strings.regret_b.toString()
            is Sad.Rejected -> MR.strings.rejected_b.toString()
            is Peaceful.Relaxed -> MR.strings.relaxed_b.toString()
            is Fear.Scared -> MR.strings.scared_b.toString()
            is Surprise.Shocked -> MR.strings.shocked_b.toString()
            is Fear.Shy -> MR.strings.shy_b.toString()
            is Happy.Silly -> MR.strings.silly_b.toString()
            is Surprise.Speechless -> MR.strings.speechless_b.toString()
            is Surprise.Startled -> MR.strings.startled_b.toString()
            is Surprise.Stunned -> MR.strings.stunned_b.toString()
            is Fear.Terrified -> MR.strings.terrified_b.toString()
            is Peaceful.Thankful -> MR.strings.thankful_b.toString()
            is Peaceful.Thoughtful -> MR.strings.thoughtful_b.toString()
            is Sad.Tired -> MR.strings.tired_b.toString()
            is Peaceful.Trusting -> MR.strings.trusting_b.toString()
            is Peaceful.Valued -> MR.strings.valued_b.toString()
            is Fear.Worried -> MR.strings.worried_b.toString()
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
